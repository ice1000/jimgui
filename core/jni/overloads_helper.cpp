///
/// Created by ice1000 on 18-5-7.
///

#include "overloads_helper.hpp"

auto ImGui::Selectable0(Ptr<const char> label, bool selected, ImGuiSelectableFlags flags, const ImVec2 &size) -> bool {
	return Selectable(label, selected, flags, size);
}

auto ImGui::PushStyleVarImVec2(ImGuiStyleVar idx, const ImVec2 &val) -> void {
	ImGui::PushStyleVar(idx, val);
}

auto ImGui::PushStyleVarFloat(ImGuiStyleVar idx, float val) -> void {
	ImGui::PushStyleVar(idx, val);
}

auto ImGui::BeginChild0(Ptr<const char> str_id, const ImVec2 &size, bool border, ImGuiWindowFlags flags) -> bool {
	return BeginChild(str_id, size, border, flags);
}

#define ColorRelated(name) \
auto ImGui::Color ## name(Ptr<const char>label, ImVec4& col, ImGuiColorEditFlags flags) -> bool { \
  return Color ## name(label, PTR_J2C(float, &col), flags); \
}

ColorRelated(Edit3)
ColorRelated(Edit4)
ColorRelated(Picker3)
ColorRelated(Picker4)

auto ImGui::RadioButton0(const char *label, bool active) -> bool {
	return RadioButton(label, active);
}

auto ImGui::ListBoxHeader0(Ptr<const char> label, const ImVec2 &size) -> bool {
	return ListBoxHeader(label, size);
}

#undef ColorRelated
