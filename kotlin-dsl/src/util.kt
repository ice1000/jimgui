@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

/**
 * Copied [org.ice1000.jimgui.util.JImGuiUtil.runPer]
 * to be inlined.
 *
 * @param millis Long millis
 * @param block [@kotlin.ExtensionFunctionType] Function1<JImGui, Unit>
 * @see org.ice1000.jimgui.util.JImGuiUtil.runPer(long, java.util.function.Consumer<org.ice1000.jimgui.JImGui>)
 */
inline fun runPer(millis: Long, block: JImGuiBlock) {
	JImGuiContext().use { imGui ->
		var latestRefresh = System.currentTimeMillis()
		while (!imGui.windowShouldClose()) {
			val currentTimeMillis = System.currentTimeMillis()
			val deltaTime = currentTimeMillis - latestRefresh
			Thread.sleep(deltaTime / 2)
			if (deltaTime > millis) {
				imGui.initNewFrame()
				block(imGui)
				imGui.render()
				latestRefresh = currentTimeMillis
			}
		}
	}
}

/**
 * Copied [org.ice1000.jimgui.util.JImGuiUtil.runPer]
 * to be inlined.
 *
 * @param millisSupplier Function0<Long> provides millis
 * @param block [@kotlin.ExtensionFunctionType] Function1<JImGui, Unit>
 * @see org.ice1000.jimgui.util.JImGuiUtil.runPer(java.util.function.LongSupplier, java.util.function.Consumer<org.ice1000.jimgui.JImGui>)
 */
inline fun runPer(millisSupplier: () -> Long, block: JImGuiBlock) {
	JImGuiContext().use { imGui ->
		var latestRefresh = System.currentTimeMillis()
		var millis = millisSupplier()
		while (!imGui.windowShouldClose()) {
			val currentTimeMillis = System.currentTimeMillis()
			val deltaTime = currentTimeMillis - latestRefresh
			Thread.sleep(deltaTime / 2)
			if (deltaTime > millis) {
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
inline fun runWithin(millis: Long, block: JImGuiBlock) {
	JImGuiContext().use { imGui ->
		val end = System.currentTimeMillis() + millis
		while (!imGui.windowShouldClose() && System.currentTimeMillis() < end) {
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
inline fun run(block: JImGuiBlock) {
	JImGuiContext().use { imGui ->
		while (!imGui.windowShouldClose()) {
			imGui.initNewFrame()
			block(imGui)
			imGui.render()
		}
	}
}
