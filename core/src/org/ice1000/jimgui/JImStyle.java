package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public final class JImStyle extends JImGuiStyleGen implements DeallocatableObject {
	/**
	 * package-private by design
	 *
	 * @param nativeObjectPtr native pointer {@code ImStyle*}
	 */
	JImStyle(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}

	/**
	 * @apiNote Should call {@link JImStyle#deallocateNativeObject()}.
	 * @see JImGuiStyleGen#getInstance(JImGui)
	 * @see JImGui#getStyle()
	 * @since v0.4
	 */
	public JImStyle() {
		this(allocateNativeObject());
	}

	public @NotNull JImVec4 getColor(@MagicConstant(valuesFromClass = JImStyleColors.class) int index) {
		return new JImVec4(getColor0(nativeObjectPtr, index));
	}

	static native long getColor0(
			long nativeObjectPtr,
			@MagicConstant(valuesFromClass = JImStyleColors.class) int index);

	private static native long allocateNativeObject();
	private static native void deallocateNativeObject(long nativeObjectPtr);

	@Override
	public void deallocateNativeObject() {
		deallocateNativeObject(nativeObjectPtr);
	}
}
