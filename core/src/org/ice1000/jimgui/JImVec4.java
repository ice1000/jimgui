package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Off-stack Vector, read only
 *
 * @author ice1000
 * @see MutableJImVec4 which is mutable
 * @since v0.1
 */
public class JImVec4 implements DeallocatableObject {
	// not sure if there should be one
	// public static final @NotNull JImVec4 NULL = new JImVec4(0);

	/** package-private by design */
	long nativeObjectPtr;

	@Contract
	public JImVec4() {
		this(0, 0, 0, 0);
	}

	/** package-private by design */
	@Contract(pure = true)
	JImVec4(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}

	@Contract
	public JImVec4(float x, float y, float z, float w) {
		nativeObjectPtr = allocateNativeObjects(x, y, z, w);
	}

	/** Don't call this unless necessary. */
	@Contract(pure = true)
	public final float getW() {
		return getW(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	@Contract(pure = true)
	public final float getX() {
		return getX(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	@Contract(pure = true)
	public final float getY() {
		return getY(nativeObjectPtr);
	}

	/** Don't call this unless necessary. */
	@Contract(pure = true)
	public final float getZ() {
		return getZ(nativeObjectPtr);
	}

	/** convert to {@code ImU32}, unsigned 32-bit integer */
	@Contract(pure = true)
	public final int toU32() {
		return toU32(nativeObjectPtr);
	}

	/** Should only be called once. */
	@Override
	public final void deallocateNativeObject() {
		deallocateNativeObjects(nativeObjectPtr);
	}

	public @NotNull java.awt.Color toAWT() {
		return new java.awt.Color(getW(), getX(), getY(), getZ());
	}

	/**
	 * @apiNote Don't call if JavaFX is not installed
	 */
	public @NotNull javafx.scene.paint.Color toJFX() {
		return new javafx.scene.paint.Color(getW(), getX(), getY(), getZ());
	}

	/**
	 * @param color AWT color
	 * @return a mutable imgui vec4 instance
	 */
	@Contract
	public static @NotNull MutableJImVec4 fromAWT(@NotNull java.awt.Color color) {
		return new MutableJImVec4(color.getRed() / 256f,
				color.getGreen() / 256f,
				color.getBlue() / 256f,
				color.getAlpha() / 256f);
	}

	/**
	 * @apiNote Don't call if JavaFX is not installed
	 * @param color JavaFX color
	 * @return a mutable imgui vec4 instance
	 */
	@Contract
	public static @NotNull MutableJImVec4 fromJFX(@NotNull javafx.scene.paint.Color color) {
		return new MutableJImVec4((float) color.getRed(),
				(float) color.getGreen(),
				(float) color.getBlue(),
				(float) color.getOpacity());
	}

	/**
	 * Convert HSV color to RGB
	 *
	 * @return a mutable imgui vec4 instance
	 */
	@Contract
	public static @NotNull MutableJImVec4 fromHSV(float h, float s, float v) {
		return fromHSV(h, s, v, 1);
	}

	/**
	 * Convert HSV color to RGB
	 *
	 * @return a mutable imgui vec4 instance
	 */
	@Contract
	public static @NotNull MutableJImVec4 fromHSV(float h, float s, float v, float a) {
		return new MutableJImVec4(fromHSV0(h, s, v, a));
	}

	/**
	 * @param u32 unsigned 32-bit int color representation
	 * @return a mutable imgui vec4 instance
	 * @see JImVec4#toU32()
	 */
	@Contract
	public static @NotNull MutableJImVec4 fromU32(int u32) {
		return new MutableJImVec4(fromImU32(u32));
	}

	@Override
	public String toString() {
		return "ImVec4{" +
				getX() + ',' +
				getY() + ',' +
				getZ() + ',' +
				getW() +
				'}';
	}

	private static native float getZ(final long nativeObjectPtr);
	private static native float getY(final long nativeObjectPtr);
	private static native float getX(final long nativeObjectPtr);
	private static native float getW(final long nativeObjectPtr);
	private static native int toU32(final long nativeObjectPtr);
	private static native long fromImU32(int u32);
	private static native long fromHSV0(float h, float s, float v, float a);
	private static native long allocateNativeObjects(float x, float y, float z, float w);
	private static native void deallocateNativeObjects(long nativeObjectPtr);
}
