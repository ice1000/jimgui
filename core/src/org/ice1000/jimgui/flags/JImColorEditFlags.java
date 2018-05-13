package org.ice1000.jimgui.flags;

import org.ice1000.jimgui.JImVec4;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImColorEditFlags {
	int Nothing = 0;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker,
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
	 * ColorPicker:
	 * disable colored square preview next to the inputs. (e.g. to show only the inputs)
	 */
	int NoSmallPreview = 1 << 4;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker:
	 * disable inputs sliders/text widgets (e.g. to show only the small preview colored square).
	 */
	int NoInputs = 1 << 5;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker,
	 * {@link org.ice1000.jimgui.JImGuiGen#{@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}(String, JImVec4, int, float, float)}:
	 * disable tooltip when hovering the preview.
	 */
	int NoTooltip = 1 << 6;
	/**
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker:
	 * disable display of inline text label (the label is still forwarded to the tooltip and picker).
	 */
	int NoLabel = 1 << 7;
	/**
	 * ColorPicker:
	 * disable bigger color preview on right side of the picker, use small colored square preview instead.
	 */
	int NoSidePreview = 1 << 8;

	// user options

	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker:
	 * show vertical alpha bar/gradient in picker.
	 */
	int AlphaBar = 1 << 9;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker,
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * display preview as a transparent color over a checkerboard, instead of opaque.
	 */
	int AlphaPreview = 1 << 10;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker,
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * display half opaque / half checkerboard, instead of opaque.
	 */
	int AlphaPreviewHalf = 1 << 11;
	/**
	 * User option
	 * (WIP) {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)}:
	 * Currently only disable 0.0f..1.0f limits in RGBA edition
	 *
	 * @apiNote you probably want to use {@link JImColorEditFlags#Float} flag as well.
	 */
	int HDR = 1 << 12;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)}: choose one among RGB/HSV/HEX.
	 * ColorPicker: choose any combination using RGB/HSV/HEX.
	 */
	int RGB = 1 << 13;
	/** @see JImColorEditFlags#RGB */
	int HSV = 1 << 14;
	/** @see JImColorEditFlags#RGB */
	int HEX = 1 << 15;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker,
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * _display_ values formatted as 0..255.
	 */
	int Uint8 = 1 << 16;
	/**
	 * User option
	 * {@link org.ice1000.jimgui.JImGuiGen#colorEdit4(String, JImVec4, int)},
	 * ColorPicker,
	 * {@link org.ice1000.jimgui.JImGuiGen#colorButton(String, JImVec4, int, float, float)}:
	 * _display_ values formatted as 0.0f..1.0f floats instead of 0..255 integers. No round-trip of value via integers.
	 */
	int Float = 1 << 17;
	/**
	 * User option
	 * ColorPicker:
	 * bar for Hue, rectangle for Sat/Value.
	 */
	int PickerHueBar = 1 << 18;
	/**
	 * User option
	 * ColorPicker:
	 * wheel for Hue, triangle for Sat/Value.
	 */
	int PickerHueWheel = 1 << 19;

	// [Internal] Masks

	int InputsMask = RGB | HSV | HEX;
	int DataTypeMask = Uint8 | Float;
	int PickerMask = PickerHueWheel | PickerHueBar;
	int OptionsDefault = Uint8 | RGB | PickerHueBar;
}
