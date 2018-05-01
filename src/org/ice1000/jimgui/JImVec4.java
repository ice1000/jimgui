package org.ice1000.jimgui;

import java.io.Closeable;

/**
 * Off-stack Vector
 *
 * @author ice1000
 * @since v0.1
 */
public class JImVec4 implements Closeable, AutoCloseable {
	/** package-private by design */
	long nativeObjectPtr;

	public JImVec4() {
		nativeObjectPtr = allocateNativeObjects();
	}

	public JImVec4(float x, float y, float z, float w) {
		nativeObjectPtr = allocateNativeObjects(x, y, z, w);
	}

	/** Don't call this unless necessary. */
	public float getW() {
		return getW(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	public float getX() {
		return getX(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	public float getY() {
		return getY(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	public float getZ() {
		return getZ(nativeObjectPtr);
	}

	/** @return see {@link JImVec4#nativeObjectPtr} */
	private static native long allocateNativeObjects();

	private static native float getZ(long nativeObjectPtr);

	private static native float getY(long nativeObjectPtr);

	private static native float getX(long nativeObjectPtr);

	private static native float getW(long nativeObjectPtr);

	private static native long allocateNativeObjects(float a, float b, float c, float d);

	/** @param nativeObjectPtr see {@link JImVec4#nativeObjectPtr} */
	private static native void deallocateNativeObjects(long nativeObjectPtr);

	@Override
	public void close() {
		deallocateNativeObjects(nativeObjectPtr);
	}
}
