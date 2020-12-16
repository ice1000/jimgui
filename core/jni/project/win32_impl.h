//
// Created by ice10 on 2019/7/12.
//

#ifndef JIMGUI_WIN32_IMPL_H
#define JIMGUI_WIN32_IMPL_H

#include <imgui.h>
#include <Windows.h>

namespace jimgui {
	auto getWindowBounds(HWND hwnd) -> ImVec4;
	auto setWindowBounds(HWND hwnd, ImVec4) -> void;
}

extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

#endif //JIMGUI_WIN32_IMPL_H
