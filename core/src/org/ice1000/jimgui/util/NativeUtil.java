/*
 * Class NativeUtil is published under the The MIT License:
 *
 * Copyright (c) 2012 Adam Heinrich <adam@adamh.cz>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ice1000.jimgui.util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

/**
 * A simple library class which helps with loading dynamic libraries stored in the
 * JAR archive. These libraries usually contain implementation of some methods in
 * native code (using JNI - Java Native Interface).
 *
 * @see <a href="http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar">http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar</a>
 * @see <a href="https://github.com/adamheinrich/native-utils">https://github.com/adamheinrich/native-utils</a>
 */
@SuppressWarnings("WeakerAccess")
public interface NativeUtil {
	@NotNull String NATIVE_FOLDER_PATH_PREFIX = "jimgui";
	@NotNull String TEM_DIR = System.getProperty("java.io.tmpdir");
	boolean isPosixCompliant = isPosixCompliant();

	/**
	 * Loads library from current JAR archive
	 * <p>
	 * The file from JAR is copied into system temporary directory and then loaded. The temporary file is deleted after
	 * exiting.
	 * Method uses String as fileName because the pathname is "abstract", not system-dependent.
	 *
	 * @param fileName    the native library name under {@code /native/}
	 * @param callerClass the caller class used to call {@link Class#getResourceAsStream(String)}
	 * @throws IllegalArgumentException      If source file (param path) does not exist
	 * @throws IllegalArgumentException      If the path is not absolute or if the fileName is shorter than three characters
	 *                                       (restriction of {@link File#createTempFile(String, String)}).
	 * @throws UnsupportedOperationException if {@link UnsatisfiedLinkError} is thrown.
	 */
	static void loadLibraryFromJar(@NotNull String fileName, @NotNull Class<?> callerClass) {
		// Prepare temporary file
		File temporaryDir = createTempDirectory();
		temporaryDir.deleteOnExit();
		String fullPath = "/native/" + fileName;

		File temp = new File(temporaryDir, fileName);
		try (InputStream is = callerClass.getResourceAsStream(fullPath)) {
			if (is == null) throw new UnsupportedOperationException("Native library " + fileName + " not found.");
			Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException | NullPointerException e) {
			if (temp.exists()) System.err.println("Deleting since load failed... " + temp.delete());
		}

		try {
			System.load(temp.getAbsolutePath());
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Got unsatisfied link error, please check the native library " + fileName);
			throw new UnsupportedOperationException(e);
		} finally {
			if (isPosixCompliant) {
				// Assume POSIX compliant file system, can be deleted after loading
				boolean delete = temp.delete();
				if (!delete) System.err.println("Failed to delete native library.");
			} else {
				// Assume non-POSIX, and don't delete until last file descriptor closed
				temp.deleteOnExit();
			}
		}
	}

	static boolean isPosixCompliant() {
		try {
			return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
		} catch (FileSystemNotFoundException | ProviderNotFoundException | SecurityException e) {
			return false;
		}
	}

	static @NotNull File createTempDirectory() {
		File generatedDir = new File(TEM_DIR, NativeUtil.NATIVE_FOLDER_PATH_PREFIX + System.currentTimeMillis());
		if (!generatedDir.mkdir())
			throw new IllegalStateException("Failed to create temp directory " + generatedDir.getName());
		return generatedDir;
	}
}