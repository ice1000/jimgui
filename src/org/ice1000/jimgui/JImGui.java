package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("WeakerAccess")
public class JImGui implements AutoCloseable, Closeable {
	private long nativeObjectPtr;
	private @NotNull JImVec4 background;

	public JImGui() {
		JniLoader.load();
		nativeObjectPtr = allocateNativeObjects();
		background = new JImVec4(1.0f, 0.55f, 0.60f, 1.00f);
	}

	@Override
	public void close() {
		deallocateNativeObjects(nativeObjectPtr);
	}

	public void demoMainLoop() {
		demoMainLoop(background.nativeObjectPtr);
	}

	public boolean windowShouldClose() {
		return windowShouldClose(nativeObjectPtr);
	}

	public void render() {
		render(nativeObjectPtr, background.nativeObjectPtr);
	}

	public static native void initNewFrame();

	/** @return see {@link JImGui#nativeObjectPtr} */
	private static native long allocateNativeObjects();

	/** @param nativeObjectPtr see {@link JImGui#nativeObjectPtr} */
	private static native void deallocateNativeObjects(long nativeObjectPtr);

	private static native void demoMainLoop(long colorPtr);

	private static native boolean windowShouldClose(long nativeObjectPtr);

	private static native void render(long nativeObjectPtr, long colorPtr);
}
