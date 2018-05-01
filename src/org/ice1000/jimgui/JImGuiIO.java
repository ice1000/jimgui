package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiIO {
	private long nativeObjectPtr;

	public JImGuiIO(@NotNull JImGui owner) {
		nativeObjectPtr = getNativeObjects(owner.nativeObjectPtr);
	}

	public float getFramerate() {
		return getFramerate(nativeObjectPtr);
	}

	private static native float getFramerate(long nativeObjectPtr);

	private static native long getNativeObjects(long ownerPtr);
}
