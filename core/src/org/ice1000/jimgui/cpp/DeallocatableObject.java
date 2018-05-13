package org.ice1000.jimgui.cpp;

public interface DeallocatableObject extends AutoCloseable {
	void deallocateNativeObject();

	@Override
	default void close() {
		deallocateNativeObject();
	}
}
