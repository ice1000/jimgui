/// TODO

#include <imgui.h>
#include <imgui_impl_dx9.h>
#include <imgui_impl_win32.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImTextureID.h>

#include <d3d9.h>
#include <d3dx9tex.h>

#define DIRECTINPUT_VERSION 0x0800

#include <dinput.h>
#include <tchar.h>

#include <basics.hpp>
#include <impl_header.h>

// for Linux editing experience
#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#define LRESULT int
#define WINAPI
#define HWND int
#define UINT unsigned int
#define MSG int
#define WNDCLASSEX int
#define LPDIRECT3D9 int
#define LPDIRECT3DTEXTURE9 int
#define WPARAM int
#define LPARAM int
#endif

// Data
static LPDIRECT3DDEVICE9 g_pd3dDevice = NULL;
static D3DPRESENT_PARAMETERS g_d3dpp;
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);
static auto WINDOW_ID = "JIMGUI_WINDOW";

extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);
//extern LRESULT D3DXCreateTextureFromFile(LPDIRECT3DDEVICE9, Ptr<const char>, Ptr<LPDIRECT3DTEXTURE9>);

auto loadTexture(Ptr<const char> fileName, LPDIRECT3DTEXTURE9 &texture) -> bool {
	return SUCCEEDED(D3DXCreateTextureFromFile(g_pd3dDevice, fileName, &texture));
}

