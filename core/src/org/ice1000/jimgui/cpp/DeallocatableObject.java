package org.ice1000.jimgui.cpp;

/**
 * Represents a native (C++) object
 *
 * @author ice1000
 * @since v0.1
 */
public interface DeallocatableObject extends AutoCloseable {
	void deallocateNativeObject();

	@Override
	default void close() {
		deallocateNativeObject();
	}
}
