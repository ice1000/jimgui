package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * <strong>Cannot use after closing the current window</strong>
 *
 * @author ice1000
 * @since v0.2
 */
public final class JImTextureID {
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
		return createJImTextureID("cannot load " + fileName, createTextureFromFile(getBytes(fileName)));
	}

	@Contract("_, null -> fail")
	private static @NotNull JImTextureID createJImTextureID(@NotNull String errorMessage, @Nullable long[] extractedData) {
		if (extractedData == null || extractedData.length != 3 || extractedData[0] == 0)
			throw new IllegalStateException(errorMessage);
		return new JImTextureID(extractedData[0], (int) extractedData[1], (int) extractedData[2]);
	}

	/**
	 * Create a texture from file.
	 *
	 * @param uri file path
	 * @return the texture
	 * @throws IllegalStateException if load failed
	 */
	public static @NotNull JImTextureID fromFile(@NotNull URI uri) {
		return fromFile(Paths.get(uri));
	}

	/**
	 * Create a texture from file.
	 *
	 * @param file file instance
	 * @return the texture
	 * @throws IllegalStateException if load failed
	 */
	public static @NotNull JImTextureID fromFile(@NotNull File file) {
		return fromFile(file.getAbsolutePath());
	}

	/**
	 * Create a texture from file.
	 *
	 * @param path file path
	 * @return the texture
	 * @throws IllegalStateException if load failed
	 */
	public static @NotNull JImTextureID fromFile(@NotNull Path path) {
		return fromFile(path.toString());
	}

	/**
	 * Create a texture from in-memory files
	 *
	 * @param rawData raw memory data, directly passed to C++
	 * @return the texture
	 * @throws IllegalStateException if native interface cannot create texture
	 */
	public static @NotNull JImTextureID fromBytes(@NotNull byte[] rawData) {
		long[] texture = createTextureFromBytes(rawData, rawData.length);
		return createJImTextureID("Failed to create texture!", texture);
	}

	private static native long[] createTextureFromFile(byte @NotNull [] fileName);
	private static native long[] createTextureFromBytes(byte @NotNull [] rawData, int size);
}
