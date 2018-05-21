package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImSelectableFlags {
	int Nothing = 0;
	/** Clicking this don't close parent popup window */
	int DontClosePopups = 1;
	/** Selectable frame can span all columns (text will still fit in current column) */
	int SpanAllColumns = 1 << 1;
	/** Generate press events on double clicks too */
	int AllowDoubleClick = 1 << 2;
}
