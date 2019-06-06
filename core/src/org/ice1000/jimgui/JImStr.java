package org.ice1000.jimgui;

import org.jetbrains.annotations.NotNull;

import static org.ice1000.jimgui.util.JImGuiUtil.getBytes;

/**
 * An alternative to {@link String} used in
 * {@link JImGui} functions.
 * Reduces memory allocations.
 *
 * @author ice1000
 * @since 0.8.2
 */
public final class JImStr {
	final byte[] bytes;

	public JImStr(@NotNull String source) {
		bytes = getBytes(source);
	}
}
