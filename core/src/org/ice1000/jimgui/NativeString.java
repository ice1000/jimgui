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
public final class NativeString implements CharSequence, DeallocatableObject {
  /** package-private by design */
  long nativeObjectPtr;

  @Contract public NativeString() {
    this(16);
  }

  @Contract public NativeString(int initialCapacity) {
    this(allocateNativeObject(initialCapacity));
  }

  public boolean isNull() {
    return nativeObjectPtr == 0;
  }

  @Contract(pure = true) protected NativeString(long nativeObjectPtr) {
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

  @Contract public void clear() {
    clear(nativeObjectPtr);
  }

  @Contract public void append(byte chr) {
    appendChar(nativeObjectPtr, chr);
  }

  @Contract public void append(char chr) {
    append((byte) chr);
  }

  @Override @Contract(pure = true) public char charAt(int position) {
    return (char) byteAt(nativeObjectPtr, position);
  }

  @Contract public void setByteAt(int position, byte newValue) {
    setByteAt(nativeObjectPtr, position, newValue);
  }

  @Contract public void setCharAt(int position, char newValue) {
    setByteAt(position, (byte) newValue);
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

  @Contract("_ -> new") public static @NotNull NativeString fromRaw(long constCharPtr) {
    return new NativeString(allocateNativeObjectFromRaw(constCharPtr));
  }

  private static native long allocateNativeObject(int initialCapacity);
  private static native long allocateNativeObjectFromRaw(long constCharPtr);
  private static native void deallocateNativeObject0(long nativeObjectPtr);
  private static native byte byteAt(long nativeObjectPtr, int position);
  private static native void setByteAt(long nativeObjectPtr, int position, byte newValue);
  private static native int length(long nativeObjectPtr);
  private static native void clear(long nativeObjectPtr);
  private static native long substring(long nativeObjectPtr, int start, int end);
  private static native void appendChar(long nativeObjectPtr, byte chr);
  private static native void readValues(long nativeObjectPtr, byte @NotNull [] buf);
}
