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

auto ImGui::PushStyleVarImVec2(ImGuiStyleVar idx, const ImVec2 &val) -> void {
	ImGui::PushStyleVar(idx, val);
}

auto ImGui::PushStyleVarFloat(ImGuiStyleVar idx, float val) -> void {
	ImGui::PushStyleVar(idx, val);
}

auto ImGui::BeginChild0(const char *str_id, const ImVec2 &size, bool border, ImGuiWindowFlags flags) -> bool {
	return BeginChild(str_id, size, border, flags);
}

auto ImGui::MenuItem0(const char *label, const char *shortcut, bool *p_selected, bool enabled) -> bool {
	return MenuItem(label, shortcut, p_selected, enabled);
}

#define ColorRelated(name) \
auto ImGui::Color ## name(const char *label, ImVec4 col, ImGuiColorEditFlags flags) -> bool { \
  return Color ## name(label, reinterpret_cast<float *> (&col), flags); \
}

ColorRelated(Edit3)
ColorRelated(Edit4)
ColorRelated(Picker3)
ColorRelated(Picker4)

#undef ColorRelated
