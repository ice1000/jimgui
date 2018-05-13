package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/**
 * Off-stack Vector, read only
 *
 * @author ice1000
 * @see MutableJImVec4 which is mutable
 * @since v0.1
 */
public class JImVec4 implements DeallocatableObject {
	/** package-private by design */
	long nativeObjectPtr;

	public JImVec4() {
		nativeObjectPtr = allocateNativeObjects();
	}

	public JImVec4(float x, float y, float z, float w) {
		nativeObjectPtr = allocateNativeObjects(x, y, z, w);
	}

	/** Don't call this unless necessary. */
	public final float getW() {
		return getW(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	public final float getX() {
		return getX(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	public final float getY() {
		return getY(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	public final float getZ() {
		return getZ(nativeObjectPtr);
	}

	/** @return see {@link JImVec4#nativeObjectPtr} */
	private static native long allocateNativeObjects();

	/** Should only be called once. */
	@Override
	public void deallocateNativeObject() {
		deallocateNativeObjects(nativeObjectPtr);
	}

	/**
	 * @param color AWT color
	 * @return a mutable imgui vec4 instance
	 */
	public static @NotNull MutableJImVec4 fromAWT(@NotNull java.awt.Color color) {
		return new MutableJImVec4(color.getRed() / 256f,
				color.getGreen() / 256f,
				color.getBlue() / 256f,
				color.getAlpha() / 256f);
	}

	/**
	 * @param color JavaFX color
	 * @return a mutable imgui vec4 instance
	 */
	public static @NotNull MutableJImVec4 fromJFX(@NotNull javafx.scene.paint.Color color) {
		return new MutableJImVec4((float) color.getRed(),
				(float) color.getGreen(),
				(float) color.getBlue(),
				(float) color.getOpacity());
	}

	private static native float getZ(final long nativeObjectPtr);
	private static native float getY(final long nativeObjectPtr);
	private static native float getX(final long nativeObjectPtr);
	private static native float getW(final long nativeObjectPtr);
	private static native long allocateNativeObjects(float x, float y, float z, float w);
	private static native void deallocateNativeObjects(long nativeObjectPtr);
}
