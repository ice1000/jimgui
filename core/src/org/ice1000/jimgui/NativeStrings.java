package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.17
 */
public class NativeStrings implements DeallocatableObject {
  long nativeObjectPtr;

  NativeStrings(long nativeObjectPtr) {
    this.nativeObjectPtr = nativeObjectPtr;
  }

  public int size() {
    return size(nativeObjectPtr);
  }

  public @NotNull NativeString get(int index) {
    return new NativeString(get(nativeObjectPtr, index));
  }

  private static native int size(long nativeObjectPtr);
  private static native long get(long nativeObjectPtr, int index);
  private static native void deallocateNativeObject(long nativeObjectPtr);

  @Override public void deallocateNativeObject() {
    deallocateNativeObject(nativeObjectPtr);
  }
}
