package org.ice1000.jimgui;

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
	MutableJImVec4(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}
	public MutableJImVec4(float x, float y, float z, float w) {
		super(x, y, z, w);
	}

	public final void setZ(final float newValue) {
		setZ(nativeObjectPtr, newValue);
	}

	public final void setY(final float newValue) {
		setY(nativeObjectPtr, newValue);
	}

	public final void setX(final float newValue) {
		setX(nativeObjectPtr, newValue);
	}

	public final void setW(final float newValue) {
		setW(nativeObjectPtr, newValue);
	}

	private static native void setZ(final long nativeObjectPtr, final float newValue);

	private static native void setY(final long nativeObjectPtr, final float newValue);

	private static native void setX(final long nativeObjectPtr, final float newValue);

	private static native void setW(final long nativeObjectPtr, final float newValue);
}
