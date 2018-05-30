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
	 * @param channelsInFile  image channels, it's forced to 4 but not always 4
	 */
	private JImTextureID(long nativeObjectPtr,
	                     int width,
	                     int height,
	                     int channelsInFile) {
		this.width = width;
		this.height = height;
		this.channelsInFile = channelsInFile;
		this.nativeObjectPtr = nativeObjectPtr;
	}

	public static @NotNull JImTextureID fromPNG(@NotNull String fileName) {
		long[] extractedData = createTextureFromFile(getBytes(fileName));
		if (extractedData == null || extractedData.length != 4 || extractedData[0] == 0)
			throw new IllegalStateException("cannot load " + fileName);
		return new JImTextureID(extractedData[0],
				(int) extractedData[1],
				(int) extractedData[2],
				(int) extractedData[3]
		);
	}

	public static @NotNull JImTextureID fromPNG(@NotNull URL url) {
		return fromPNG(url.getFile());
	}

	public static @NotNull JImTextureID fromPNG(@NotNull File file) {
		return fromPNG(file.getAbsolutePath());
	}

	public static @NotNull JImTextureID fromPNG(@NotNull Path path) {
		return fromPNG(path.toString());
	}

	private static native long[] createTextureFromFile(byte @NotNull [] fileName);
}
