package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImBackendFlags {
	int Nothing = 0;
	/** Back-end supports and has a connected gamepad. */
	int HasGamepad = 1;
	/** Back-end supports reading GetMouseCursor() to change the OS cursor shape. */
	int HasMouseCursors = 1 << 1;
	/**
	 * Back-end supports io.WantSetMousePos requests to reposition the OS mouse position
	 * (only used if {@link JImConfigFlags#NavEnableSetMousePos} is set).
	 */
	int HasSetMousePos = 1 << 2;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImBackendFlags.Nothing),
		/** @see JImBackendFlags#Nothing */
		Nothing(JImBackendFlags.Nothing),
		/** @see JImBackendFlags#HasGamepad */
		HasGamepad(JImBackendFlags.HasGamepad),
		/** @see JImBackendFlags#HasMouseCursors */
		HasMouseCursors(JImBackendFlags.HasMouseCursors),
		/** @see JImBackendFlags#HasSetMousePos */
		HasSetMousePos(JImBackendFlags.HasSetMousePos);

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
