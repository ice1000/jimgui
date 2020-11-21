package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImMouseButton {
	int Left = 0;
	int Right = 1;
	int Middle = 2;
	int ExtraA = 3;
	int ExtraB = 4;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(-1),
		/** @see JImMouseButton#Left */
		Left(JImMouseButton.Left),
		/** @see JImMouseButton#Right */
		Right(JImMouseButton.Right),
		/** @see JImMouseButton#Middle */
		Middle(JImMouseButton.Middle),
		/** @see JImMouseButton#ExtraA */
		ExtraA(JImMouseButton.ExtraA),
		/** @see JImMouseButton#ExtraB */
		ExtraB(JImMouseButton.ExtraB);

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
