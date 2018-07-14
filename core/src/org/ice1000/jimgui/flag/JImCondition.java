package org.ice1000.jimgui.flag;

/**
 * Conditions
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImCondition {
	/** Set the variable */
	int Always = 1;
	/** Set the variable once per runtime session (only the first call with succeed) */
	int Once = 1 << 1;
	/** Set the variable if the object/window has no persistently saved data (no entry in .ini file) */
	int FirstUseEver = 1 << 2;
	/** Set the variable if the object/window is appearing after being hidden/inactive (or the first time) */
	int Appearing = 1 << 3;
}
