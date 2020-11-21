//
// Created by ice1000 on 18-7-3.
//

#include <imgui_ext.h>
#include <imgui_internal.h>

auto ImGui::LineTo(ComVec2 delta, ComVec4 color, const float thickness) -> void {
	ImGuiWindow *window = GetCurrentWindow();
	if (window->SkipItems) return;
	auto &cursorPos = window->DC.CursorPos;
	ImRect bb{cursorPos, cursorPos + delta};
	ItemSize(bb);
	if (!ItemAdd(bb, 0)) return;
	window->DrawList->AddLine(bb.Min, bb.Max, GetColorU32(color), thickness);
}

auto ImGui::LineTo(ComVec2 delta, const float thickness) -> void {
	LineTo(delta, ImGui::GetStyle().Colors[ImGuiCol_PlotLines], thickness);
}

auto ImGui::Rect(ComVec2 size,
                 ComVec4 color,
                 const float rounding,
                 const float thickness,
                 const int rounding_corners_flags) -> void {
	ImGuiWindow *window = GetCurrentWindow();
	if (window->SkipItems) return;
	auto &cursorPos = window->DC.CursorPos;
	ImRect bb{cursorPos, cursorPos + size};
	ItemSize(bb);
	if (!ItemAdd(bb, 0)) return;
	if (thickness >= 0)
		window->DrawList->AddRect(bb.Min, bb.Max, GetColorU32(color), rounding, rounding_corners_flags, thickness);
	else window->DrawList->AddRectFilled(bb.Min, bb.Max, GetColorU32(color), rounding, rounding_corners_flags);
}

auto ImGui::Circle(const float radius, ComVec4 color, const int num_segments, const float thickness) -> void {
	ImGuiWindow *window = GetCurrentWindow();
	if (window->SkipItems) return;
	auto &cursorPos = window->DC.CursorPos;
	const auto &&centre = cursorPos + radius;
	ImRect bb{cursorPos, centre + radius};
	ItemSize(bb);
	if (!ItemAdd(bb, 0)) return;
	if (thickness >= 0)
		window->DrawList->AddCircle(centre, radius, GetColorU32(color), num_segments, thickness);
	else window->DrawList->AddCircleFilled(centre, radius, GetColorU32(color), num_segments);
}

auto ImGui::Circle(const float radius, const int num_segments, const float thickness) -> void {
	Circle(radius, ImGui::GetStyle().Colors[ImGuiCol_Button], num_segments, thickness);
}

auto ImGui::DrawRect(ComVec4 border,
                     ComVec4 color,
                     const float rounding,
                     const float thickness,
                     const int rounding_corners_flags) -> void {
	DrawRect({border.x, border.y}, {border.z, border.w}, color, rounding, thickness, rounding_corners_flags);
}

auto ImGui::DrawRect(ComVec2 pos,
                     ComVec2 size,
                     ComVec4 color,
                     const float rounding,
                     const float thickness,
                     const int rounding_corners_flags) -> void {
	auto &&a = pos + ImGui::GetWindowPos();
	auto &&b = a + size;
	if (thickness >= 0)
		ImGui::GetWindowDrawList()->AddRect(a, b, GetColorU32(color), rounding, rounding_corners_flags, thickness);
	else ImGui::GetWindowDrawList()->AddRectFilled(a, b, GetColorU32(color), rounding, rounding_corners_flags);
}

auto ImGui::EmptyButton(ComVec4 bounds) -> bool {
	ImGui::SetCursorPos({bounds.x, bounds.y});
	return ImGui::Button("", {
			bounds.z < 0.00001f ? 0.00001f : bounds.z,
			bounds.w < 0.00001f ? 0.00001f : bounds.w});
}

auto ImGui::DragVec4(RawStr name, RefVec4 val, float speed, float min, float max, ImGuiSliderFlags flags) -> void {
	ImGui::DragFloat4(name, &val.x, speed, min, max, "%.3f", flags);
}

auto ImGui::SliderVec4(RawStr name, RefVec4 val, float min, float max, ImGuiSliderFlags flags) -> void {
	ImGui::SliderFloat4(name, &val.x, min, max, "%.3f", flags);
}

auto ImGui::DragDouble(RawStr label,
                       Ptr<double> v,
                       float v_speed,
                       double v_min,
                       double v_max,
                       RawStr format,
                       ImGuiSliderFlags flags) -> bool {
	return DragScalar(label, ImGuiDataType_Double, v, v_speed, &v_min, &v_max, format, flags);
}

auto ImGui::DialogBox(RawStr title,
                      RawStr text,
                      ComVec2 windowSize,
                      Ptr<bool> pOpen,
                      const float percentageOnScreen) -> void {
	ImGui::SetNextWindowPos({0, windowSize.y * (1 - percentageOnScreen)});
	ImGui::SetNextWindowSize({windowSize.x, windowSize.y * percentageOnScreen});
	if (Begin(title, pOpen, ImGuiWindowFlags_None | // NOLINT
	                        ImGuiWindowFlags_NoCollapse |
	                        ImGuiWindowFlags_NoNav |
	                        ImGuiWindowFlags_NoSavedSettings |
	                        ImGuiWindowFlags_NoMove |
	                        ImGuiWindowFlags_NoResize)) {
		ImGui::TextUnformatted(text);
		End();
	}
}

