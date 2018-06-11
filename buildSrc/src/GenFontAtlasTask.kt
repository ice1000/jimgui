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
	override val userCode = """protected long nativeObjectPtr;

	/** package-private by design */
	JImGuiFontAtlasGen(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}
"""

	override fun java(javaCode: StringBuilder) {
		imVec2Members.forEach { genJavaObjectiveXYAccessor(javaCode, it, "float") }
		primitiveMembers.forEach { (type, name, annotation) ->
			javaCode.genJavaObjectiveMemberAccessor(name, annotation, type)
		}
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		imVec2Members.joinLinesTo(cppCode) { `c++XYAccessor`(it, "float", "jlong nativeObjectPtr") }
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, "jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params.dropLast(1), name, type, cppCode, "jlong nativeObjectPtr") }
	}

	override val `c++Expr` = "PTR_J2C(ImFontAtlas, nativeObjectPtr)->"
	private val imVec2Members = listOf("TexUvScale", "TexUvWhitePixel")
	private val functions = listOf(
			Fun.protected("addFontDefault", "long", nativeObjectPtr),
			Fun.protected("addFontFromFileTTF", "long", string("path"), float("sizePixels"), nativeObjectPtr),
			Fun.protected("addFontFromMemoryCompressedBase85TTF", "long", string("compressedFontDataBase85"), float("sizePixels"), nativeObjectPtr),
			Fun.private("build", "boolean", nativeObjectPtr),
			Fun.private("clearInputData", nativeObjectPtr),
			Fun.private("clearTexData", nativeObjectPtr),
			Fun.private("clearFonts", nativeObjectPtr),
			Fun.private("clear", nativeObjectPtr),
			Fun.private("setTexID", texture("id"), nativeObjectPtr),
			Fun.protected("getGlyphRangesDefault", "long", nativeObjectPtr),
			Fun.protected("getGlyphRangesKorean", "long", nativeObjectPtr),
			Fun.protected("getGlyphRangesJapanese", "long", nativeObjectPtr),
			Fun.protected("getGlyphRangesChineseFull", "long", nativeObjectPtr),
			Fun.protected("getGlyphRangesChineseSimplifiedCommon", "long", nativeObjectPtr),
			Fun.protected("getGlyphRangesCyrillic", "long", nativeObjectPtr),
			Fun.protected("getGlyphRangesThai", "long", nativeObjectPtr),
			Fun.private("addCustomRectRegular", int("id"), int("width"), int("height"), nativeObjectPtr),
			Fun.protected("addCustomRectFontGlyph",
					font(), p("id", "short"), int("width"), int("height"),
					float("advanceX"), pos("offset", default = "0,0"), nativeObjectPtr))

	private val primitiveMembers = listOf(
			PPT("int", "Flags", annotation = "@MagicConstant(flagsFromClass = JImFontAtlasFlags.class)"),
			PPT("int", "TexWidth"),
			PPT("int", "TexHeight"),
			PPT("int", "TexDesiredWidth"),
			PPT("int", "TexGlyphPadding")
	)
}
