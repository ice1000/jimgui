package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImSelectableFlags {
	int Nothing = 0;
	/** Clicking this don't close parent popup window */
	int DontClosePopups = 1;
	/** Selectable frame can span all columns (text will still fit in current column) */
	int SpanAllColumns = 1 << 1;
	/** Generate press events on double clicks too */
	int AllowDoubleClick = 1 << 2;
	/** Cannot be selected, display grayed out text */
	int Disabled = 1 << 3;
	/** (WIP) Hit testing to allow subsequent widgets to overlap this one */
	int AllowItemOverlap = 1 << 4;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImSelectableFlags.Nothing),
		Nothing(JImSelectableFlags.Nothing),
		/** @see JImSelectableFlags#DontClosePopups */
		DontClosePopups(JImSelectableFlags.DontClosePopups),
		/** @see JImSelectableFlags#SpanAllColumns */
		SpanAllColumns(JImSelectableFlags.SpanAllColumns),
		/** @see JImSelectableFlags#AllowDoubleClick */
		AllowDoubleClick(JImSelectableFlags.AllowDoubleClick);

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
