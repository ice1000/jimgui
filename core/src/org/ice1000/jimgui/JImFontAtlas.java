package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

/**
 * @author ice1000
 * @since v0.1
 */
public final class JImFontAtlas extends JImGuiFontAtlasGen {
	/** package-private by design */
	JImFontAtlas(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param path path to a ttf file
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path) {
		return addFontFromFile(path, 16);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the font
	 */
	public @NotNull JImFont addDefaultFont() {
		return new JImFont(addFontDefault(nativeObjectPtr));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param path       path to a ttf file
	 * @param sizePixels font size
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path, float sizePixels) {
		return new JImFont(addFontFromFileTTF(path, sizePixels, nativeObjectPtr));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param compressedFontDataBase85 in-memory base85 compressed ttf font data
	 * @param sizePixels               font size
	 * @return the font
	 */
	public @NotNull JImFont addFontFromMemoryCompressedBase85(@NotNull String compressedFontDataBase85, float sizePixels) {
		return new JImFont(addFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, nativeObjectPtr));
	}
}
