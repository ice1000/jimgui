//
// Created by ice10 on 12/18/2020.
//

#include <imgui_impl_win32.h>
#include <basics.hpp>
#include <impl_header.h>
#include <win32_impl.h>
#include <dinput.h>
#include <tchar.h>
#include <winuser.h>

static auto WINDOW_ID = "JIMGUI_WINDOW";

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

struct NativeObject {
  HWND hwnd;
  MSG msg;
  WNDCLASSEX wc;

  NativeObject(jint width, jint height, Ptr<const char> title) : wc{
      sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L,
      0L,
      GetModuleHandle(nullptr), nullptr,
      nullptr, nullptr, nullptr, _T(WINDOW_ID),
      nullptr
  } {
    RegisterClassEx(&wc);
    ZeroMemory(&msg, sizeof msg);
    hwnd = CreateWindow(
        _T(WINDOW_ID), _T(title), WS_OVERLAPPEDWINDOW,
        100, 100, width, height, NULL, NULL, wc.hInstance, NULL);
  };
};

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initBeforeMainLoop(jlong nativeObjectPtr) {
  auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
  ShowWindow(object->hwnd, SW_SHOWDEFAULT);
  UpdateWindow(object->hwnd);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(jlong nativeObjectPtr) -> jboolean {
  auto object = reinterpret_cast<Ptr<NativeObject>> (nativeObjectPtr);
  return static_cast<jboolean> (object->msg.message == WM_QUIT ? JNI_TRUE : JNI_FALSE);
}

void dispatchMessage(NativeObject *object) {
  while (object->msg.message != WM_QUIT && PeekMessage(&object->msg, NULL, 0U, 0U, PM_REMOVE)) {
    TranslateMessage(&object->msg);
    DispatchMessage(&object->msg);
  }
}


JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(jlong nativeObjectPtr) -> float {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  RECT rect{};
  GetWindowRect(wc->hwnd, &rect);
  return static_cast<float>(rect.right - rect.left);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(jlong nativeObjectPtr) -> float {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  RECT rect{};
  GetWindowRect(wc->hwnd, &rect);
  return static_cast<float>(rect.bottom - rect.top);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(jlong nativeObjectPtr) -> float {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  RECT rect{};
  GetWindowRect(wc->hwnd, &rect);
  return static_cast<float>(rect.left);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(jlong nativeObjectPtr) -> float {
  auto wc = PTR_J2C(NativeObject, nativeObjectPtr);
  RECT rect{};
  GetWindowRect(wc->hwnd, &rect);
  return static_cast<float>(rect.top);
}

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
