//
// Created by ice10 on 2019/7/12.
//

#include "win32_impl.h"
#include <string>

NativeObject::NativeObject(jint width, jint height, Ptr<const char> title) : wc{
    sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L,
    0L,
    GetModuleHandle(nullptr), nullptr,
    nullptr, nullptr, nullptr, _T(WINDOW_ID),
    nullptr
}, msg{} {
  RegisterClassEx(&wc);
  ZeroMemory(&msg, sizeof msg);
  hwnd = CreateWindow(
      _T(WINDOW_ID), _T(title), WS_OVERLAPPEDWINDOW,
      100, 100, width, height, nullptr, nullptr, wc.hInstance, nullptr);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(jlong nativeObjectPtr) -> jboolean {
  auto object = PTR_J2C(NativeObject, nativeObjectPtr);
  return static_cast<jboolean> (object->msg.message == WM_QUIT ? JNI_TRUE : JNI_FALSE);
}

void setupImgui(jlong nativeObjectPtr, jlong fontAtlas) {
  auto *object = PTR_J2C(NativeObject, nativeObjectPtr);
  ShowWindow(object->hwnd, SW_SHOWDEFAULT);
  UpdateWindow(object->hwnd);
  IMGUI_CHECKVERSION();
  ImGui::CreateContext(PTR_J2C(ImFontAtlas, fontAtlas));
  ImGuiIO &io = ImGui::GetIO();
  // Enable Keyboard Controls
  io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
  ImGui_ImplWin32_Init(object->hwnd);
}

void dispatchMessage(NativeObject *object) {
  while (object->msg.message != WM_QUIT && PeekMessage(&object->msg, NULL, 0U, 0U, PM_REMOVE)) {
    TranslateMessage(&object->msg);
    DispatchMessage(&object->msg);
  }
}

#define DEFINE_PLATFORM_WINDOW_FUNCTIONS(name, expr) \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindow ## name(jlong nativeObjectPtr) -> float { \
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr); \
  RECT rect{}; \
  GetWindowRect(wc->hwnd, &rect); \
  return static_cast<float>(expr); \
}

DEFINE_PLATFORM_WINDOW_FUNCTIONS(SizeX, rect.right - rect.left)
DEFINE_PLATFORM_WINDOW_FUNCTIONS(SizeY, rect.bottom - rect.top)
DEFINE_PLATFORM_WINDOW_FUNCTIONS(PosX, rect.left)
DEFINE_PLATFORM_WINDOW_FUNCTIONS(PosY, rect.top)

#undef DEFINE_PLATFORM_WINDOW_FUNCTIONS

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSize(jlong nativeObjectPtr, float newX, float newY) {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  SetWindowPos(
      wc->hwnd,
      nullptr,
      0,
      0,
      static_cast<int>(newX),
      static_cast<int>(newY),
      SWP_NOMOVE | SWP_NOZORDER);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPos(jlong nativeObjectPtr, float newX, float newY) {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  SetWindowPos(
      wc->hwnd,
      nullptr,
      static_cast<int>(newX),
      static_cast<int>(newY),
      0,
      0,
      SWP_NOSIZE | SWP_NOZORDER);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setWindowTitle(jlong nativeObjectPtr, jint, jbyte* title){
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  SetWindowTextA(wc->hwnd, STR_J2C(title));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setWindowTitlePtr(jlong nativeObjectPtr, jlong titlePtr) {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  SetWindowTextA(wc->hwnd, PTR_J2C(std::string, titlePtr)->c_str());
}
