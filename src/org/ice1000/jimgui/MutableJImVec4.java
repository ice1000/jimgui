package org.ice1000.jimgui;

/**
 * Off-stack Vector providing mutability.
 *
 * @author ice1000
 * @see org.ice1000.jimgui.JImVec4
 * @since v0.1
 */
public class MutableJImVec4 extends JImVec4 {
	public MutableJImVec4() {
		super();
	}

	public MutableJImVec4(float x, float y, float z, float w) {
		super(x, y, z, w);
	}

	public void setZ(final float newValue) {
		setZ(nativeObjectPtr, newValue);
	}

	public void setY(final float newValue) {
		setY(nativeObjectPtr, newValue);
	}

	public void setX(final float newValue) {
		setX(nativeObjectPtr, newValue);
	}

	public void setW(final float newValue) {
		setW(nativeObjectPtr, newValue);
	}

	private static native float setZ(final long nativeObjectPtr, final float newValue);

	private static native float setY(final long nativeObjectPtr, final float newValue);

	private static native float setX(final long nativeObjectPtr, final float newValue);

	private static native float setW(final long nativeObjectPtr, final float newValue);
}
