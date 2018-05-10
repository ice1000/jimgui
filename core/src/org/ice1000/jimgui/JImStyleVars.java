package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.JImStyleVar.*;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImStyleVars {
	@NotNull JImStyleVar<@NotNull Float> Alpha = new JImStyleVar<>(getAlpha());
	@NotNull JImStyleVar<@NotNull Void> WindowPadding = new JImStyleVar<>(getWindowPadding());
	@NotNull JImStyleVar<@NotNull Float> WindowRounding = new JImStyleVar<>(getWindowRounding());
	@NotNull JImStyleVar<@NotNull Float> WindowBorderSize = new JImStyleVar<>(getWindowBorderSize());
	@NotNull JImStyleVar<@NotNull Void> WindowMinSize = new JImStyleVar<>(getWindowMinSize());
	@NotNull JImStyleVar<@NotNull Void> WindowTitleAlign = new JImStyleVar<>(getWindowTitleAlign());
	@NotNull JImStyleVar<@NotNull Float> ChildRounding = new JImStyleVar<>(getChildRounding());
	@NotNull JImStyleVar<@NotNull Float> ChildBorderSize = new JImStyleVar<>(getChildBorderSize());
	@NotNull JImStyleVar<@NotNull Float> PopupRounding = new JImStyleVar<>(getPopupRounding());
	@NotNull JImStyleVar<@NotNull Float> PopupBorderSize = new JImStyleVar<>(getPopupBorderSize());
	@NotNull JImStyleVar<@NotNull Void> FramePadding = new JImStyleVar<>(getFramePadding());
	@NotNull JImStyleVar<@NotNull Float> FrameRounding = new JImStyleVar<>(getFrameRounding());
	@NotNull JImStyleVar<@NotNull Float> FrameBorderSize = new JImStyleVar<>(getFrameBorderSize());
	@NotNull JImStyleVar<@NotNull Void> ItemSpacing = new JImStyleVar<>(getItemSpacing());
	@NotNull JImStyleVar<@NotNull Void> ItemInnerSpacing = new JImStyleVar<>(getItemInnerSpacing());
	@NotNull JImStyleVar<@NotNull Float> IndentSpacing = new JImStyleVar<>(getIndentSpacing());
	@NotNull JImStyleVar<@NotNull Float> ScrollbarSize = new JImStyleVar<>(getScrollbarSize());
	@NotNull JImStyleVar<@NotNull Float> ScrollbarRounding = new JImStyleVar<>(getScrollbarRounding());
	@NotNull JImStyleVar<@NotNull Float> GrabMinSize = new JImStyleVar<>(getGrabMinSize());
	@NotNull JImStyleVar<@NotNull Float> GrabRounding = new JImStyleVar<>(getGrabRounding());
	@NotNull JImStyleVar<@NotNull Void> ButtonTextAlign = new JImStyleVar<>(getButtonTextAlign());
}
