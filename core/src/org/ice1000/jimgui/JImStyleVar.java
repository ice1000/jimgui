package org.ice1000.jimgui;

/**
 * @param <T> Corresponding style var type,
 *            {@link Float} or ImVec2(Use {@link Void} to represent)
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings({"unused"})
public final class JImStyleVar<T> {
	/** package-private by design */
	int nativeValue;

	/** package-private by design */
	JImStyleVar(int nativeValue) {
		this.nativeValue = nativeValue;
	}

	//region Trivial copy-pasted codes
	protected static native int getAlpha();
	protected static native int getWindowPadding();
	protected static native int getWindowRounding();
	protected static native int getWindowBorderSize();
	protected static native int getWindowMinSize();
	protected static native int getWindowTitleAlign();
	protected static native int getChildRounding();
	protected static native int getChildBorderSize();
	protected static native int getPopupRounding();
	protected static native int getPopupBorderSize();
	protected static native int getFramePadding();
	protected static native int getFrameRounding();
	protected static native int getFrameBorderSize();
	protected static native int getItemSpacing();
	protected static native int getItemInnerSpacing();
	protected static native int getIndentSpacing();
	protected static native int getScrollbarSize();
	protected static native int getScrollbarRounding();
	protected static native int getGrabMinSize();
	protected static native int getGrabRounding();
	protected static native int getButtonTextAlign();
	//endregion
}
