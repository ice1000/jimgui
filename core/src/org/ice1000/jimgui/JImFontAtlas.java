package org.ice1000.jimgui;

import org.ice1000.jimgui.cpp.DeallocatableObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * @author ice1000
 * @since v0.1
 */
public final class JImFontAtlas extends JImGuiFontAtlasGen implements DeallocatableObject {
	/** package-private by design */
	@Contract(pure = true)
	JImFontAtlas(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}

	/**
	 * @apiNote Should call {@link JImFontAtlas#deallocateNativeObject()}.
	 * @see JImFont#getContainerAtlas()
	 */
	@Contract
	public JImFontAtlas() {
		this(allocateNativeObject());
	}

	public void addCustomRectFontGlyph(@NotNull JImFont font, char id, int width, int height, float advanceX) {
		super.addCustomRectFontGlyph(font, (short) id, width, height, advanceX);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the font
	 */
	public @NotNull JImFont addDefaultFont() {
		return new JImFont(addFontDefault(0, nativeObjectPtr));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the font
	 */
	public @NotNull JImFont addDefaultFont(@NotNull JImFontConfig config) {
		return new JImFont(addFontDefault(config, nativeObjectPtr));
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
	 * @param path       path to a ttf file
	 * @param sizePixels font size
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path, float sizePixels, @NotNull JImFontConfig config) {
		return new JImFont(addFontFromFileTTF0(getBytes(path), sizePixels, config.nativeObjectPtr, 0, nativeObjectPtr));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param path       path to a ttf file
	 * @param sizePixels font size
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path, float sizePixels) {
		return new JImFont(addFontFromFileTTF0(getBytes(path), sizePixels, 0, 0, nativeObjectPtr));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param path       path to a ttf file
	 * @param sizePixels font size
	 * @param glyphRange see {@link JImFontAtlas#getGlyphRangesForChineseFull()},
	 *                   {@link JImFontAtlas#getGlyphRangesForChineseSimplifiedCommon()},
	 *                   {@link JImFontAtlas#getGlyphRangesForDefault()},
	 *                   {@link JImFontAtlas#getGlyphRangesForJapanese()},
	 *                   {@link JImFontAtlas#getGlyphRangesForCyrillic()},
	 *                   {@link JImFontAtlas#getGlyphRangesForThai()},
	 *                   {@link JImFontAtlas#getGlyphRangesForKorean()}
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path, float sizePixels, @NotNull NativeShort glyphRange) {
		return new JImFont(addFontFromFileTTF0(getBytes(path), sizePixels, 0, glyphRange.nativeObjectPtr, nativeObjectPtr));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param path       path to a ttf file
	 * @param sizePixels font size
	 * @param glyphRange see {@link JImFontAtlas#getGlyphRangesForChineseFull()},
	 *                   {@link JImFontAtlas#getGlyphRangesForChineseSimplifiedCommon()},
	 *                   {@link JImFontAtlas#getGlyphRangesForDefault()},
	 *                   {@link JImFontAtlas#getGlyphRangesForJapanese()},
	 *                   {@link JImFontAtlas#getGlyphRangesForCyrillic()},
	 *                   {@link JImFontAtlas#getGlyphRangesForThai()},
	 *                   {@link JImFontAtlas#getGlyphRangesForKorean()}
	 * @return the font
	 */
	public @NotNull JImFont addFontFromFile(@NotNull String path,
	                                        float sizePixels,
	                                        @NotNull JImFontConfig config,
	                                        @NotNull NativeShort glyphRange) {
		return new JImFont(addFontFromFileTTF0(getBytes(path),
				sizePixels,
				config.nativeObjectPtr,
				glyphRange.nativeObjectPtr,
				nativeObjectPtr));
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
	public @NotNull NativeShort getGlyphRangesForChineseFull() {
		return new NativeShort(super.getGlyphRangesChineseFull());
	}
	/** Do NOT deallocate the returned pointer */
	@Contract(pure = true)
	public @NotNull NativeShort getGlyphRangesForChineseSimplifiedCommon() {
		return new NativeShort(super.getGlyphRangesChineseSimplifiedCommon());
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

	private static native long allocateNativeObject();
	private static native void deallocateNativeObject(long nativeObjectPtr);

	@Override
	public void deallocateNativeObject() {
		deallocateNativeObject(nativeObjectPtr);
	}

	private static native long addFontFromFileTTF0(byte[] path,
	                                               float sizePixels,
	                                               long nativeConfigPtr,
	                                               long nativeShortPtr,
	                                               long nativeObjectPtr);
}
