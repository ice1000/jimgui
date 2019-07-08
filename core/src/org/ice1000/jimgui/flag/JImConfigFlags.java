package org.ice1000.jimgui.flag;

import org.ice1000.jimgui.JImGuiIOGen;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImConfigFlags {
	int Nothing = 0;
	/**
	 * Master keyboard navigation enable flag.
	 * initNewFrame() will automatically fill getIO().NavInputs[] based on getIO().KeysDown[].
	 */
	int NavEnableKeyboard = 1;
	/**
	 * Master gamepad navigation enable flag. This is mostly to instruct your imgui back-end to fill getIO().NavInputs[].
	 * Back-end also needs to set {@link JImBackendFlags#HasGamepad}.
	 */
	int NavEnableGamepad = 1 << 1;
	/**
	 * Instruct navigation to move the mouse cursor.
	 * May be useful on TV/console systems where moving a virtual mouse is awkward.
	 * Will update io.MousePos and set {@link JImGuiIOGen#isWantSetMousePos()} = true.
	 * If enabled you MUST honor {@link JImGuiIOGen#isWantSetMousePos()} requests in your binding,
	 * otherwise ImGui will react as if the mouse is jumping around back and forth.
	 */
	int NavEnableSetMousePos = 1 << 2;
	/**
	 * Instruct navigation to not call the {@link JImGuiIOGen#setWantCaptureKeyboard(boolean)} flag
	 * with {@link JImGuiIOGen#setNavActive(boolean)} is called.
	 */
	int NavNoCaptureKeyboard = 1 << 3;
	/**
	 * Instruct imgui to clear mouse position/buttons in {@link org.ice1000.jimgui.JImGui#initNewFrame}.
	 * This allows ignoring the mouse information back-end
	 */
	int NoMouse = 1 << 4;
	/** Instruct back-end to not alter mouse cursor shape and visibility. */
	int NoMouseCursorChange = 1 << 5;

	/** Application is SRGB-aware. */
	int IsSRGB = 1 << 20;
	/** Application is using a touch screen instead of a mouse. */
	int IsTouchScreen = 1 << 21;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImConfigFlags.Nothing),
		Nothing(JImConfigFlags.Nothing),
		/** @see JImConfigFlags#NavEnableKeyboard */
		NavEnableKeyboard(JImConfigFlags.NavEnableKeyboard),
		/** @see JImConfigFlags#NavEnableGamepad */
		NavEnableGamepad(JImConfigFlags.NavEnableGamepad),
		/** @see JImConfigFlags#NavEnableSetMousePos */
		NavEnableSetMousePos(JImConfigFlags.NavEnableSetMousePos),
		/** @see JImConfigFlags#NavNoCaptureKeyboard */
		NavNoCaptureKeyboard(JImConfigFlags.NavNoCaptureKeyboard),
		/** @see JImConfigFlags#NoMouse */
		NoMouse(JImConfigFlags.NoMouse),
		/** @see JImConfigFlags#NoMouseCursorChange */
		NoMouseCursorChange(JImConfigFlags.NoMouseCursorChange),
		/** @see JImConfigFlags#IsSRGB */
		IsSRGB(JImConfigFlags.IsSRGB),
		/** @see JImConfigFlags#IsTouchScreen */
		IsTouchScreen(JImConfigFlags.IsTouchScreen);

		public final int flag;

		Type(int flag) {
			this.flag = flag;
		}

		@Override
		public int get() {
			return flag;
		}
	}
}
