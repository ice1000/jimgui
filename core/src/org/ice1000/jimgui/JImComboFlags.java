package org.ice1000.jimgui;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImComboFlags {
	int PopupAlignLeft = 1 << 0;
	int HeightSmall = 1 << 1;
	int HeightRegular = 1 << 2;
	int HeightLarge = 1 << 3;
	int HeightLargest = 1 << 4;
	int NoArrowButton = 1 << 5;
	int NoPreview = 1 << 6;
	int HeightMask_ = HeightSmall | HeightRegular | HeightLarge | HeightLargest;
}
