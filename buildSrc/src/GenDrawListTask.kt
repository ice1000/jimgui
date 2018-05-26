@file:Suppress("unused")

package org.ice1000.gradle

import org.intellij.lang.annotations.Language

/**
 * @author ice1000
 */
open class GenDrawListTask : GenTask("JImGuiDrawListGen", "imgui_draw_list") {
	init {
		description = "Generate binding for ImGui::GetDrawList"
	}

	@Language("JAVA", prefix = "class A{", suffix = "}")
	override val userCode = """/** subclass-private by design */
	protected long nativeObjectPtr;

	/** package-private by design */
	JImGuiDrawListGen(long nativeObjectPtr) {
		this.nativeObjectPtr = nativeObjectPtr;
	}
"""

	override fun java(javaCode: StringBuilder) {
		primitiveMembers.forEach { (type, name, annotation) ->
			javaCode.genJavaObjectiveMemberAccessor(name, annotation, type)
		}
		functions.forEach { genJavaFun(javaCode, it) }
	}

	override fun `c++`(cppCode: StringBuilder) {
		primitiveMembers.joinLinesTo(cppCode) { (type, name) -> `c++PrimitiveAccessor`(type, name, ", jlong nativeObjectPtr") }
		functions.forEach { (name, type, params) -> `genC++Fun`(params.dropLast(1), name, type, cppCode, ", jlong nativeObjectPtr") }
	}

	override val `c++Expr` = "PTR_J2C(ImDrawList, nativeObjectPtr)->"
	private val functions = listOf(
			Fun.private("pushClipRect",
					size("clipRectMin"),
					size("clipRectMax"),
					bool("intersectWithCurrentClipRect", default = false),
					nativeObjectPtr),
			Fun.private("pushClipRectFullScreen", nativeObjectPtr),
			Fun.private("popClipRect", nativeObjectPtr),
			Fun.private("pushTextureID", texture("textureID"), nativeObjectPtr),
			Fun.private("popTextureID", nativeObjectPtr),

			// Primitives
			Fun.private("addLine", pos("a"), pos("b"), u32, thickness, nativeObjectPtr),
			Fun.private("addRect", pos("a"), pos("b"), u32, rounding, roundingFlags, thickness, nativeObjectPtr),
			Fun.private("addRectFilled", pos("a"), pos("b"), u32, rounding, roundingFlags, nativeObjectPtr),
			Fun.private("addRectFilledMultiColor", pos("a"), pos("b"),
					int("colorUpperLeft"), int("colorUpperRight"),
					int("colorBottomRight"), int("colorBottomLeft"),
					nativeObjectPtr),
			Fun.private("addQuad", pos("a"), pos("b"), pos("c"), pos("d"), u32, thickness, nativeObjectPtr),
			Fun.private("addQuadFilled", pos("a"), pos("b"), pos("c"), pos("d"), u32, nativeObjectPtr),
			Fun.private("addTriangle", pos("a"), pos("b"), pos("c"), u32, thickness, nativeObjectPtr),
			Fun.private("addTriangleFilled", pos("a"), pos("b"), pos("c"), u32, nativeObjectPtr),
			Fun.private("addCircle", pos("centre"), float("radius"), u32, numSegments, thickness, nativeObjectPtr),
			Fun.private("addCircleFilled", pos("centre"), float("radius"), u32, numSegments, nativeObjectPtr),
			Fun.protected("addText",
					font(), float("fontSize"), pos, u32,
					stringSized("text"),
					float("wrapWidth", default = 0),
					vec4Ptr("cpuFineClipRect", default = 0),
					nativeObjectPtr),
			Fun.private("addBezierCurve",
					pos("pos0"), pos("cp0"), pos("cp1"),
					pos("pos1"), u32, thickness, numSegments(0),
					nativeObjectPtr),

			// Stateful path API, add points then finish with PathFillConvex() or PathStroke()
			Fun.private("pathClear", nativeObjectPtr),
			Fun.private("pathLineTo", pos, nativeObjectPtr),
			Fun.private("pathLineToMergeDuplicate", pos, nativeObjectPtr),
			Fun.private("pathFillConvex", u32, nativeObjectPtr),
			Fun.private("pathStroke", u32, bool("closed"), thickness, nativeObjectPtr),
			Fun.private("pathArcTo",
					pos("centre"), float("radius"),
					float("aMin"), float("aMax"),
					numSegments(10), nativeObjectPtr),
			Fun.private("pathArcToFast",
					pos("centre"), float("radius"),
					float("aMinOf12"), float("aMaxOf12"), nativeObjectPtr),
			Fun.private("pathBezierCurveTo",
					pos("p1"), pos("p2"), pos("p3"),
					numSegments(0),
					nativeObjectPtr),
			Fun.private("pathRect",
					pos("rectMin"), pos("rectMax"), rounding,
					roundingFlags, nativeObjectPtr),

			// Channels
			Fun.private("channelsSplit", int("channelsCount"), nativeObjectPtr),
			Fun.private("channelsMerge", nativeObjectPtr),
			Fun.private("channelsSetCurrent", int("channelsIndex"), nativeObjectPtr),

			// Internal helpers
			Fun.private("clear", nativeObjectPtr),
			Fun.private("clearFreeMemory", nativeObjectPtr),
			Fun.private("primReserve", int("idxCount"), int("vtxCount"), nativeObjectPtr),
			Fun.private("primRect", pos("a"), pos("b"),
					u32, nativeObjectPtr),
			Fun.private("primRectUV",
					pos("a"), pos("b"),
					pos("uvA"), pos("uvB"),
					u32, nativeObjectPtr),
			Fun.private("primQuadUV",
					pos("a"), pos("b"), pos("c"), pos("d"),
					pos("uvA"), pos("uvB"), pos("uvC"), pos("uvD"),
					u32, nativeObjectPtr),
			Fun.private("primWriteVtx", pos, pos("uv"), u32, nativeObjectPtr),
			Fun.private("primWriteIdx", int("idx"), nativeObjectPtr),
			Fun.private("primVtx", pos, pos("uv"), u32, nativeObjectPtr),
			Fun.private("updateClipRect", nativeObjectPtr),
			Fun.private("updateTextureID", nativeObjectPtr)
	)

	private val primitiveMembers = listOf(PPT("int", "Flags",
			annotation = "@MagicConstant(flagsFromClass = JImDrawListFlags.class)"))
}
