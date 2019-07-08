package org.ice1000.jimgui.flag;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlagTest {

    @Test
    public void hasFlag() {
        assertTrue(Flag.hasFlag(2, JImColorEditFlags.Type.NoAlpha, JImColorEditFlags.Type.NoPicker));
    }

    @Test
    public void reverseLookup() {
        assertEquals(JImColorEditFlags.Type.NoAlpha, Flag.reverseLookup(JImColorEditFlags.Type.class, 2));
    }

    @Test
    public void getAsFlags() {
        List<JImColorEditFlags.Type> flags = new ArrayList<>(2);
        flags.add(JImColorEditFlags.Type.NoAlpha);
        flags.add(JImColorEditFlags.Type.NoPicker);
        JImColorEditFlags.Type[] asFlags = Flag.getAsFlags(JImColorEditFlags.Type.class, 6);
        for (int i = 0; i < asFlags.length; i++) {
            JImColorEditFlags.Type asFlag = asFlags[i];
            assertTrue(flags.contains(asFlag));
        }
    }

    @Test
    public void getFlagsValue() {
        assertEquals(6, Flag.getAsValue(JImColorEditFlags.Type.NoAlpha, JImColorEditFlags.Type.NoPicker));
    }
}