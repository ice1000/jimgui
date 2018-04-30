package org.ice1000.jimgui;

import java.io.Closeable;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGui implements AutoCloseable, Closeable {
	@Override
	public void close() {
		deallocateNativeObjects();
	}

	private native void deallocateNativeObjects();
}
