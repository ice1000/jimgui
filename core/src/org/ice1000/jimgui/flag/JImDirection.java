package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImDirection {
	int None = -1;
	int Left = 0;
	int Right = 1;
	int Up = 2;
	int Down = 3;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImDirection.None),
		/** @see JImDirection#None */
		None(JImDirection.None),
		/** @see JImDirection#Left */
		Left(JImDirection.Left),
		/** @see JImDirection#Right */
		Right(JImDirection.Right),
		/** @see JImDirection#Up */
		Up(JImDirection.Up),
		/** @see JImDirection#Down */
		Down(JImDirection.Down);

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
