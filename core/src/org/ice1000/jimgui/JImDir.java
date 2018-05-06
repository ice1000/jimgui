package org.ice1000.jimgui;

/**
 * @author ice1000
 * @since v0.1
 */
public enum JImDir {
	ImGuiDir_None(-1), ImGuiDir_Left(0), ImGuiDir_Right(1), ImGuiDir_Up(2), ImGuiDir_Down(3),;

	/** package-private by design */
	int intValue;

	JImDir(int intValue) {
		this.intValue = intValue;
	}
}
