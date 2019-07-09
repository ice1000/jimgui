package org.ice1000.jimgui.flag;

import org.ice1000.jimgui.JImGuiGen;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImTreeNodeFlags {
	int Nothing = 0;
	/** Draw as selected */
	int Selected = 1;
	/** Full colored frame (e.g. for {@link JImTreeNodeFlags#CollapsingHeader}) */
	int Framed = 1 << 1;
	/** Hit testing to allow subsequent widgets to overlap this one */
	int AllowItemOverlap = 1 << 2;
	/** Don't do a TreePush() when open (e.g. for {@link JImTreeNodeFlags#CollapsingHeader}) = no extra indent nor pushing on ID stack */
	int NoTreePushOnOpen = 1 << 3;
	/** Don't automatically and temporarily open node when Logging is active (by default logging will automatically open tree nodes) */
	int NoAutoOpenOnLog = 1 << 4;
	/** Default node to be open */
	int DefaultOpen = 1 << 5;
	/** Need double-click to open node */
	int OpenOnDoubleClick = 1 << 6;
	/**
	 * Only open when clicking on the arrow part.
	 * If {@link JImTreeNodeFlags#OpenOnDoubleClick} is also set, single-click arrow or double-click all box to open.
	 */
	int OpenOnArrow = 1 << 7;
	/** No collapsing, no arrow (use as a convenience for leaf nodes). */
	int Leaf = 1 << 8;
	/** Display a bullet instead of arrow */
	int Bullet = 1 << 9;
	/** Use this (even for an unframed text node) to vertically align text baseline to regular widget height. Equivalent to calling AlignTextToFramePadding(). */
	int FramePadding = 1 << 10;
	//** FIXME: TODO: Extend hit box horizontally even if not framed */
	// int SpanAllAvailWidth = 1 << 11;
	//** FIXME: TODO: Disable automatic scroll on TreePop() if node got just open and contents is not visible */
	// int NoScrollOnOpen = 1 << 12;
	/**
	 * (WIP) Nav: left direction may move to this {@link JImGuiGen#treeNode(String)} from any of its child
	 * (items submitted between {@link JImGuiGen#treeNode(String)} and {@link JImGuiGen#treePop()})
	 */
	int NavLeftJumpsBackHere = 1 << 13;
	int CollapsingHeader = Framed | NoAutoOpenOnLog;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImTreeNodeFlags.Nothing),
		Nothing(JImTreeNodeFlags.Nothing),
		/** @see JImTreeNodeFlags#Selected */
		Selected(JImTreeNodeFlags.Selected),
		/** @see JImTreeNodeFlags#Framed */
		Framed(JImTreeNodeFlags.Framed),
		/** @see JImTreeNodeFlags#AllowItemOverlap */
		AllowItemOverlap(JImTreeNodeFlags.AllowItemOverlap),
		/** @see JImTreeNodeFlags#NoTreePushOnOpen */
		NoTreePushOnOpen(JImTreeNodeFlags.NoTreePushOnOpen),
		/** @see JImTreeNodeFlags#NoAutoOpenOnLog */
		NoAutoOpenOnLog(JImTreeNodeFlags.NoAutoOpenOnLog),
		/** @see JImTreeNodeFlags#DefaultOpen */
		DefaultOpen(JImTreeNodeFlags.DefaultOpen),
		/** @see JImTreeNodeFlags#OpenOnDoubleClick */
		OpenOnDoubleClick(JImTreeNodeFlags.OpenOnDoubleClick),
		/** @see JImTreeNodeFlags#OpenOnArrow */
		OpenOnArrow(JImTreeNodeFlags.OpenOnArrow),
		/** @see JImTreeNodeFlags#Leaf */
		Leaf(JImTreeNodeFlags.Leaf),
		/** @see JImTreeNodeFlags#Bullet */
		Bullet(JImTreeNodeFlags.Bullet),
		/** @see JImTreeNodeFlags#FramePadding */
		FramePadding(JImTreeNodeFlags.FramePadding),
		/** @see JImTreeNodeFlags#NavLeftJumpsBackHere */
		NavLeftJumpsBackHere(JImTreeNodeFlags.NavLeftJumpsBackHere),
		/** @see JImTreeNodeFlags#CollapsingHeader */
		CollapsingHeader(JImTreeNodeFlags.CollapsingHeader);

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
