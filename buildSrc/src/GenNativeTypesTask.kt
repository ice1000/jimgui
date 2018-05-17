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
				// "Double" to "double",
				// "Short" to "short",
				// "Byte" to "byte",
				// "Char" to "char",
				// "Long" to "long",
				"Bool" to "boolean"
		).forEach { (it, java) ->
			cppPackage
					.resolve("Native$it.java")
					.apply { if (!exists()) createNewFile() }
					.writeText("""
package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.*;
import org.jetbrains.annotations.Contract;

/**
 * @author ice1000
 * @since v0.1
 */
public class Native$it implements DeallocatableObject {
	/** package-private by design */
	long nativeObjectPtr;

	@Contract
	public Native$it() {
		nativeObjectPtr = allocateNativeObject();
	}

	@Override
	@Contract
	public void deallocateNativeObject() {
		deallocateNativeObject0(nativeObjectPtr);
	}

	@Contract(pure = true)
	public $java accessValue() {
		return accessValue(nativeObjectPtr);
	}

	@Contract
	public void modifyValue($java newValue) {
		modifyValue(nativeObjectPtr, newValue);
	}

	private static native $java accessValue(long nativeObjectPtr);
	private static native void modifyValue(long nativeObjectPtr, $java newValue);
	private static native long allocateNativeObject();
	private static native void deallocateNativeObject0(long nativeObjectPtr);
}
""")
		}
	}
}
