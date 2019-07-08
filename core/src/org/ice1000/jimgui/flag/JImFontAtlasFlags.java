package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImFontAtlasFlags {
	int Nothing = 0;
	int NoPowerOfTwoHeight = 1;
	int NoMouseCursors = 1 << 1;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison. 0 Used to prevent ImGui errors from
		 * unexpected results
		 */
		NoSuchFlag(JImFontAtlasFlags.Nothing),
		Nothing(JImFontAtlasFlags.Nothing),
		/** @see JImFontAtlasFlags#NoPowerOfTwoHeight */
		NoPowerOfTwoHeight(JImFontAtlasFlags.NoPowerOfTwoHeight),
		/** @see JImFontAtlasFlags#NoMouseCursors */
		NoMouseCursors(JImFontAtlasFlags.NoMouseCursors);

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
