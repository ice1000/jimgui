@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenFontAtlasTask : GenTask("JImGuiFontAtlasGen", "imgui_font_atlas") {
	init {
		description = "Generate binding for ImGui::GetFont().ContainerAtlas"
	}

	@Language("JAVA", prefix = "class A{", suffix = "}")
	override val userCode = """/** package-private by design */
	protected long nativeObjectPtr;

	/** package-private by design */
	JImGuiFontAtlasGen(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}
"""

	override fun java(javaCode: StringBuilder) {
		imVec2Members.forEach { genJavaObjectiveXYAccessor(javaCode, it, "float") }
		primitiveMembers.forEach { (type, name, annotation) ->
			javaCode.javadoc(name)
					.append("\tpublic ").append(annotation).append(type).append(" get").append(name).append("(){return get").append(name).appendln("(nativeObjectPtr);}")
					.append("\tprivate native ").append(annotation).append(type).append(" get").append(name).appendln("(long nativeObjectPtr);")
					.javadoc(name)
					.append("\tpublic void set").append(name).append('(').append(annotation).append(type).append(" newValue) {set").append(name).appendln("(nativeObjectPtr, newValue);}")
					.append("\tprivate native void set").append(name).append("(long nativeObjectPtr, ").append(annotation).append(type).appendln(" newValue);")
		}
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float", ", jlong nativeObjectPtr") }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, ", jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params.drop(1), name, type, cppCode, ", jlong nativeObjectPtr") }
	}

	override val `c++Expr` = "(reinterpret_cast<ImFontAtlas *> (nativeObjectPtr))->"
	private val imVec2Members = listOf("TexUvScale", "TexUvWhitePixel")
	private val functions = listOf(
			Fun.protected("addFontFromFileTTF", "long", nativeObjectPtr, string("path"), float("sizePixels")),
			Fun.protected("addFontFromMemoryCompressedBase85TTF", "long", nativeObjectPtr, string("compressedFontDataBase85"), float("sizePixels")),
			Fun.protected("build", "boolean", nativeObjectPtr),
			Fun.protected("clearInputData", nativeObjectPtr),
			Fun.protected("clearTexData", nativeObjectPtr),
			Fun.protected("clearFonts", nativeObjectPtr),
			Fun.protected("clear", nativeObjectPtr))

	private val primitiveMembers = listOf(
			PPT("int", "Flags", annotation = "@MagicConstant(flagsFromClass = JImFontAtlasFlags.class)"),
			PPT("int", "TexWidth"),
			PPT("int", "TexHeight"),
			PPT("int", "TexDesiredWidth"),
			PPT("int", "TexGlyphPadding")
	)
}
