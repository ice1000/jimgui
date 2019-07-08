package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.5
 */
public interface JImTextEditFlags {
	int Nothing = 0;
	/** Allow 0123456789.*+-/ */
	int CharsDecimal = 1;
	/** Allow 0123456789ABCDEFabcdef */
	int CharsHexadecimal = 1 << 1;
	/** Turn a..z into A..Z */
	int CharsUppercase = 1 << 2;
	/** Filter out spaces, tabs */
	int CharsNoBlank = 1 << 3;
	/** Select entire text when first taking mouse focus */
	int AutoSelectAll = 1 << 4;
	/** Return 'true' when Enter is pressed (as opposed to when the value was modified) */
	int EnterReturnsTrue = 1 << 5;
	/** Call user function on pressing TAB (for completion handling) */
	int CallbackCompletion = 1 << 6;
	/** Call user function on pressing Up/Down arrows (for history handling) */
	int CallbackHistory = 1 << 7;
	/** Call user function every time. User code may query cursor position, modify text buffer. */
	int CallbackAlways = 1 << 8;
	/** Call user function to filter character. Modify data->EventChar to replace/filter input, or return 1 to discard character. */
	int CallbackCharFilter = 1 << 9;
	/** Pressing TAB input a '\t' character into the text field */
	int AllowTabInput = 1 << 10;
	/** In multi-line mode, unfocus with Enter, add new line with Ctrl+Enter (default is opposite: unfocus with Ctrl+Enter, add line with Enter). */
	int CtrlEnterForNewLine = 1 << 11;
	/** Disable following the cursor horizontally */
	int NoHorizontalScroll = 1 << 12;
	/** Insert mode */
	int AlwaysInsertMode = 1 << 13;
	/** Read-only mode */
	int ReadOnly = 1 << 14;
	/** Password mode, display all characters as '*' */
	int Password = 1 << 15;
	/** Disable undo/redo. Note that input text owns the text data while active, if you want to provide your own undo/redo stack you need e.g. to call ClearActiveID(). */
	int NoUndoRedo = 1 << 16;
	/** Allow 0123456789.+*-/eE(Scientific notation input) */
	int CharsScientific = 1 << 17;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImTextEditFlags.Nothing),
		Nothing(JImTextEditFlags.Nothing),
		/** @see JImTextEditFlags#CharsDecimal */
		CharsDecimal(JImTextEditFlags.CharsDecimal),
		/** @see JImTextEditFlags#CharsHexadecimal */
		CharsHexadecimal(JImTextEditFlags.CharsHexadecimal),
		/** @see JImTextEditFlags#CharsUppercase */
		CharsUppercase(JImTextEditFlags.CharsUppercase),
		/** @see JImTextEditFlags#CharsNoBlank */
		CharsNoBlank(JImTextEditFlags.CharsNoBlank),
		/** @see JImTextEditFlags#AutoSelectAll */
		AutoSelectAll(JImTextEditFlags.AutoSelectAll),
		/** @see JImTextEditFlags#EnterReturnsTrue */
		EnterReturnsTrue(JImTextEditFlags.EnterReturnsTrue),
		/** @see JImTextEditFlags#CallbackCompletion */
		CallbackCompletion(JImTextEditFlags.CallbackCompletion),
		/** @see JImTextEditFlags#CallbackHistory */
		CallbackHistory(JImTextEditFlags.CallbackHistory),
		/** @see JImTextEditFlags#CallbackAlways */
		CallbackAlways(JImTextEditFlags.CallbackAlways),
		/** @see JImTextEditFlags#CallbackCharFilter */
		CallbackCharFilter(JImTextEditFlags.CallbackCharFilter),
		/** @see JImTextEditFlags#AllowTabInput */
		AllowTabInput(JImTextEditFlags.AllowTabInput),
		/** @see JImTextEditFlags#CtrlEnterForNewLine */
		CtrlEnterForNewLine(JImTextEditFlags.CtrlEnterForNewLine),
		/** @see JImTextEditFlags#NoHorizontalScroll */
		NoHorizontalScroll(JImTextEditFlags.NoHorizontalScroll),
		/** @see JImTextEditFlags#AlwaysInsertMode */
		AlwaysInsertMode(JImTextEditFlags.AlwaysInsertMode),
		/** @see JImTextEditFlags#ReadOnly */
		ReadOnly(JImTextEditFlags.ReadOnly),
		/** @see JImTextEditFlags#Password */
		Password(JImTextEditFlags.Password),
		/** @see JImTextEditFlags#NoUndoRedo */
		NoUndoRedo(JImTextEditFlags.NoUndoRedo),
		/** @see JImTextEditFlags#CharsScientific */
		CharsScientific(JImTextEditFlags.CharsScientific);

		public final int flag;

		Type(int flag) {
			this.flag = flag;
		}

		@Override
		public int get() {
			return flag;
		}
	}
}
