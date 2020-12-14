package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper of C++ <code>std::string</code>.
 *
 * @author ice1000
 * @since v0.13
 */
public class NativeString implements CharSequence, DeallocatableObject {
  /** package-private by design */
  long nativeObjectPtr;

  @Contract public NativeString() {
    this(allocateNativeObject());
  }

  @Contract(pure = true) private NativeString(long nativeObjectPtr) {
    this.nativeObjectPtr = nativeObjectPtr;
  }

  @Override @Contract public void deallocateNativeObject() {
    deallocateNativeObject0(nativeObjectPtr);
    nativeObjectPtr = 0;
  }

  @Override @Contract(value = "null -> false", pure = true) public boolean equals(@Nullable Object o) {
    return this == o || o instanceof NativeBool && nativeObjectPtr == ((NativeBool) o).nativeObjectPtr;
  }

  @Override @Contract(pure = true) public int hashCode() {
    return Long.hashCode(nativeObjectPtr);
  }

  @Override @Contract(pure = true) public int length() {
    return length(nativeObjectPtr);
  }

  @Override @Contract(pure = true) public char charAt(int position) {
    return (char) byteAt(nativeObjectPtr, position);
  }

  @Override public @NotNull NativeString subSequence(int start, int end) {
    return new NativeString(substring(nativeObjectPtr, start, end));
  }

  @Contract(pure = true) public byte byteAt(int position) {
    return byteAt(nativeObjectPtr, position);
  }

  public byte @NotNull [] toBytes() {
    byte[] values = new byte[length()];
    readValues(nativeObjectPtr, values);
    return values;
  }

  @Override public @NotNull String toString() {
    return new String(toBytes());
  }

  private static native long allocateNativeObject();

  private static native void deallocateNativeObject0(long nativeObjectPtr);

  private static native byte byteAt(long nativeObjectPtr, int position);

  private static native byte length(long nativeObjectPtr);

  private static native long substring(long nativeObjectPtr, int start, int end);

  private static native long readValues(long nativeObjectPtr, byte @NotNull [] buf);
}
