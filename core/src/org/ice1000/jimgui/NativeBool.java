package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ice1000
 * @since v0.1
 */
public final class NativeBool implements DeallocatableObject, Cloneable {
  /** package-private by design */
  long nativeObjectPtr;

  @Contract public NativeBool() {
    nativeObjectPtr = allocateNativeObject();
  }

  @Override @Contract public void deallocateNativeObject() {
    deallocateNativeObject0(nativeObjectPtr);
    nativeObjectPtr = 0;
  }

  @Contract(pure = true) public boolean accessValue() {
    return accessValue(nativeObjectPtr);
  }

  @Contract public void invertValue() {
    invertValue(nativeObjectPtr);
  }

  @Contract public void modifyValue(boolean newValue) {
    modifyValue(nativeObjectPtr, newValue);
  }

  private static native boolean accessValue(long nativeObjectPtr);

  private static native void modifyValue(long nativeObjectPtr, boolean newValue);

  private static native void invertValue(long nativeObjectPtr);

  private static native long allocateNativeObject();

  private static native void deallocateNativeObject0(long nativeObjectPtr);

  @Override @Contract(value = "null -> false", pure = true) public boolean equals(@Nullable Object o) {
    return this == o || o instanceof NativeBool && nativeObjectPtr == ((NativeBool) o).nativeObjectPtr;
  }

  @Override @Contract(pure = true) public int hashCode() {
    return Long.hashCode(nativeObjectPtr);
  }

  @Override @Contract(pure = true) public @NotNull String toString() {
    return String.valueOf(accessValue());
  }

  @Override @Contract @SuppressWarnings("MethodDoesntCallSuperMethod") public @NotNull NativeBool clone() {
    NativeBool nativeBool = new NativeBool();
    nativeBool.modifyValue(accessValue());
    return nativeBool;
  }
}
