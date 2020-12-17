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
@SuppressWarnings("WeakerAccess")
public final class JImTextureID {
  /** package-private by design */
  long nativeObjectPtr;
  public final int width;
  public final int height;

  /**
   * private by design
   * you're encouraged to call this if you want to use opengl or dx9 only
   * for opengl, {@code nativeObjectPtr} is {@code GLuint}
   * for dx9, {@code nativeObjectPtr} is {@code LPDIRECT3DTEXTURE9}
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

  @Contract("_, null -> fail") private static @NotNull JImTextureID createJImTextureID(
      @NotNull String errorMessage, long @Nullable [] extractedData) {
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
  public static @NotNull JImTextureID fromUri(@NotNull URI uri) {
    return fromPath(Paths.get(uri));
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
  public static @NotNull JImTextureID fromPath(@NotNull Path path) {
    return fromFile(path.toString());
  }

  /**
   * Create a texture from in-memory files
   *
   * @param rawData raw memory data, directly passed to C++
   * @return the texture
   * @throws IllegalStateException if native interface cannot create texture
   */
  public static @NotNull JImTextureID fromBytes(byte @NotNull [] rawData) {
    long[] texture = createTextureFromBytes(rawData, rawData.length);
    return createJImTextureID("Failed to create texture!", texture);
  }

  /**
   * @param nativeObjectPtr existing texture id, like {@code GLuint} in glfw
   * @param width           size, used in {@link JImGui#image(JImTextureID)}
   * @param height          size, used in {@link JImGui#image(JImTextureID)}
   * @return created texture
   */
  public static @NotNull JImTextureID fromExistingID(long nativeObjectPtr, int width, int height) {
    return new JImTextureID(nativeObjectPtr, width, height);
  }

  private static native long[] createTextureFromFile(byte @NotNull [] fileName);

  private static native long[] createTextureFromBytes(byte @NotNull [] rawData, int size);
}
