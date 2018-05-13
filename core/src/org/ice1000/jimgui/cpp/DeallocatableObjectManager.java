package org.ice1000.jimgui.cpp;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.ArrayList;

public class DeallocatableObjectManager extends ArrayList<@NotNull DeallocatableObject> implements AutoCloseable,
		Closeable {
	@Override
	public void close() {
		deallocateAll();
	}

	public void deallocateAll() {
		for (DeallocatableObject object : this) object.deallocateNativeObject();
	}
}
