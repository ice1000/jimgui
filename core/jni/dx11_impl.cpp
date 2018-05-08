///
/// Created by ice1000 on 18-5-8.
///

#include <imgui.h>
#include <imgui_impl_dx11.h>

#include <org_ice1000_jimgui_JImGui.h>
#include "basics.hpp"

#include <d3d11.h>
#include <dinput.h>
#include <tchar.h>

#define DIRECTINPUT_VERSION 0x0800

// for Linux editing experience
#ifndef WIN32
#define LRESULT
#define WINAPI
#define HWND int
#define LPVOID int
#define UINT unsigned int
#define MSG int
#define IDXGISwapChain int
#define ID3D11RenderTargetView int
#define WNDCLASSEX int
#endif

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

// Data
static ID3D11Device *g_pd3dDevice = NULL;
static ID3D11DeviceContext *g_pd3dDeviceContext = NULL;
static IDXGISwapChain *g_pSwapChain = NULL;
static ID3D11RenderTargetView *g_mainRenderTargetView = NULL;
static auto WINDOW_ID = "JIMGUI_WINDOW";

struct NativeObject {
		HWND hwnd;
		MSG msg;
		WNDCLASSEX wc;
		NativeObject(const char *title) : wc(
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

void CreateRenderTarget() {
	ID3D11Texture2D *pBackBuffer;
	g_pSwapChain->GetBuffer(0, __uuidof(ID3D11Texture2D), (LPVOID *) &pBackBuffer);
	g_pd3dDevice->CreateRenderTargetView(pBackBuffer, NULL, &g_mainRenderTargetView);
	pBackBuffer->Release();
}

void CleanupRenderTarget() {
	if (g_mainRenderTargetView) {
		g_mainRenderTargetView->Release();
		g_mainRenderTargetView = NULL;
	}
}

HRESULT CreateDeviceD3D(HWND hWnd) {
	// Setup swap chain
	DXGI_SWAP_CHAIN_DESC sd;
	ZeroMemory(&sd, sizeof(sd));
	sd.BufferCount = 2;
	sd.BufferDesc.Width = 0;
	sd.BufferDesc.Height = 0;
	sd.BufferDesc.Format = DXGI_FORMAT_R8G8B8A8_UNORM;
	sd.BufferDesc.RefreshRate.Numerator = 60;
	sd.BufferDesc.RefreshRate.Denominator = 1;
	sd.Flags = DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH;
	sd.BufferUsage = DXGI_USAGE_RENDER_TARGET_OUTPUT;
	sd.OutputWindow = hWnd;
	sd.SampleDesc.Count = 1;
	sd.SampleDesc.Quality = 0;
	sd.Windowed = TRUE;
	sd.SwapEffect = DXGI_SWAP_EFFECT_DISCARD;

	UINT createDeviceFlags = 0;
	//createDeviceFlags |= D3D11_CREATE_DEVICE_DEBUG;
	D3D_FEATURE_LEVEL featureLevel;
	const D3D_FEATURE_LEVEL featureLevelArray[2] = {D3D_FEATURE_LEVEL_11_0, D3D_FEATURE_LEVEL_10_0,};
	if (D3D11CreateDeviceAndSwapChain(
			NULL, D3D_DRIVER_TYPE_HARDWARE, NULL, createDeviceFlags, featureLevelArray, 2,
			D3D11_SDK_VERSION, &sd, &g_pSwapChain, &g_pd3dDevice, &featureLevel,
			&g_pd3dDeviceContext) != S_OK)
		return E_FAIL;

	CreateRenderTarget();

	return S_OK;
}

void CleanupDeviceD3D() {
	CleanupRenderTarget();
	if (g_pSwapChain) {
		g_pSwapChain->Release();
		g_pSwapChain = NULL;
	}
	if (g_pd3dDeviceContext) {
		g_pd3dDeviceContext->Release();
		g_pd3dDeviceContext = NULL;
	}
	if (g_pd3dDevice) {
		g_pd3dDevice->Release();
		g_pd3dDevice = NULL;
	}
}

extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam) {
	if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
		return true;

	switch (msg) {
		case WM_SIZE:
			if (g_pd3dDevice != NULL && wParam != SIZE_MINIMIZED) {
				ImGui_ImplDX11_InvalidateDeviceObjects();
				CleanupRenderTarget();
				g_pSwapChain->ResizeBuffers(0, (UINT) LOWORD(lParam), (UINT) HIWORD(lParam), DXGI_FORMAT_UNKNOWN, 0);
				CreateRenderTarget();
				ImGui_ImplDX11_CreateDeviceObjects();
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

auto Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jbyteArray _title) -> jlong {
	__JNI__FUNCTION__INIT__
	__get(Byte, title);

	// Create application window
	auto object = new NativeObject(static_cast<const jbyte *> (title));

	// Initialize Direct3D
	if (CreateDeviceD3D(object->hwnd) < 0) {
		CleanupDeviceD3D();
		UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
		return NULL;
	}

	// Show the window
	ShowWindow(object->hwnd, SW_SHOWDEFAULT);
	UpdateWindow(object->hwnd);

	// Setup Dear ImGui binding
	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO &io = ImGui::GetIO();
	(void) io;
	//io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;  // Enable Keyboard Controls
	ImGui_ImplDX11_Init(object->hwnd, g_pd3dDevice, g_pd3dDeviceContext);
	__abort(Byte, title);
	__JNI__FUNCTION__CLEAN__
	return static_cast<jlong> (object);
}

auto Java_org_ice1000_jimgui_JImGui_windowShouldClose(JNIEnv *, jclass, jlong nativeObjectPtr) -> jboolean {
	auto object = static_cast<NativeObject *> (nativeObjectPtr);
	return static_cast<jboolean> (object->msg.message == WM_QUIT ? JNI_TRUE : JNI_FALSE);
}

void Java_org_ice1000_jimgui_JImGui_initNewFrame(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto object = static_cast<NativeObject *> (nativeObjectPtr);
	while (object->msg.message != WM_QUIT and PeekMessage(&object->msg, NULL, 0U, 0U, PM_REMOVE)) {
		TranslateMessage(&object->msg);
		DispatchMessage(&object->msg);
	}
	ImGui_ImplDX11_NewFrame();
}

void Java_org_ice1000_jimgui_JImGui_render(JNIEnv *, jclass, jlong nativeObjectPtr, jlong colorPtr) {
	auto clear_color = *reinterpret_cast<ImVec4 *> (colorPtr);
// Rendering
	g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
	g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, (float *) &clear_color);
	ImGui::Render();
	ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());

	g_pSwapChain->Present(1, 0); // Present with vsync
//g_pSwapChain->Present(0, 0); // Present without vsync
}

void Java_org_ice1000_jimgui_JImGui_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto object = static_cast<NativeObject *> (nativeObjectPtr);
	ImGui_ImplDX11_Shutdown();
	ImGui::DestroyContext();

	CleanupDeviceD3D();
	DestroyWindow(object->hwnd);
	UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
	delete object;
}


#pragma clang diagnostic pop
