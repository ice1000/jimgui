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

HRESULT initTexture(Ptr<void> imageData, ID3D11ShaderResourceView **texture, int width, int height) {
  HRESULT hr{S_OK};

  // Create texture
  D3D11_TEXTURE2D_DESC desc;
  desc.Width = width;
  desc.Height = height;
  desc.MipLevels = 1;
  desc.ArraySize = 1;
  desc.Format = DXGI_FORMAT_R8G8B8A8_UNORM;
  desc.SampleDesc.Count = 1;
  desc.SampleDesc.Quality = 0;
  desc.Usage = D3D11_USAGE_DEFAULT;
  desc.CPUAccessFlags = 0;
  desc.BindFlags = D3D11_BIND_SHADER_RESOURCE;
  desc.MiscFlags = 0;

  size_t bpp = 32;
  size_t rowPitch = (width * bpp + 7) / 8;
  size_t imageSize = rowPitch * height;

  D3D11_SUBRESOURCE_DATA initData;
  initData.pSysMem = imageData;
  initData.SysMemPitch = static_cast<UINT>(rowPitch);
  initData.SysMemSlicePitch = static_cast<UINT>(imageSize);

  ID3D11Texture2D* tex = nullptr;
  hr = g_pd3dDevice->CreateTexture2D(
      &desc,
      &initData,
      &tex);
  if (SUCCEEDED(hr) && tex) {
    D3D11_SHADER_RESOURCE_VIEW_DESC srvDesc{};
    srvDesc.Format = desc.Format;
    srvDesc.ViewDimension = D3D11_SRV_DIMENSION_TEXTURE2D;
    srvDesc.Texture2D.MipLevels = 1;
    srvDesc.Texture2D.MostDetailedMip = 0;

    ID3D11ShaderResourceView* localTexture = nullptr;
    hr = g_pd3dDevice->CreateShaderResourceView(tex, &srvDesc, &localTexture);
    if (SUCCEEDED(hr) && localTexture)
      *texture = localTexture;
    tex->Release();
  }

  return hr;
}

// see https://github.com/Microsoft/DirectXTex/blob/94b06c90728a08c1eab43a190fe0376e8426cb1d/DDSTextureLoader/DDSTextureLoader.cpp#L914-L1145
JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(JNIEnv *env, jclass, jbyteArray _fileName) -> jlongArray {
  __get(Byte, fileName)
  int channels;
  int forceChannels = 4;
  int w, h;
  auto *imageData = stbi_load(STR_J2C(fileName), &w, &h, &channels, forceChannels);
  __release(Byte, fileName)
  ImTextureID texture;
  if (nullptr != imageData) {
    ID3D11ShaderResourceView* localTexture = nullptr;
    if (SUCCEEDED(initTexture(imageData, &localTexture, w, h))) {
      texture = reinterpret_cast<ImTextureID>(localTexture);
    }

    stbi_image_free(imageData);
  }
  auto ret = new jlong[3];
  ret[0] = PTR_C2J(texture);
  ret[1] = static_cast<jlong> (w);
  ret[2] = static_cast<jlong> (h);
  __init_array(Long, ret, 3);
  delete[] ret;
  __release(Long, ret);
  return _ret;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromBytes(
    Ptr<JNIEnv> env,
    jclass,
    jbyteArray _rawData,
    jint size
) -> jlongArray {
  __get(Byte, rawData)
  int channels;
  int forceChannels = 4;
  int w, h;
  auto *imageData = stbi_load_from_memory(PTR_J2C(stbi_uc, rawData), size, &w, &h, &channels, forceChannels);
  __release(Byte, rawData)
  ImTextureID texture;
  if (nullptr != imageData) {
    ID3D11ShaderResourceView* localTexture = nullptr;
    if (SUCCEEDED(initTexture(imageData, &localTexture, w, h))) {
      texture = reinterpret_cast<ImTextureID>(localTexture);
    }
    stbi_image_free(imageData);
  }

  auto ret = new jlong[3];
  ret[0] = PTR_C2J(texture);
  ret[1] = static_cast<jlong> (w);
  ret[2] = static_cast<jlong> (h);
  __init_array(Long, ret, 3);
  delete[] ret;
  __release(Long, ret);
  return _ret;
}

void CreateRenderTarget() {
  ID3D11Texture2D *pBackBuffer;
  g_pSwapChain->GetBuffer(0, IID_PPV_ARGS(&pBackBuffer));
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
  ZeroMemory(&sd, sizeof sd);
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
    return false;

  CreateRenderTarget();

  return true;
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
    g_pd3dDevice = nullptr;
  }
}

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam) {
  if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
    return true;

  switch (msg) {
    case WM_SIZE:
      if (g_pd3dDevice != NULL && wParam != SIZE_MINIMIZED) {
        CleanupRenderTarget();
        g_pSwapChain->ResizeBuffers(0, (UINT) LOWORD(lParam), (UINT) HIWORD(lParam), DXGI_FORMAT_UNKNOWN, 0);
        CreateRenderTarget();
      }
      return 0;
    case WM_SYSCOMMAND:
      if ((wParam & 0xfff0) == SC_KEYMENU) // Disable ALT application menu
        return 0;
      break;
    case WM_DESTROY:
      ::PostQuitMessage(0);
      return 0;
  }
  return ::DefWindowProc(hWnd, msg, wParam, lParam);
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
  // see https://github.com/ocornut/imgui/issues/2496#issuecomment-483357656
  static UINT presentFlags = 0;
  if (g_pSwapChain->Present(1, presentFlags) == DXGI_STATUS_OCCLUDED) {
    presentFlags = DXGI_PRESENT_TEST;
    Sleep(20);
  } else {
    presentFlags = 0;
  }

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
