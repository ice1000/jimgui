package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;

/**
 * Off-stack Vector providing mutability.
 *
 * @author ice1000
 * @see org.ice1000.jimgui.JImVec4
 * @since v0.1
 */
public final class MutableJImVec4 extends JImVec4 {
	public MutableJImVec4() {
		super();
	}

	/** package-private by design */
	@Contract(pure = true)
	MutableJImVec4(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}
	public MutableJImVec4(float x, float y, float z, float w) {
		super(x, y, z, w);
	}

	@Contract
	public final void setZ(final float newValue) {
		setZ(nativeObjectPtr, newValue);
	}

	@Contract
	public final void setY(final float newValue) {
		setY(nativeObjectPtr, newValue);
	}

	@Contract
	public final void setX(final float newValue) {
		setX(nativeObjectPtr, newValue);
	}

	@Contract
	public final void setW(final float newValue) {
		setW(nativeObjectPtr, newValue);
	}

	@Contract
	public final void incZ(final float increment) {
		incZ(nativeObjectPtr, increment);
	}

	@Contract
	public final void incY(final float increment) {
		incY(nativeObjectPtr, increment);
	}

	@Contract
	public final void incX(final float increment) {
		incX(nativeObjectPtr, increment);
	}

	@Contract
	public final void incW(final float increment) {
		incW(nativeObjectPtr, increment);
	}

	private static native void setZ(final long nativeObjectPtr, final float newValue);
	private static native void setY(final long nativeObjectPtr, final float newValue);
	private static native void setX(final long nativeObjectPtr, final float newValue);
	private static native void setW(final long nativeObjectPtr, final float newValue);
	private static native void incZ(final long nativeObjectPtr, final float increment);
	private static native void incY(final long nativeObjectPtr, final float increment);
	private static native void incX(final long nativeObjectPtr, final float increment);
	private static native void incW(final long nativeObjectPtr, final float increment);
}
