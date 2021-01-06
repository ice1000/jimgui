#include <imgui.h>
#include <imgui_impl_dx9.h>
#include <win32_impl.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImTextureID.h>

#include <d3d9.h>
#include <d3dx9tex.h>

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
static LPDIRECT3DDEVICE9 g_pd3dDevice = nullptr;
static D3DPRESENT_PARAMETERS g_d3dpp = {};
static LPDIRECT3D9 g_pD3D = nullptr;
bool CreateDeviceD3D(HWND hWnd);
void CleanupDeviceD3D();
void ResetDevice();

//extern LRESULT D3DXCreateTextureFromFile(LPDIRECT3DDEVICE9, Ptr<const char>, Ptr<LPDIRECT3DTEXTURE9>);

auto loadTexture(Ptr<const char> fileName, LPDIRECT3DTEXTURE9 &texture) -> bool {
  return SUCCEEDED(D3DXCreateTextureFromFile(g_pd3dDevice, fileName, &texture));
}

auto loadTextureInMemory(Ptr<void> rawData, size_t size, LPDIRECT3DTEXTURE9 &texture) -> bool {
  return SUCCEEDED(D3DXCreateTextureFromFileInMemory(g_pd3dDevice, rawData, size, &texture));
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(
    Ptr<JNIEnv> env,
    jclass,
    jbyteArray _fileName
) -> jlongArray {
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
  __init_array(Long, ret, RET_LEN);
#undef RET_LEN
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
  LPDIRECT3DTEXTURE9 texture;
  auto success = loadTextureInMemory(PTR_J2C(void, rawData), size, texture);
  __release(Byte, rawData)
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
  __init_array(Long, ret, RET_LEN);
#undef RET_LEN
  delete[] ret;
  __release(Long, ret);
  return _ret;
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
    jlong
) -> jlong {
  // Create application window
  __get(Byte, title);
  ImGui_ImplWin32_EnableDpiAwareness();
  auto *object = new NativeObject(width, height, STR_J2C(title));

  __release(Byte, title);
  if ((g_pD3D = Direct3DCreate9(D3D_SDK_VERSION)) == nullptr) {
    UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
    return NULL;
  }
  ZeroMemory(&g_d3dpp, sizeof g_d3dpp);
  g_d3dpp.Windowed = TRUE;
  g_d3dpp.SwapEffect = D3DSWAPEFFECT_DISCARD;
  g_d3dpp.BackBufferFormat = D3DFMT_UNKNOWN;
  g_d3dpp.EnableAutoDepthStencil = TRUE;
  g_d3dpp.AutoDepthStencilFormat = D3DFMT_D16;
  g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_ONE;
  // Present with vsync
  // g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_IMMEDIATE;
  // Present without vsync, maximum unthrottled framerate

  // Create the D3DDevice
  if (!CreateDeviceD3D(object->hwnd)) {
    CleanupDeviceD3D();
    ::UnregisterClass(_T(WINDOW_ID), object->wc.hInstance);
    return 0;
  }
  return PTR_C2J(object);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setupImguiSpecificObjects(jlong nativeObjectPtr, jlong fontAtlas) {
  setupImgui(nativeObjectPtr, fontAtlas);
  ImGui_ImplDX9_Init(g_pd3dDevice);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(jlong nativeObjectPtr) {
  auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
  dispatchMessage(object);
  ImGui_ImplDX9_NewFrame();
  ImGui_ImplWin32_NewFrame();
  ImGui::NewFrame();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_render(jlong, jlong colorPtr) {
  auto clear_color = reinterpret_cast<Ptr<ImVec4>> (colorPtr);
  // Rendering
  ImGui::EndFrame();
  g_pd3dDevice->SetRenderState(D3DRS_ZENABLE, FALSE);
  g_pd3dDevice->SetRenderState(D3DRS_ALPHABLENDENABLE, FALSE);
  g_pd3dDevice->SetRenderState(D3DRS_SCISSORTESTENABLE, FALSE);
  D3DCOLOR clear_col_dx = D3DCOLOR_RGBA(
      (int) (clear_color->x * 255.0f),
      (int) (clear_color->y * 255.0f),
      (int) (clear_color->z * 255.0f),
      (int) (clear_color->w * 255.0f));
  g_pd3dDevice->Clear(
      0, nullptr, D3DCLEAR_TARGET | D3DCLEAR_ZBUFFER, clear_col_dx,
      1.0f, 0);
  if (g_pd3dDevice->BeginScene() >= 0) {
    ImGui::Render();
    ImGui_ImplDX9_RenderDrawData(ImGui::GetDrawData());
    g_pd3dDevice->EndScene();
  }
  HRESULT result = g_pd3dDevice->Present(NULL, NULL, NULL, NULL);

  // Handle loss of D3D9 device
  if (result == D3DERR_DEVICELOST &&
      g_pd3dDevice->TestCooperativeLevel() == D3DERR_DEVICENOTRESET) {
    ResetDevice();
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
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  CleanupDeviceD3D();
  DestroyWindow(wc->hwnd);
  UnregisterClass(_T(WINDOW_ID), wc->wc.hInstance);
  delete wc;
}

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam) {
  if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
    return true;

  switch (msg) {
    case WM_SIZE:
      if (g_pd3dDevice != nullptr && wParam != SIZE_MINIMIZED) {
        g_d3dpp.BackBufferWidth = LOWORD(lParam);
        g_d3dpp.BackBufferHeight = HIWORD(lParam);
        ResetDevice();
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

bool CreateDeviceD3D(HWND hWnd) {
  if ((g_pD3D = Direct3DCreate9(D3D_SDK_VERSION)) == nullptr)
    return false;

// Create the D3DDevice
  ZeroMemory(&g_d3dpp, sizeof(g_d3dpp));
  g_d3dpp.Windowed = TRUE;
  g_d3dpp.SwapEffect = D3DSWAPEFFECT_DISCARD;
  g_d3dpp.BackBufferFormat = D3DFMT_UNKNOWN;
  g_d3dpp.EnableAutoDepthStencil = TRUE;
  g_d3dpp.AutoDepthStencilFormat = D3DFMT_D16;
  g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_ONE;           // Present with vsync
//g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_IMMEDIATE;   // Present without vsync, maximum unthrottled framerate
  if (g_pD3D->CreateDevice(D3DADAPTER_DEFAULT, D3DDEVTYPE_HAL, hWnd, D3DCREATE_HARDWARE_VERTEXPROCESSING, &g_d3dpp,
                           &g_pd3dDevice) < 0)
    return false;

  return true;
}

void CleanupDeviceD3D() {
  if (g_pd3dDevice) {
    g_pd3dDevice->Release();
    g_pd3dDevice = nullptr;
  }
  if (g_pD3D) {
    g_pD3D->Release();
    g_pD3D = nullptr;
  }
}

void ResetDevice() {
  ImGui_ImplDX9_InvalidateDeviceObjects();
  HRESULT hr = g_pd3dDevice->Reset(&g_d3dpp);
  if (hr == D3DERR_INVALIDCALL)
    IM_ASSERT(0);
  ImGui_ImplDX9_CreateDeviceObjects();
}

#ifndef WIN32
#pragma clang diagnostic pop
#endif
