package org.ice1000.jimgui.flag;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JImFlagsTest {

    @Test
    public void backendFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImBackendFlags.Type.values(), JImBackendFlags.class);
    }

    @Test
    public void comboFlagst() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImComboFlags.Type.values(), JImComboFlags.class);
    }

    @Test
    public void conditionFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImCondition.Type.values(), JImCondition.class);
    }

    @Test
    public void configFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImConfigFlags.Type.values(), JImConfigFlags.class);
    }

    @Test
    public void directionFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImDirection.Type.values(), JImDirection.class, -1);
    }

    @Test
    public void drawCornerFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImDrawCornerFlags.Type.values(), JImDrawCornerFlags.class);
    }

    @Test
    public void drawListFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImDrawListFlags.Type.values(), JImDrawListFlags.class);
    }

    @Test
    public void focusedFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImFocusedFlags.Type.values(), JImFocusedFlags.class);
    }

    @Test
    public void fontAtlasFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImFontAtlasFlags.Type.values(), JImFontAtlasFlags.class);
    }

    @Test
    public void hoveredFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImHoveredFlags.Type.values(), JImHoveredFlags.class);
    }

    @Test
    public void inputTextFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImInputTextFlags.Type.values(), JImInputTextFlags.class);
    }

    @Test
    public void mouseIndexesFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImMouseButton.Type.values(), JImMouseButton.class, -1);
    }

    @Test
    public void selectableFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImSelectableFlags.Type.values(), JImSelectableFlags.class);
    }

    @Test
    public void tabBarFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImTabBarFlags.Type.values(), JImTabBarFlags.class);
    }

    @Test
    public void tabItemFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImTabItemFlags.Type.values(), JImTabItemFlags.class);
    }

    @Test
    public void textEditFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImTextEditFlags.Type.values(), JImTextEditFlags.class);
    }

    @Test
    public void treeNodeFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImTreeNodeFlags.Type.values(), JImTreeNodeFlags.class);
    }

    @Test
    public void windowFlags() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        checkFlags(JImWindowFlags.Type.values(), JImWindowFlags.class);
    }

    public static void checkFlags(Enum[] flags, Class<?> flagsClazz) throws NoSuchFieldException,
            IllegalAccessException, InstantiationException {
        checkFlags(flags, flagsClazz, 0);
    }

    public static void checkFlags(Enum[] flags, Class<?> flagsClazz, int flag0Value)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        // No Such Flag
        Flag flags0 = (Flag) flags[0];
        assertEquals(flag0Value, flags0.get());

        // Do a reverse lookup and make sure we have the same amount of types as the original flags class
        Field[] declaredFields = flagsClazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            boolean foundFlag = false;
            for (Enum<?> flag : flags) {
                if (flag.name().equals(name)) {
                    foundFlag = true;
                    break;
                }
            }
            assertTrue(foundFlag);
        }

        // Start at one, since the first value should be no such flag
        for (int i = 1; i < flags.length; i++) {
            Enum<?> enumFlag = flags[i];
            Flag flagType = (Flag) enumFlag;
            int flagTypeInt = flagType.get();

            Field field = flagsClazz.getField(enumFlag.name());

            int flagClazzInt = field.getInt(null);
            assertEquals(flagClazzInt, flagTypeInt);
        }
    }
}