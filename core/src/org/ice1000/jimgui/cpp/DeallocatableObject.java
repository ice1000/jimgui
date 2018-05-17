package org.ice1000.jimgui.cpp;

import org.jetbrains.annotations.Contract;

/**
 * Represents a native (C++) object
 *
 * @author ice1000
 * @since v0.1
 */
public interface DeallocatableObject extends AutoCloseable {
	@Contract
	void deallocateNativeObject();

	@Override
	default void close() {
		deallocateNativeObject();
	}
}
