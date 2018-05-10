package org.ice1000.jimgui.flags;

/**
 * Conditions
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImCondition {
	/** Set the variable */
	int Always = 1;
	int Once = 1 << 1;
	int FirstUseEver = 1 << 2;
	int Appearing = 1 << 3;
}