auto ImGui::RotationCenter(size_t rotation_start_index) -> ImVec2 {
	ImVec2 l{FLT_MAX, FLT_MAX}, u{-FLT_MAX, -FLT_MAX}; // bounds

	const auto &buf = ImGui::GetWindowDrawList()->VtxBuffer;
	for (size_t i = rotation_start_index; i < buf.Size; i++)
		l = ImMin(l, buf[i].pos), u = ImMax(u, buf[i].pos);

	return {(l.x + u.x) / 2, (l.y + u.y) / 2}; // or use _ClipRectStack?
}

auto ImGui::EndRotate(float rad, size_t rotation_start_index) -> void {
	EndRotate(rad, rotation_start_index, RotationCenter(rotation_start_index));
}

auto ImGui::EndRotate(float rad, size_t rotation_start_index, ImVec2 center) -> void {
	float s = ImSin(rad), c = ImCos(rad);
	center = ImRotate(center, s, c) - center;

	auto &buf = ImGui::GetWindowDrawList()->VtxBuffer;
	for (size_t i = rotation_start_index; i < buf.Size; i++)
		buf[i].pos = ImRotate(buf[i].pos, s, c) - center;
}

auto ImGui::Spinner(float radius, float thickness, int num_segments, ComVec4 color) -> void {
	auto window = GetCurrentWindow();
	if (window->SkipItems)
		return;

	auto &g = *GImGui;
	const auto &style = g.Style;
	auto &&pos = ImGui::GetCursorPos();
	ImVec2 size{radius * 2, radius * 2};
	const ImRect bb{pos, pos + size};
	ItemSize(bb);
	if (!ItemAdd(bb, 0))
		return;

	window->DrawList->PathClear();
	int start = static_cast<int>(fabsf(ImSin(static_cast<float>(g.Time * 1.8f)) * (num_segments - 5)));
	const float a_min = IM_PI * 2.0f * ((float) start) / (float) num_segments;
	const float a_max = IM_PI * 2.0f * ((float) num_segments - 3) / (float) num_segments;
	const auto &&centre = pos + radius;
	for (auto i = 0; i < num_segments; i++) {
		const float a = a_min + ((float) i / (float) num_segments) * (a_max - a_min);
		auto time = static_cast<float>(g.Time);
		window->DrawList->PathLineTo({centre.x + ImCos(a + time * 8) * radius,
		                              centre.y + ImSin(a + time * 8) * radius});
	}
	window->DrawList->PathStroke(GetColorU32(color), false, thickness);
}

auto ImGui::BufferingBar(float value, ComVec2 size, ComVec4 bg_col, ComVec4 fg_col) -> void {
	auto window = GetCurrentWindow();
	if (window->SkipItems)
		return;

	auto &g = *GImGui;
	const auto &style = g.Style;
	auto &&pos = ImGui::GetCursorPos();
	const ImRect bb{pos, pos + size};
	ItemSize(bb);
	if (!ItemAdd(bb, 0))
		return;

	// Render
	const auto circleStart = size.x * 0.7f;
	const auto circleEnd = size.x;
	const auto circleWidth = circleEnd - circleStart;

	window->DrawList->AddRectFilled(bb.Min, {pos.x + circleStart, bb.Max.y}, GetColorU32(bg_col));
	window->DrawList->AddRectFilled(bb.Min, {pos.x + circleStart * value, bb.Max.y}, GetColorU32(fg_col));

	const auto t = static_cast<float>(g.Time);
	const auto r = size.y / 2;
	const auto speed = 1.5f;

	const auto a = speed * 0;
	const auto b = speed * 0.333f;
	const auto c = speed * 0.666f;

	const float o1 = (circleWidth + r) * (t + a - speed * (int) ((t + a) / speed)) / speed;
	const float o2 = (circleWidth + r) * (t + b - speed * (int) ((t + b) / speed)) / speed;
	const float o3 = (circleWidth + r) * (t + c - speed * (int) ((t + c) / speed)) / speed;

	window->DrawList->AddCircleFilled({pos.x + circleEnd - o1, bb.Min.y + r}, r, GetColorU32(bg_col));
	window->DrawList->AddCircleFilled({pos.x + circleEnd - o2, bb.Min.y + r}, r, GetColorU32(bg_col));
	window->DrawList->AddCircleFilled({pos.x + circleEnd - o3, bb.Min.y + r}, r, GetColorU32(bg_col));
}

auto ImGui::SetDisableHighlight(bool newValue) -> void {
	ImGui::GetCurrentContext()->NavDisableHighlight = newValue;
}

auto ImGui::GetDisableHighlight() -> bool {
	return ImGui::GetCurrentContext()->NavDisableHighlight;
}

size_t ImGui::BeginRotate() noexcept {
	return ImGui::GetWindowDrawList()->VtxBuffer.Size;
}
