package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

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

	public final int width;
	public final int height;
	public final int channelsInFile;

	/**
	 * package-private by design
	 *
	 * @param nativeObjectPtr native ImTextureID*
	 *                        have different implementation on difference platforms
	 * @param width           image width
	 * @param height          image height
	 * @param channelsInFile
	 */
	private JImTextureID(long nativeObjectPtr, int width, int height, int channelsInFile) {
		this.width = width;
		this.height = height;
		this.channelsInFile = channelsInFile;
		if (nativeObjectPtr == 0)
			throw new IllegalArgumentException("Native object is null, please check if you're using ");
		this.nativeObjectPtr = nativeObjectPtr;
	}

	public static @NotNull JImTextureID fromPNG(@NotNull String fileName) {
		long[] extractedData = createTextureFromFile(getBytes(fileName));
		return new JImTextureID(extractedData[0], (int) extractedData[1], (int) extractedData[2], (int) extractedData[3]);
	}

	public static @NotNull JImTextureID fromPNG(@NotNull URL file) {
		return fromPNG(file.getFile());
	}

	public static @NotNull JImTextureID fromPNG(@NotNull File file) {
		return fromPNG(file.getAbsolutePath());
	}

	public static @NotNull JImTextureID fromPNG(@NotNull Path path) {
		return fromPNG(path.toString());
	}

	public static native long @NotNull [] createTextureFromFile(byte @NotNull [] fileName);
}
