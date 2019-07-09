package org.ice1000.jimgui.flag;

/**
 * Flags for {@link org.ice1000.jimgui.JImGuiGen#beginCombo}
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImComboFlags {
	int Nothing = 0;
	/** Align the popup toward the left by default */
	int PopupAlignLeft = 1;
	/**
	 * Max ~4 items visible.
	 * Tip: If you want your combo popup to be a specific size you can use SetNextWindowSizeConstraints()
	 * prior to calling {@link org.ice1000.jimgui.JImGui#beginCombo}
	 */
	int HeightSmall = 1 << 1;
	/** Max ~8 items visible (default) */
	int HeightRegular = 1 << 2;
	/** Max ~20 items visible */
	int HeightLarge = 1 << 3;
	/** As many fitting items as possible */
	int HeightLargest = 1 << 4;
	/** Display on the preview box without the square arrow button */
	int NoArrowButton = 1 << 5;
	/** Display only a square arrow button */
	int NoPreview = 1 << 6;
	int HeightMask_ = HeightSmall | HeightRegular | HeightLarge | HeightLargest;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImComboFlags.Nothing),
		Nothing(JImComboFlags.Nothing),
		/** @see JImComboFlags#PopupAlignLeft */
		PopupAlignLeft(JImComboFlags.PopupAlignLeft),
		/** @see JImComboFlags#HeightSmall */
		HeightSmall(JImComboFlags.HeightSmall),
		/** @see JImComboFlags#HeightRegular */
		HeightRegular(JImComboFlags.HeightRegular),
		/** @see JImComboFlags#HeightLarge */
		HeightLarge(JImComboFlags.HeightLarge),
		/** @see JImComboFlags#HeightLargest */
		HeightLargest(JImComboFlags.HeightLargest),
		/** @see JImComboFlags#NoArrowButton */
		NoArrowButton(JImComboFlags.NoArrowButton),
		/** @see JImComboFlags#NoPreview */
		NoPreview(JImComboFlags.NoPreview),

		HeightMask_(JImComboFlags.HeightSmall | JImComboFlags.HeightRegular | JImComboFlags.HeightLarge | JImComboFlags.HeightLargest);

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
