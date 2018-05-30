///
/// Created by ice1000 on 18-5-8.
///

#include <imgui.h>
#include <imgui_impl_dx11.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImTextureID.h>

#define STB_IMAGE_IMPLEMENTATION
#define STBI_NO_LINEAR
#define STBI_NO_HDR
#define STBI_NO_GIF

#include <stb_image.h>

#include <d3d11.h>
#include <d3d11_1.h>
#include <dinput.h>
#include <tchar.h>

#include "basics.hpp"

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
#define _In_
#define _In_opt_
#define _In_z_
#define _Out_opt_
#endif

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

// Data
static ID3D11Device *g_pd3dDevice = NULL;
static ID3D11DeviceContext *g_pd3dDeviceContext = NULL;
static IDXGISwapChain *g_pSwapChain = NULL;
static ID3D11RenderTargetView *g_mainRenderTargetView = NULL;
static auto WINDOW_ID = "JIMGUI_WINDOW";

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

// see https://github.com/Microsoft/DirectXTex/blob/94b06c90728a08c1eab43a190fe0376e8426cb1d/DDSTextureLoader/DDSTextureLoader.cpp#L914-L1145
JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(JNIEnv *env, jclass, jbyteArray _fileName) -> jlongArray {
	__JNI__FUNCTION__INIT__
	__get(Byte, fileName)
	int width, height, channels;
	auto *imageData = stbi_load(STR_J2C(fileName), &width, &height, &channels, 4);
	__release(Byte, fileName)
	if (imageData == nullptr) {
		return nullptr;
	}
	D3D11_TEXTURE1D_DESC desc;
	desc.Width = static_cast<UINT> (width);
	// desc.Height = static_cast<UINT> (height);
	desc.MipLevels = static_cast<UINT> (1);
	desc.ArraySize = static_cast<UINT> (1);
	desc.BindFlags = static_cast<UINT> (D3D11_BIND_SHADER_RESOURCE);
	switch (channels) {
		case 3:
		case 4:
			desc.Format = DXGI_FORMAT_R8G8B8A8_UINT;
			break;
		default:
			delete[] imageData;
			return nullptr;
	}
	desc.Usage = D3D11_USAGE_IMMUTABLE;
	desc.CPUAccessFlags = 0;
	ID3D11Texture1D *tex = nullptr;
	ID3D11ShaderResourceView *resourceView = nullptr;
	HRESULT hr = E_FAIL;
	hr = g_pd3dDevice->CreateTexture1D(&desc, PTR_J2C(const D3D11_SUBRESOURCE_DATA, imageData), &tex);
	if (SUCCEEDED(hr) && tex != nullptr) {
		D3D11_SHADER_RESOURCE_VIEW_DESC SRVDesc = {};
		SRVDesc.Format = desc.Format;
		SRVDesc.ViewDimension = D3D11_SRV_DIMENSION_TEXTURE1D;
		SRVDesc.Texture1D.MipLevels = 1;
		hr = g_pd3dDevice->CreateShaderResourceView(tex, &SRVDesc, &resourceView);
		if (FAILED(hr)) {
			tex->Release();
			delete[] imageData;
			return nullptr;
		}
	}
	__release(Byte, fileName)
	auto ret = new jlong[4];
	ret[0] = PTR_C2J(resourceView);
	ret[1] = static_cast<jlong> (width);
	ret[2] = static_cast<jlong> (height);
	ret[3] = static_cast<jlong> (channels);
	__init(Long, ret, 4);
	delete[] ret;
	__JNI__FUNCTION__CLEAN__
	return _ret;
}

struct NativeObject {
	HWND hwnd;
	MSG msg;
	WNDCLASSEX wc;

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

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jbyteArray _title) -> jlong {
	__JNI__FUNCTION__INIT__
	__get(Byte, title);

	// Create application window
	auto object = new NativeObject(width, height, reinterpret_cast<Ptr<const char>> (title));

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
	__release(Byte, title);
	__JNI__FUNCTION__CLEAN__
	return reinterpret_cast<jlong> (object);
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_windowShouldClose(JNIEnv *, jclass, jlong nativeObjectPtr) -> jboolean {
	auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
	return static_cast<jboolean> (object->msg.message == WM_QUIT ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_initNewFrame(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
	while (object->msg.message != WM_QUIT && PeekMessage(&object->msg, NULL, 0U, 0U, PM_REMOVE)) {
		TranslateMessage(&object->msg);
		DispatchMessage(&object->msg);
	}
	ImGui_ImplDX11_NewFrame();
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_render(JNIEnv *, jclass, jlong, jlong colorPtr) {
	auto clear_color = reinterpret_cast<Ptr<ImVec4>> (colorPtr);
// Rendering
	g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
	g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, (float *) clear_color);
	ImGui::Render();
	ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());

	g_pSwapChain->Present(1, 0); // Present with vsync
//g_pSwapChain->Present(0, 0); // Present without vsync
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
	ImGui_ImplDX11_Shutdown();
	ImGui::DestroyContext();

	CleanupDeviceD3D();
	DestroyWindow(object->hwnd);
	UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
	delete object;
}


#pragma clang diagnostic pop
