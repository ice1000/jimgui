package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImMouseIndexes {
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
		/** @see JImMouseIndexes#Left */
		Left(JImMouseIndexes.Left),
		/** @see JImMouseIndexes#Right */
		Right(JImMouseIndexes.Right),
		/** @see JImMouseIndexes#Middle */
		Middle(JImMouseIndexes.Middle),
		/** @see JImMouseIndexes#ExtraA */
		ExtraA(JImMouseIndexes.ExtraA),
		/** @see JImMouseIndexes#ExtraB */
		ExtraB(JImMouseIndexes.ExtraB);

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