auto loadTextureInMemory(Ptr<void> rawData, size_t size, LPDIRECT3DTEXTURE9 &texture) -> bool {
	return SUCCEEDED(D3DXCreateTextureFromFileInMemory(g_pd3dDevice, rawData, size, &texture));
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImTextureID_createTextureFromBytes(jint,
                                                                    Ptr<jbyte> rawData,
                                                                    jint size,
                                                                    jint width,
                                                                    jint height) -> jlong {
	LPDIRECT3DTEXTURE9 texture;
	auto success = loadTextureInMemory(PTR_J2C(void, rawData), size, texture);
	if (!success) return nullptr;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(Ptr<JNIEnv> env,
                                                           jclass,
                                                           jbyteArray _fileName) -> jlongArray {
	__JNI__FUNCTION__INIT__
	__get(Byte, fileName)
	LPDIRECT3DTEXTURE9 texture;
	auto success = loadTexture(STR_J2C(fileName), texture);
	__release(Byte, fileName)
	if (!success) return nullptr;
	D3DSURFACE_DESC desc;
	texture->GetLevelDesc(0, &desc);
	int width = static_cast<jint>(desc.Width);
	int height = static_cast<jint>(desc.Height);
#define RET_LEN 3
	auto ret = new jlong[RET_LEN];
	ret[0] = PTR_C2J(texture);
	ret[1] = static_cast<jlong> (width);
	ret[2] = static_cast<jlong> (height);
	__init(Long, ret, RET_LEN);
#undef RET_LEN
	delete[] ret;
	__JNI__FUNCTION__CLEAN__
	return _ret;
}

struct NativeObject {
	HWND hwnd;
	MSG msg;
	WNDCLASSEX wc;
	LPDIRECT3D9 pD3D;

	NativeObject(jint width, jint height, Ptr<const char> title) : wc(
			{
					sizeof(WNDCLASSEX),
					CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(NULL), NULL, NULL, NULL, NULL,
					_T(WINDOW_ID), NULL
			}) {
		RegisterClassEx(&wc);
		ZeroMemory(&msg, sizeof(msg));
		hwnd = CreateWindow(
				_T(WINDOW_ID), _T(title), WS_OVERLAPPEDWINDOW,
				100, 100, width, height, NULL, NULL, wc.hInstance, NULL);
	};
};

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jlong fontAtlas, jbyteArray _title) -> jlong {
	__JNI__FUNCTION__INIT__
	__get(Byte, title);

	// Create application window
	auto object = new NativeObject(width, height, STR_J2C(title));

	__release(Byte, title);
	__JNI__FUNCTION__CLEAN__

	// Initialize Direct3D
	LPDIRECT3D9 pD3D;
	if ((pD3D = Direct3DCreate9(D3D_SDK_VERSION)) == NULL) {
		UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
		return NULL;
	}
	object->pD3D = pD3D;
	ZeroMemory(&g_d3dpp, sizeof(g_d3dpp));
	g_d3dpp.Windowed = TRUE;
	g_d3dpp.SwapEffect = D3DSWAPEFFECT_DISCARD;
	g_d3dpp.BackBufferFormat = D3DFMT_UNKNOWN;
	g_d3dpp.EnableAutoDepthStencil = TRUE;
	g_d3dpp.AutoDepthStencilFormat = D3DFMT_D16;
	g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_ONE; // Present with vsync
	//g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_IMMEDIATE; // Present without vsync, maximum unthrottled framerate

	// Create the D3DDevice
	if (pD3D->CreateDevice(D3DADAPTER_DEFAULT,
	                       D3DDEVTYPE_HAL, object->hwnd, D3DCREATE_HARDWARE_VERTEXPROCESSING, &g_d3dpp, &g_pd3dDevice) <
	    0) {
		pD3D->Release();
		UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
		return NULL;
	}

	// Setup Dear ImGui binding
	IMGUI_CHECKVERSION();
	ImGui::CreateContext(PTR_J2C(ImFontAtlas, fontAtlas));
	ImGuiIO &io = ImGui::GetIO();
	(void) io;
	// Enable Keyboard Controls
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
	ImGui_ImplWin32_Init(object->hwnd);
	ImGui_ImplDX9_Init(g_pd3dDevice);

	ShowWindow(object->hwnd, SW_SHOWDEFAULT);
	UpdateWindow(object->hwnd);
	return PTR_C2J(object);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(jlong nativeObjectPtr) -> jboolean {
	auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
	return static_cast<jboolean> (object->msg.message == WM_QUIT ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(jlong nativeObjectPtr) {
	auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
	while (object->msg.message != WM_QUIT && PeekMessage(&object->msg, NULL, 0U, 0U, PM_REMOVE)) {
		TranslateMessage(&object->msg);
		DispatchMessage(&object->msg);
	}
	ImGui_ImplDX9_NewFrame();
	ImGui_ImplWin32_NewFrame();
	ImGui::NewFrame();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_render(jlong, jlong colorPtr) {
	auto clear_color = reinterpret_cast<Ptr<ImVec4>> (colorPtr);
	// Rendering
	ImGui::EndFrame();
	g_pd3dDevice->SetRenderState(D3DRS_ZENABLE, false);
	g_pd3dDevice->SetRenderState(D3DRS_ALPHABLENDENABLE, false);
	g_pd3dDevice->SetRenderState(D3DRS_SCISSORTESTENABLE, false);
	D3DCOLOR clear_col_dx = D3DCOLOR_RGBA((int) (clear_color->x * 255.0f),
	                                      (int) (clear_color->y * 255.0f),
	                                      (int) (clear_color->z * 255.0f),
	                                      (int) (clear_color->w * 255.0f));
	g_pd3dDevice->Clear(0, NULL, D3DCLEAR_TARGET | D3DCLEAR_ZBUFFER, clear_col_dx, 1.0f, 0);
	if (g_pd3dDevice->BeginScene() >= 0) {
		ImGui::Render();
		ImGui_ImplDX9_RenderDrawData(ImGui::GetDrawData());
		g_pd3dDevice->EndScene();
	}
	HRESULT result = g_pd3dDevice->Present(NULL, NULL, NULL, NULL);

	// Handle loss of D3D9 device
	if (result == D3DERR_DEVICELOST && g_pd3dDevice->TestCooperativeLevel() == D3DERR_DEVICENOTRESET) {
		ImGui_ImplDX9_InvalidateDeviceObjects();
		g_pd3dDevice->Reset(&g_d3dpp);
		ImGui_ImplDX9_CreateDeviceObjects();
	}
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(jlong nativeObjectPtr) {
	ImGui_ImplDX9_Shutdown();
	ImGui_ImplWin32_Shutdown();
	ImGui::DestroyContext();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateGuiFramework(jlong nativeObjectPtr) {
	auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
	if (g_pd3dDevice) g_pd3dDevice->Release();
	if (object->pD3D) object->pD3D->Release();
	DestroyWindow(object->hwnd);
	UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
	delete object;
}

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam) {
	if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
		return true;

	switch (msg) {
		case WM_SIZE:
			if (g_pd3dDevice != NULL && wParam != SIZE_MINIMIZED) {
				ImGui_ImplDX9_InvalidateDeviceObjects();
				g_d3dpp.BackBufferWidth = LOWORD(lParam);
				g_d3dpp.BackBufferHeight = HIWORD(lParam);
				HRESULT hr = g_pd3dDevice->Reset(&g_d3dpp);
				if (hr == D3DERR_INVALIDCALL)
					IM_ASSERT(0);
				ImGui_ImplDX9_CreateDeviceObjects();
			}
			return 0;
		case WM_SYSCOMMAND:
			if ((wParam & 0xfff0) == SC_KEYMENU) // Disable ALT application menu
				return 0;
			break;
		case WM_DESTROY:
			PostQuitMessage(0);
			return 0;
	}
	return DefWindowProc(hWnd, msg, wParam, lParam);
}

#ifndef WIN32
#pragma clang diagnostic pop
#endif
