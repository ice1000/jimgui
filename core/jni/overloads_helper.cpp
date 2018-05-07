///
/// Created by ice1000 on 18-5-7.
///

#include "overloads_helper.hpp"

auto ImGui::ListBoxHeader0(const char *label, int items_count, int height_in_items) -> bool {
	return ListBoxHeader(label, items_count, height_in_items);
}

auto ImGui::PushID0(const char *str_id_begin, const char *str_id_end) -> void {
	PushID(str_id_begin, str_id_end);
}

auto ImGui::PushID1(const char *str_id) -> void {
	PushID(str_id);
}

auto ImGui::GetID0(const char *str_id_begin, const char *str_id_end) -> ImGuiID {
	return GetID(str_id_begin, str_id_end);
}
