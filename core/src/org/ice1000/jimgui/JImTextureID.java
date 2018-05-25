package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * <strong>Cannot use after closing the current window</strong>
 *
 * @author ice1000
 * @since v0.2
 */
public class JImTextureID {
	/** package-private by design */
	long nativeObjectPtr;

	/**
	 * package-private by design
	 *
	 * @param nativeObjectPtr native ImTextureID*
	 *                        have different implementation on difference platforms
	 */
	private JImTextureID(long nativeObjectPtr) {
		if (nativeObjectPtr == 0) throw new IllegalArgumentException("Native object is null, please check if you're using ");
		this.nativeObjectPtr = nativeObjectPtr;
	}

	public JImTextureID(@NotNull String fileName) {
		this(createTextureFromFile(getBytes(fileName)));
	}

	public static native long createTextureFromFile(byte @NotNull [] fileName);
}
