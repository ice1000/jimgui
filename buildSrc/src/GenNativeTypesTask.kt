package org.ice1000.gradle

import org.gradle.api.tasks.TaskAction

open class GenNativeTypesTask : GenJavaTask(""), Runnable {
  @TaskAction
  override fun run() {
    val cppPackage = targetJavaFile.parentFile
    cppPackage.mkdirs()
    listOf(
        "Int" to "int",
        "Float" to "float",
        "Double" to "double",
        "Short" to "short",
        // "Byte" to "byte",
        // "Char" to "char",
        "Long" to "long"
    ).forEach { (it, java) ->
      cppPackage
          .resolve("Native$it.java")
          .apply { if (!exists()) createNewFile() }
          //language=JAVA
          .writeText("""
package org.ice1000.jimgui;
import org.ice1000.jimgui.cpp.*;
import org.jetbrains.annotations.*;

/**
 * @author ice1000
 * @since v0.1
 */
@SuppressWarnings("ALL")
public final class Native$it extends Number implements DeallocatableObject, Cloneable {
  /** package-private by design */
  long nativeObjectPtr;
  @Contract public Native$it() { nativeObjectPtr = allocateNativeObject(); }
  @Contract(pure = true) public Native$it(long nativeObjectPtr) { this.nativeObjectPtr = nativeObjectPtr; }

  @Override @Contract
  public void deallocateNativeObject() { deallocateNativeObject0(nativeObjectPtr); nativeObjectPtr = 0; }
  @Contract(pure = true) public $java accessValue() { return accessValue(nativeObjectPtr); }
  @Contract public void increaseValue($java increment) { increaseValue(nativeObjectPtr, increment); }
  @Contract public void modifyValue($java newValue) { modifyValue(nativeObjectPtr, newValue); }

  @Override @Contract(pure = true) public int intValue() { return (int) accessValue(); }
  @Override @Contract(pure = true) public long longValue() { return (long) accessValue(); }
  @Override @Contract(pure = true) public float floatValue() { return (float) accessValue(); }
  @Override @Contract(pure = true) public double doubleValue() { return (double) accessValue(); }

  private static native $java accessValue(long nativeObjectPtr);
  private static native void modifyValue(long nativeObjectPtr, $java newValue);
  private static native void increaseValue(long nativeObjectPtr, $java increment);
  private static native long allocateNativeObject();
  private static native void deallocateNativeObject0(long nativeObjectPtr);

  @Override @Contract(value = "null -> false", pure = true)
  public boolean equals(@Nullable Object o) {
    return this == o || o instanceof Native$it && nativeObjectPtr == ((Native$it) o).nativeObjectPtr;
  }
  @Override @Contract(pure = true)
  public int hashCode() { return Long.hashCode(nativeObjectPtr); }
  @Override public @NotNull String toString() { return String.valueOf(accessValue()); }
  @Override @Contract public @NotNull Native$it clone() {
    Native$it newInstance = new Native$it();
    newInstance.modifyValue(accessValue());
    return newInstance;
  }
}
""")
    }
  }
}
