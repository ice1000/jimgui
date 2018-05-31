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

	/**
	 * package-private by design
	 *
	 * @param nativeObjectPtr native ImTextureID*
	 *                        have different implementation on difference platforms
	 * @param width           image width
	 * @param height          image height
	 */
	private JImTextureID(long nativeObjectPtr, int width, int height) {
		this.width = width;
		this.height = height;
		this.nativeObjectPtr = nativeObjectPtr;
	}

	public static @NotNull JImTextureID fromFile(@NotNull String fileName) {
		long[] extractedData = createTextureFromFile(getBytes(fileName));
		if (extractedData == null || extractedData.length != 3 || extractedData[0] == 0)
			throw new IllegalStateException("cannot load " + fileName);
		return new JImTextureID(extractedData[0], (int) extractedData[1], (int) extractedData[2]);
	}

	public static @NotNull JImTextureID fromFile(@NotNull URL url) {
		return fromFile(url.getFile());
	}

	public static @NotNull JImTextureID fromFile(@NotNull File file) {
		return fromFile(file.getAbsolutePath());
	}

	public static @NotNull JImTextureID fromFile(@NotNull Path path) {
		return fromFile(path.toString());
	}

	private static native long[] createTextureFromFile(byte @NotNull [] fileName);
}
