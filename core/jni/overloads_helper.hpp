///
/// Created by ice1000 on 18-5-7.
///

#ifndef JIMGUI_GENERATED_HELPER_H
#define JIMGUI_GENERATED_HELPER_H

#include <imgui.h>
#include "basics.hpp"

namespace ImGui {
	auto ListBoxHeader0(Ptr<const char> label, int items_count, int height_in_items) -> bool;
	auto PushStyleVarImVec2(ImGuiStyleVar idx, const ImVec2 &val) -> void;
	auto PushStyleVarFloat(ImGuiStyleVar idx, float val) -> void;
	auto BeginChild0(
			Ptr<const char> str_id, const ImVec2 &size = ImVec2(0, 0),
			bool border = false, ImGuiWindowFlags flags = 0) -> bool;
	auto ColorEdit3(Ptr<const char> label, ImVec4 &col, ImGuiColorEditFlags flags = 0) -> bool;
	auto ColorEdit4(Ptr<const char> label, ImVec4 &col, ImGuiColorEditFlags flags = 0) -> bool;
	auto ColorPicker3(Ptr<const char> label, ImVec4 &col, ImGuiColorEditFlags flags = 0) -> bool;
	auto ColorPicker4(Ptr<const char> label, ImVec4 &col, ImGuiColorEditFlags flags = 0) -> bool;
	auto RadioButton0(Ptr<const char> label, bool active) -> bool;
	auto MenuItem0(Ptr<const char> label, Ptr<const char> shortcut, Ptr<bool> p_selected, bool enabled = true) -> bool;
}

#endif //JIMGUI_GENERATED_HELPER_H
