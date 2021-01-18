package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;

/**
 * @author ice1000
 * @since v0.16
 */
public class NativeTime implements DeallocatableObject {
  long nativeObjectPtr;

  public NativeTime() {
    nativeObjectPtr = allocateNativeObject();
  }

  @Override @Contract public void deallocateNativeObject() {
    deallocateNativeObject(nativeObjectPtr);
    nativeObjectPtr = 0;
  }

  public void resetToToday() {
    resetToToday(nativeObjectPtr);
  }

  public void reset() {
    reset(nativeObjectPtr);
  }

  public long toAbsoluteSeconds() {
    return toAbsoluteSeconds(nativeObjectPtr);
  }

  private static native long allocateNativeObject();
  private static native long toAbsoluteSeconds(long nativeObjectPtr);
  private static native void resetToToday(long nativeObjectPtr);
  private static native void reset(long nativeObjectPtr);
  private static native void deallocateNativeObject(long nativeObjectPtr);
}
