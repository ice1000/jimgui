//
// Created by ice10 on 2019/7/12.
//

#include "win32_impl.h"

auto jimgui::getWindowBounds(HWND hwnd) -> ImVec4 {
	RECT rect{};
	GetWindowRect(hwnd, &rect);
	return {
			static_cast<float>(rect.left),
			static_cast<float>(rect.top),
			static_cast<float>(rect.right - rect.left),
			static_cast<float>(rect.bottom - rect.top),
	};
}

auto jimgui::setWindowBounds(HWND hwnd, ImVec4 bounds) -> void {
	SetWindowPos(hwnd, nullptr,
	             static_cast<int>(bounds.x),
	             static_cast<int>(bounds.y),
	             static_cast<int>(bounds.z),
	             static_cast<int>(bounds.w),
	             0);
}
