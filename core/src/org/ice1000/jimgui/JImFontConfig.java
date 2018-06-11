package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;

/**
 * @author ice1000
 * @since v0.4
 */
public final class JImFontConfig extends JImGuiFontConfigGen implements DeallocatableObject {
	/**
	 * {@inheritDoc}
	 *
	 * @param nativeObjectPtr native ImFont*
	 */
	@Contract(pure = true)
	JImFontConfig(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}

	@Contract
	public JImFontConfig() {
		this(allocateNativeObject());
	}

	private static native long allocateNativeObject();
	private static native void deallocateNativeObject(long nativeObjectPtr);

	@Override
	public void deallocateNativeObject() {
		deallocateNativeObject(nativeObjectPtr);
	}
}
