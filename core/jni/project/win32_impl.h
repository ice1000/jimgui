//
// Created by ice10 on 2019/7/12.
//

#ifndef JIMGUI_WIN32_IMPL_H
#define JIMGUI_WIN32_IMPL_H

#define DIRECTINPUT_VERSION 0x0800

#include <imgui_impl_win32.h>
#include <basics.hpp>
#include <dinput.h>
#include <tchar.h>
#include <winuser.h>
#include <impl_header.h>

static auto WINDOW_ID = "JIMGUI_WINDOW";

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

struct NativeObject {
  HWND hwnd;
  MSG msg;
  WNDCLASSEX wc;

  NativeObject(jint width, jint height, Ptr<const char> title);
};

void dispatchMessage(NativeObject *object);
void setupImgui(jlong nativeObjectPtr, jlong fontAtlas);

extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

#endif //JIMGUI_WIN32_IMPL_H
