package org.ice1000.jimgui.cpp;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;

public class DeallocatableObjectManager extends ArrayList<@NotNull DeallocatableObject>
		implements AutoCloseable, Closeable {
	public DeallocatableObjectManager(int initialCapacity) {
		super(initialCapacity);
	}

	public DeallocatableObjectManager() {
	}

	public DeallocatableObjectManager(@NotNull Collection<@NotNull ? extends @NotNull DeallocatableObject> c) {
		super(c);
	}

	@Override
	public void close() {
		deallocateAll();
	}

	public void deallocateAll() {
		for (DeallocatableObject object : this) object.deallocateNativeObject();
	}
}
