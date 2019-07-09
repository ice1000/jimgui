package org.ice1000.jimgui.flag;

/**
 * Conditions
 *
 * @author ice1000
 * @since v0.1
 */
public interface JImCondition {
	int Nothing = 0;
	/** Set the variable */
	int Always = 1;
	/** Set the variable once per runtime session (only the first call with succeed) */
	int Once = 1 << 1;
	/** Set the variable if the object/window has no persistently saved data (no entry in .ini file) */
	int FirstUseEver = 1 << 2;
	/** Set the variable if the object/window is appearing after being hidden/inactive (or the first time) */
	int Appearing = 1 << 3;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImCondition.Nothing),
		Nothing(JImCondition.Nothing),
		/** @see JImCondition#Always */
		Always(JImCondition.Always),
		/** @see JImCondition#Once */
		Once(JImCondition.Once),
		/** @see JImCondition#FirstUseEver */
		FirstUseEver(JImCondition.FirstUseEver),
		/** @see JImCondition#Appearing */
		Appearing(JImCondition.Appearing);

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
