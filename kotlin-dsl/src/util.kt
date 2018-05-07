@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

/**
 * Copied [org.ice1000.jimgui.util.JImGuiUtil.runPer(long, java.util.function.Consumer<org.ice1000.jimgui.JImGui>)]
 * to be inlined.
 *
 * @param millis Long millis
 * @param block [@kotlin.ExtensionFunctionType] Function1<JImGui, Unit>
 * @see org.ice1000.jimgui.util.JImGuiUtil.runPer(long, java.util.function.Consumer<org.ice1000.jimgui.JImGui>)
 */
inline fun runPer(millis: Long, block: JImGuiContext.() -> Unit) {
	JImGuiContext().use { imGui ->
		var latestRefresh = System.currentTimeMillis()
		while (!imGui.windowShouldClose()) {
			val currentTimeMillis = System.currentTimeMillis()
			if (currentTimeMillis - latestRefresh > millis) {
				imGui.initNewFrame()
				block(imGui)
				imGui.render()
				latestRefresh = currentTimeMillis
			}
		}
	}
}

/**
 * Copied [org.ice1000.jimgui.util.JImGuiUtil.runPer(java.util.function.LongSupplier, java.util.function.Consumer<org.ice1000.jimgui.JImGui>)]
 * to be inlined.
 *
 * @param millisSupplier Function0<Long> provides millis
 * @param block [@kotlin.ExtensionFunctionType] Function1<JImGui, Unit>
 * @see org.ice1000.jimgui.util.JImGuiUtil.runPer(java.util.function.LongSupplier, java.util.function.Consumer<org.ice1000.jimgui.JImGui>)
 */
inline fun runPer(millisSupplier: () -> Long, block: JImGuiContext.() -> Unit) {
	JImGuiContext().use { imGui ->
		var latestRefresh = System.currentTimeMillis()
		var millis = millisSupplier()
		while (!imGui.windowShouldClose()) {
			val currentTimeMillis = System.currentTimeMillis()
			if (currentTimeMillis - latestRefresh > millis) {
				imGui.initNewFrame()
				block(imGui)
				imGui.render()
				latestRefresh = currentTimeMillis
				millis = millisSupplier()
			}
		}
	}
}

/**
 * Copied [org.ice1000.jimgui.util.JImGuiUtil.runWithin]
 * to be inlined.
 *
 * @param millis Long
 * @param block [@kotlin.ExtensionFunctionType] Function1<JImGui, Unit>
 * @see org.ice1000.jimgui.util.JImGuiUtil.runWithin
 */
inline fun runWithin(millis: Long, block: JImGuiContext.() -> Unit) {
	JImGuiContext().use { imGui ->
		var i = 0
		while (!imGui.windowShouldClose() && i++ < millis) {
			imGui.initNewFrame()
			block(imGui)
			imGui.render()
		}
	}
}

/**
 * Copied [org.ice1000.jimgui.util.JImGuiUtil.run]
 * to be inlined.
 *
 * @param block [@kotlin.ExtensionFunctionType] Function1<JImGui, Unit>
 * @see org.ice1000.jimgui.util.JImGuiUtil.run
 */
fun run(block: JImGuiContext.() -> Unit) {
	JImGuiContext().use { imGui ->
		while (!imGui.windowShouldClose()) {
			imGui.initNewFrame()
			block(imGui)
			imGui.render()
		}
	}
}
