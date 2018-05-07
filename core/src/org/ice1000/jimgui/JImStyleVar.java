package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

/**
 * @param <T> Corresponding style var type, {@link Float} or ImVec2(Use {@link Void} to represent)
 * @author ice1000
 * @since v0.1
 */
public final class JImStyleVar<T> {
	/**
	 * package-private by design
	 */
	int nativeValue;

	private JImStyleVar(int nativeValue) {
		this.nativeValue = nativeValue;
	}

	public static final @NotNull JImStyleVar<@NotNull Float> Alpha;
	public static final @NotNull JImStyleVar<@NotNull Void> WindowPadding;
	public static final @NotNull JImStyleVar<@NotNull Float> WindowRounding;
	public static final @NotNull JImStyleVar<@NotNull Float> WindowBorderSize;
	public static final @NotNull JImStyleVar<@NotNull Void> WindowMinSize;
	public static final @NotNull JImStyleVar<@NotNull Void> WindowTitleAlign;
	public static final @NotNull JImStyleVar<@NotNull Float> ChildRounding;
	public static final @NotNull JImStyleVar<@NotNull Float> ChildBorderSize;
	public static final @NotNull JImStyleVar<@NotNull Float> PopupRounding;
	public static final @NotNull JImStyleVar<@NotNull Float> PopupBorderSize;
	public static final @NotNull JImStyleVar<@NotNull Void> FramePadding;
	public static final @NotNull JImStyleVar<@NotNull Float> FrameRounding;
	public static final @NotNull JImStyleVar<@NotNull Float> FrameBorderSize;
	public static final @NotNull JImStyleVar<@NotNull Void> ItemSpacing;
	public static final @NotNull JImStyleVar<@NotNull Void> ItemInnerSpacing;
	public static final @NotNull JImStyleVar<@NotNull Float> IndentSpacing;
	public static final @NotNull JImStyleVar<@NotNull Float> ScrollbarSize;
	public static final @NotNull JImStyleVar<@NotNull Float> ScrollbarRounding;
	public static final @NotNull JImStyleVar<@NotNull Float> GrabMinSize;
	public static final @NotNull JImStyleVar<@NotNull Float> GrabRounding;
	public static final @NotNull JImStyleVar<@NotNull Void> ButtonTextAlign;
	public static final int COUNT;

	//region Trivial copy-pasted codes
	private static native int getAlpha();
	private static native int getWindowPadding();
	private static native int getWindowRounding();
	private static native int getWindowBorderSize();
	private static native int getWindowMinSize();
	private static native int getWindowTitleAlign();
	private static native int getChildRounding();
	private static native int getChildBorderSize();
	private static native int getPopupRounding();
	private static native int getPopupBorderSize();
	private static native int getFramePadding();
	private static native int getFrameRounding();
	private static native int getFrameBorderSize();
	private static native int getItemSpacing();
	private static native int getItemInnerSpacing();
	private static native int getIndentSpacing();
	private static native int getScrollbarSize();
	private static native int getScrollbarRounding();
	private static native int getGrabMinSize();
	private static native int getGrabRounding();
	private static native int getButtonTextAlign();
	private static native int getCOUNT();

	static {
		Alpha = new JImStyleVar<>(getAlpha());
		WindowPadding = new JImStyleVar<>(getWindowPadding());
		WindowRounding = new JImStyleVar<>(getWindowRounding());
		WindowBorderSize = new JImStyleVar<>(getWindowBorderSize());
		WindowMinSize = new JImStyleVar<>(getWindowMinSize());
		WindowTitleAlign = new JImStyleVar<>(getWindowTitleAlign());
		ChildRounding = new JImStyleVar<>(getChildRounding());
		ChildBorderSize = new JImStyleVar<>(getChildBorderSize());
		PopupRounding = new JImStyleVar<>(getPopupRounding());
		PopupBorderSize = new JImStyleVar<>(getPopupBorderSize());
		FramePadding = new JImStyleVar<>(getFramePadding());
		FrameRounding = new JImStyleVar<>(getFrameRounding());
		FrameBorderSize = new JImStyleVar<>(getFrameBorderSize());
		ItemSpacing = new JImStyleVar<>(getItemSpacing());
		ItemInnerSpacing = new JImStyleVar<>(getItemInnerSpacing());
		IndentSpacing = new JImStyleVar<>(getIndentSpacing());
		ScrollbarSize = new JImStyleVar<>(getScrollbarSize());
		ScrollbarRounding = new JImStyleVar<>(getScrollbarRounding());
		GrabMinSize = new JImStyleVar<>(getGrabMinSize());
		GrabRounding = new JImStyleVar<>(getGrabRounding());
		ButtonTextAlign = new JImStyleVar<>(getButtonTextAlign());
		COUNT = getCOUNT();
	}
	//endregion
}
