package org.ice1000.jimgui.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * Private state shared in this package.
 *
 * @author ice1000
 * @since v0.12
 */
class SharedState {
	static boolean isLoaded = false;
	static @Nullable Function<String, byte[]> STRING_TO_BYTES = null;

	@Contract(value = "!null -> !null; null -> null", pure = true)
	static byte @Nullable [] getBytesDefaultImpl(@Nullable String text) {
		return text != null ? (text + '\0').getBytes(StandardCharsets.UTF_8) : null;
	}
}
