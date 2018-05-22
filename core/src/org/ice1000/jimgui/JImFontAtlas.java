package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

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
	 * @param path       path to a ttf file
	 * @param sizePixels font size
	 * @param glyphRange see {@link JImFontAtlas#getGlyphRangesForChinese()},
	 *                   {@link JImFontAtlas#getGlyphRangesForDefault()},
	 *                   {@link JImFontAtlas#getGlyphRangesForJapanese()},
	 *                   {@link JImFontAtlas#getGlyphRangesForCyrillic()},
	 *                   {@link JImFontAtlas#getGlyphRangesForThai()},
	 *                   {@link JImFontAtlas#getGlyphRangesForKorean()}
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path, float sizePixels, @NotNull NativeShort glyphRange) {
		return new JImFont(addFontFromFileTTF0(getBytes(path), sizePixels, glyphRange.nativeObjectPtr, nativeObjectPtr));
	}

	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForDefault() {
		return new NativeShort(super.getGlyphRangesDefault());
	}
	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForKorean() {
		return new NativeShort(super.getGlyphRangesKorean());
	}
	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForJapanese() {
		return new NativeShort(super.getGlyphRangesJapanese());
	}
	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForChinese() {
		return new NativeShort(super.getGlyphRangesChinese());
	}
	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForCyrillic() {
		return new NativeShort(super.getGlyphRangesCyrillic());
	}
	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForThai() {
		return new NativeShort(super.getGlyphRangesThai());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param compressedFontDataBase85 in-memory base85 compressed ttf font data
	 * @param sizePixels               font size
	 * @return the font
	 */
	public @NotNull JImFont addFontFromMemoryCompressedBase85(@NotNull String compressedFontDataBase85,
	                                                          float sizePixels) {
		return new JImFont(addFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, nativeObjectPtr));
	}

	private static native long addFontFromFileTTF0(byte[] path,
	                                               float sizePixels,
	                                               long nativeShortPtr,
	                                               long nativeObjectPtr);
}
