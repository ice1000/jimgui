package org.ice1000.jimgui.flag;

/**
 * Flags for {@link org.ice1000.jimgui.JImDrawList}
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImDrawCornerFlags {
	int Nothing = 0;
	/** 0x1 */
	int TopLeft = 1;
	/** 0x2 */
	int TopRight = 1 << 1;
	/** 0x4 */
	int BotLeft = 1 << 2;
	/** 0x8 */
	int BotRight = 1 << 3;
	/** 0x3 */
	int Top = TopLeft | TopRight;
	/** 0xC */
	int Bot = BotLeft | BotRight;
	/** 0x5 */
	int Left = TopLeft | BotLeft;
	/** 0xA */
	int Right = TopRight | BotRight;
	/**
	 * In your function calls you may use ~0
	 * (= all bits sets) instead of this, as a convenience
	 */
	int All = 0xF;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImDrawCornerFlags.Nothing),
		Nothing(JImDrawCornerFlags.Nothing),
		/** @see JImDrawCornerFlags#TopLeft */
		TopLeft(JImDrawCornerFlags.TopLeft),
		/** @see JImDrawCornerFlags#TopRight */
		TopRight(JImDrawCornerFlags.TopRight),
		/** @see JImDrawCornerFlags#BotLeft */
		BotLeft(JImDrawCornerFlags.BotLeft),
		/** @see JImDrawCornerFlags#BotRight */
		BotRight(JImDrawCornerFlags.BotRight),
		/** @see JImDrawCornerFlags#Top */
		Top(JImDrawCornerFlags.Top),
		/** @see JImDrawCornerFlags#Bot */
		Bot(JImDrawCornerFlags.Bot),
		/** @see JImDrawCornerFlags#Left */
		Left(JImDrawCornerFlags.Left),
		/** @see JImDrawCornerFlags#Right */
		Right(JImDrawCornerFlags.Right),
		/** @see JImDrawCornerFlags#All */
		All(JImDrawCornerFlags.All);

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
