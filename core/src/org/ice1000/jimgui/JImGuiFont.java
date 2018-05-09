package org.ice1000.jimgui;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImGuiFont extends JImGuiFontGen {
	JImGuiFont() {
	}

	public native void setDisplayOffsetX(float newX);
	public native void setDisplayOffsetY(float newY);
	public native void setDisplayOffset(float newX, float newY);
	public native float getDisplayOffsetX();
	public native float getDisplayOffsetY();
}
