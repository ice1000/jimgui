//
// Created by ice1000 on 18-7-3.
//

#ifndef HUGE_IMGUI_EXT_H
#define HUGE_IMGUI_EXT_H

#include <basics.hpp>
#include <imgui.h>

using ComVec2 = Com<ImVec2>;
using RefVec2 = Ref<ImVec2>;

using ComVec4 = Com<ImVec4>;
using RefVec4 = Ref<ImVec4>;

#define IM_VEC2_OP(op) \
static inline ImVec2 operator op(ComVec2 lhs, ComVec2 rhs) { return {lhs.x op rhs.x, lhs.y op rhs.y}; } \
static inline ImVec2 operator op(ComVec2 lhs, const float rhs) { return {lhs.x op rhs, lhs.y op rhs}; } \
static inline RefVec2 operator op ## =(RefVec2 lhs, ComVec2 rhs) { \
  lhs.x op ## = rhs.x; \
  lhs.y op ## = rhs.y; \
  return lhs; \
}

IM_VEC2_OP(+)
IM_VEC2_OP(-)
IM_VEC2_OP(*)
IM_VEC2_OP(/)

#undef IM_VEC2_OP

static constexpr inline bool operator==(ComVec2 lhs, ComVec2 rhs) { return lhs.x == rhs.x && lhs.y == rhs.y; }
static constexpr inline bool operator!=(ComVec2 lhs, ComVec2 rhs) { return !(lhs == rhs); }

#define IM_VEC4_OP(op) \
static inline ImVec4 operator op(ComVec4 lhs, ComVec4 rhs) { \
  return {lhs.x op rhs.x, lhs.y op rhs.y, lhs.z op rhs.z, lhs.w op rhs.w}; \
} \
static inline RefVec4 operator op ## =(RefVec4 lhs, ComVec4 rhs) { \
  lhs.x op ## = rhs.x; \
  lhs.y op ## = rhs.y; \
  lhs.z op ## = rhs.z; \
  lhs.w op ## = rhs.w; \
  return lhs; \
}

IM_VEC4_OP(+)
IM_VEC4_OP(-)
IM_VEC4_OP(*)
IM_VEC4_OP(/)

#undef IM_VEC4_OP

namespace ImGui {
	auto EmptyButton(ComVec4 bounds) -> bool;
	auto SetDisableHighlight(bool newValue) -> void;
	auto GetDisableHighlight() -> bool;
	auto DragVec4(RawStr name, RefVec4 val, float speed = 1, float min = 0, float max = 0, ImGuiSliderFlags flags = 0) -> void;
	auto SliderVec4(RawStr name, RefVec4 val, float min = 0, float max = 100, ImGuiSliderFlags flags = 0) -> void;
	auto DragDouble(RawStr label,
	                Ptr<double> v,
	                float v_speed = 1.0f,
	                double v_min = .0,
	                double v_max = .0,
	                RawStr format = "%.6lf",
	                ImGuiSliderFlags flags = 0) -> bool;
	auto LineTo(ComVec2 delta, ComVec4 color, float thickness = 1.0f) -> void;
	auto LineTo(ComVec2 delta, float thickness = 1.0f) -> void;
	/// if @param thickness < 0, circle will be filled
	auto Circle(float radius, ComVec4 color, int num_segments = 12, float thickness = 1.0f) -> void;
	/// if @param thickness < 0, circle will be filled
	auto Circle(float radius, int num_segments = 12, float thickness = 1.0f) -> void;
	size_t BeginRotate() noexcept;
	auto RotationCenter(size_t rotation_start_index) -> ImVec2;
	auto EndRotate(float rad, size_t rotation_start_index, ImVec2 center) -> void;
	auto EndRotate(float rad, size_t rotation_start_index) -> void;

	/// if @param thickness < 0, rect will be filled
	auto Rect(ComVec2 size,
	          ComVec4 color = ImGui::GetStyle().Colors[ImGuiCol_Button],
	          float rounding = 0.0f,
	          float thickness = 1.0f,
	          int rounding_corners_flags = ImDrawCornerFlags_All) -> void;
	/// if @param thickness < 0, rect will be filled
	auto DrawRect(ComVec4 border,
	              ComVec4 color = ImGui::GetStyle().Colors[ImGuiCol_Button],
	              float rounding = 0.0f,
	              float thickness = 1.0f,
	              int rounding_corners_flags = ImDrawCornerFlags_All) -> void;
	/// if @param thickness < 0, rect will be filled
	auto DrawRect(ComVec2 pos,
	              ComVec2 size,
	              ComVec4 color = ImGui::GetStyle().Colors[ImGuiCol_Button],
	              float rounding = 0.0f,
	              float thickness = 1.0f,
	              int rounding_corners_flags = ImDrawCornerFlags_All) -> void;

	auto BufferingBar(float value,
	                  ComVec2 size,
	                  ComVec4 bg_col = ImGui::GetStyle().Colors[ImGuiCol_TextDisabled],
	                  ComVec4 fg_col = ImGui::GetStyle().Colors[ImGuiCol_Button]) -> void;
	auto Spinner(float radius,
	             float thickness = 6.0f,
	             int num_segments = 30,
	             ComVec4 color = ImGui::GetStyle().Colors[ImGuiCol_Button]) -> void;

#ifdef DialogBox
#undef DialogBox
#endif // DialogBox

	auto DialogBox(RawStr title,
	               RawStr text,
	               ComVec2 windowSize,
	               Ptr<bool> pOpen = nullptr,
	               float percentageOnScreen = .2f) -> void;
}

#endif //HUGE_IMGUI_EXT_H
