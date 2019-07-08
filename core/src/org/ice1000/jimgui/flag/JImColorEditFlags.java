package org.ice1000.jimgui.flag;

import org.ice1000.jimgui.JImVec4;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImColorEditFlags {
	int Nothing = 0;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#{@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#{@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}(String, JImVec4, int, float, float)}:
	 * ignore Alpha component (read 3 components from the input pointer).
	 */
	int NoAlpha = 1 << 1;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)}:
	 * disable picker when clicking on colored square.
	 */
	int NoPicker = 1 << 2;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)}:
	 * disable toggling options menu when right-clicking on inputs/small preview.
	 */
	int NoOptions = 1 << 3;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * disable colored square preview next to the inputs. (e.g. to show only the inputs)
	 */
	int NoSmallPreview = 1 << 4;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * disable inputs sliders/text widgets (e.g. to show only the small preview colored square).
	 */
	int NoInputs = 1 << 5;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#{@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}(String, JImVec4, int, float, float)}:
	 * disable tooltip when hovering the preview.
	 */
	int NoTooltip = 1 << 6;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * disable display of inline text label (the label is still forwarded to the tooltip and picker).
	 */
	int NoLabel = 1 << 7;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * disable bigger color preview on right side of the picker, use small colored square preview instead.
	 */
	int NoSidePreview = 1 << 8;

	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4)}: disable drag and drop target.
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4)}: disable drag and drop source.
	 */
	int NoDragDrop = 1 << 9;

	// user options

	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * show vertical alpha bar/gradient in picker.
	 */
	int AlphaBar = 1 << 16;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * display preview as a transparent color over a checkerboard, instead of opaque.
	 */
	int AlphaPreview = 1 << 17;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * display half opaque / half checkerboard, instead of opaque.
	 */
	int AlphaPreviewHalf = 1 << 18;
	/**
	 * User option
	 * (WIP) {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)}:
	 * Currently only disable 0.0f..1.0f limits in RGBA edition
	 *
	 * @apiNote you probably want to use {@link JImColorEditFlags#Float} flag as well.
	 */
	int HDR = 1 << 19;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)}: choose one among RGB/HSV/HEX.
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}: choose any combination using RGB/HSV/HEX.
	 */
	int RGB = 1 << 20;
	/** @see JImColorEditFlags#RGB */
	int HSV = 1 << 21;
	/** @see JImColorEditFlags#RGB */
	int HEX = 1 << 22;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * _display_ values formatted as 0..255.
	 */
	int Uint8 = 1 << 23;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)},
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * _display_ values formatted as 0.0f..1.0f floats instead of 0..255 integers. No round-trip of value via integers.
	 */
	int Float = 1 << 24;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * bar for Hue, rectangle for Sat/Value.
	 */
	int PickerHueBar = 1 << 25;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorPicker4(String, JImVec4, int)}:
	 * wheel for Hue, triangle for Sat/Value.
	 */
	int PickerHueWheel = 1 << 26;

	// [Internal] Masks

	int InputsMask = RGB | HSV | HEX;
	int DataTypeMask = Uint8 | Float;
	int PickerMask = PickerHueWheel | PickerHueBar;
	int OptionsDefault = Uint8 | RGB | PickerHueBar;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImColorEditFlags.Nothing),
		Nothing(JImColorEditFlags.Nothing),
		/** @see JImColorEditFlags#NoAlpha */
		NoAlpha(JImColorEditFlags.NoAlpha),
		/** @see JImColorEditFlags#NoPicker */
		NoPicker(JImColorEditFlags.NoPicker),
		/** @see JImColorEditFlags#NoOptions */
		NoOptions(JImColorEditFlags.NoOptions),
		/** @see JImColorEditFlags#NoSmallPreview */
		NoSmallPreview(JImColorEditFlags.NoSmallPreview),
		/** @see JImColorEditFlags#NoInputs */
		NoInputs(JImColorEditFlags.NoInputs),
		/** @see JImColorEditFlags#NoTooltip */
		NoTooltip(JImColorEditFlags.NoTooltip),
		/** @see JImColorEditFlags#NoLabel */
		NoLabel(JImColorEditFlags.NoLabel),
		/** @see JImColorEditFlags#NoSidePreview */
		NoSidePreview(JImColorEditFlags.NoSidePreview),
		/** @see JImColorEditFlags#NoDragDrop */
		NoDragDrop(JImColorEditFlags.NoDragDrop),

		// user options

		/** @see JImColorEditFlags#AlphaBar */
		AlphaBar(JImColorEditFlags.AlphaBar),
		/** @see JImColorEditFlags#AlphaPreview */
		AlphaPreview(JImColorEditFlags.AlphaPreview),
		/** @see JImColorEditFlags#AlphaPreviewHalf */
		AlphaPreviewHalf(JImColorEditFlags.AlphaPreviewHalf),
		/** @see JImColorEditFlags#HDR */
		HDR(JImColorEditFlags.HDR),
		/** @see JImColorEditFlags#RGB */
		RGB(JImColorEditFlags.RGB),
		/** @see JImColorEditFlags#HSV */
		HSV(JImColorEditFlags.HSV),
		/** @see JImColorEditFlags#HEX */
		HEX(JImColorEditFlags.HEX),
		/** @see JImColorEditFlags#Uint8 */
		Uint8(JImColorEditFlags.Uint8),
		/** @see JImColorEditFlags#Float */
		Float(JImColorEditFlags.Float),
		/** @see JImColorEditFlags#PickerHueBar */
		PickerHueBar(JImColorEditFlags.PickerHueBar),
		/** @see JImColorEditFlags#PickerHueWheel */
		PickerHueWheel(JImColorEditFlags.PickerHueWheel),


		// [Internal] Masks

		InputsMask(JImColorEditFlags.RGB | JImColorEditFlags.HSV | JImColorEditFlags.HEX ),
		DataTypeMask(JImColorEditFlags.Uint8 | JImColorEditFlags.Float),
		PickerMask (JImColorEditFlags.PickerHueWheel | JImColorEditFlags.PickerHueBar),
		OptionsDefault(JImColorEditFlags.Uint8 | JImColorEditFlags.RGB | JImColorEditFlags.PickerHueBar);

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
