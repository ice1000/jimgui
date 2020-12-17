///
/// Created by ice1000 on 18-5-8.
/// [WARNING] OBSOLETE, see dx9_impl.cpp
///

#include <imgui.h>
#include <imgui_impl_dx11.h>
#include <win32_impl.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImTextureID.h>

#define STB_IMAGE_IMPLEMENTATION
#define STBI_NO_LINEAR
#define STBI_NO_HDR
#define STBI_NO_GIF

#include <stb_image.h>

#include <d3d11.h>
#include <WinUser.h>

#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#endif

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

// Data
static ID3D11Device *g_pd3dDevice = NULL;
static ID3D11DeviceContext *g_pd3dDeviceContext = NULL;
static IDXGISwapChain *g_pSwapChain = NULL;
static ID3D11RenderTargetView *g_mainRenderTargetView = NULL;

// see https://github.com/Microsoft/DirectXTex/blob/94b06c90728a08c1eab43a190fe0376e8426cb1d/DDSTextureLoader/DDSTextureLoader.cpp#L914-L1145
JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(JNIEnv *env, jclass, jbyteArray _fileName) -> jlongArray {
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
  return _ret;
}

void CreateRenderTarget() {
  ID3D11Texture2D *pBackBuffer;
  g_pSwapChain->GetBuffer(0, __uuidof(ID3D11Texture2D), (LPVOID *) &pBackBuffer);
  g_pd3dDevice->CreateRenderTargetView(pBackBuffer, nullptr, &g_mainRenderTargetView);
  pBackBuffer->Release();
}

void CleanupRenderTarget() {
  if (g_mainRenderTargetView) {
    g_mainRenderTargetView->Release();
    g_mainRenderTargetView = nullptr;
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
      nullptr, D3D_DRIVER_TYPE_HARDWARE, nullptr, createDeviceFlags, featureLevelArray, 2,
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
    g_pSwapChain = nullptr;
  }
  if (g_pd3dDeviceContext) {
    g_pd3dDeviceContext->Release();
    g_pd3dDeviceContext = nullptr;
  }
  if (g_pd3dDevice) {
    g_pd3dDevice->Release();
    g_pd3dDevice = NULL;
  }
}

extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

void dispatchMessage(const NativeObject *object);

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
      void setupImgui(jlong nativeObjectPtr, jlong fontAtlas);

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

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(
    jint width,
    jint height,
    Ptr<jbyte> title,
    jlong anotherWindow
) -> jlong {
  return 0;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
    JNIEnv *env,
    jclass,
    jint width,
    jint height,
    jlong,
    jbyteArray _title,
    jlong) -> jlong {
  __get(Byte, title);

  // Create application window
  ImGui_ImplWin32_EnableDpiAwareness();
  auto *object = new NativeObject(width, height, STR_J2C(title));
  __release(Byte, title);
  // Initialize Direct3D
  if (CreateDeviceD3D(object->hwnd) < 0) {
    CleanupDeviceD3D();
    UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
    return 0;
  }
  return PTR_C2J(object);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setupImguiSpecificObjects(jlong nativeObjectPtr, jlong fontAtlas) {
  setupImgui(nativeObjectPtr, fontAtlas);
  ImGui_ImplDX11_Init(g_pd3dDevice, g_pd3dDeviceContext);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(jlong nativeObjectPtr) {
  dispatchMessage(PTR_J2C(NativeObject, nativeObjectPtr));
  ImGui_ImplDX11_NewFrame();
  ImGui_ImplWin32_NewFrame();
  ImGui::NewFrame();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_render(jlong, jlong colorPtr) {
  auto clear_color = reinterpret_cast<Ptr<ImVec4>> (colorPtr);
  // Rendering
  ImGui::Render();
  g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
  g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, (float *) clear_color);
  ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());

  g_pSwapChain->Present(1, 0); // Present with vsync
  //g_pSwapChain->Present(0, 0); // Present without vsync
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(jlong nativeObjectPtr) {
  ImGui_ImplDX11_Shutdown();
  ImGui_ImplWin32_Shutdown();
  ImGui::DestroyContext();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateGuiFramework(jlong nativeObjectPtr) {
  auto object = PTR_J2C(NativeObject, nativeObjectPtr);
  CleanupDeviceD3D();
  DestroyWindow(object->hwnd);
  UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
  delete object;
}

#ifndef WIN32
#pragma clang diagnostic pop
#endif
