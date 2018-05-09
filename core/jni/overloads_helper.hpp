///
/// Created by ice1000 on 18-5-7.
///

#ifndef JIMGUI_GENERATED_HELPER_H
#define JIMGUI_GENERATED_HELPER_H

#include <imgui.h>

namespace ImGui {
		auto ListBoxHeader0(const char *label, int items_count, int height_in_items) -> bool;
		auto PushID0(const char *str_id_begin, const char *str_id_end) -> void;
		auto GetID0(const char *str_id_begin, const char *str_id_end) -> ImGuiID;
		auto PushID1(const char *str_id) -> void;
		auto PushStyleVarImVec2(ImGuiStyleVar idx, const ImVec2 &val) -> void;
		auto PushStyleVarFloat(ImGuiStyleVar idx, float val) -> void;
		auto BeginChild0(const char *str_id, const ImVec2 &size = ImVec2(0, 0), bool border = false, ImGuiWindowFlags flags = 0) -> bool;
}

#endif //JIMGUI_GENERATED_HELPER_H
