/*
 * PolyMc
 * Copyright (C) 2020-2020 TheEpicBlock_TEB
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; If not, see <https://www.gnu.org/licenses>.
 */
package io.github.theepicblock.polymc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class Util {
    /**
     * Returns true if this identifier is in the minecraft namespace
     */
    public static boolean isVanilla(Identifier id) {
        if (id == null) return false;
        return isNamespaceVanilla(id.getNamespace());
    }

    /**
     * Returns true if this namespace is minecraft
     */
    public static boolean isNamespaceVanilla(String v) {
        return v.equals("minecraft");
    }

    /**
     * Get a BlockState using the properties from a string
     * @param block  base block on which the properties are applied
     * @param string the properties which define this blockstate. Eg: "facing=north,lit=false"
     * @return the blockstate
     */
    public static BlockState getBlockStateFromString(Block block, String string) {
        BlockState v = block.getDefaultState();
        for (String property : string.split(",")) {
            String[] t = property.split("=");
            if (t.length != 2) continue;
            String key = t[0];
            String value = t[1];

            Property<?> prop = block.getStateManager().getProperty(key);
            if (prop != null) {
                v = parseAndAddBlockState(v, prop, value);
            }
        }
        return v;
    }

    private static <T extends Comparable<T>> BlockState parseAndAddBlockState(BlockState v, Property<T> property, String value) {
        Optional<T> optional = property.parse(value);
        if (optional.isPresent()) {
            return v.with(property, optional.get());
        }
        return v;
    }

    /**
     * Get the properties of a blockstate as a string
     * @param state state to extract properties from
     * @return "facing=north,lit=false" for example
     */
    public static String getPropertiesFromBlockState(BlockState state) {
        StringBuilder v = new StringBuilder();
        state.getEntries().forEach((property, value) -> {
            v.append(property.getName());
            v.append("=");
            v.append(nameValue(property, value));
            v.append(",");
        });
        String res = v.toString();
        if (res.length() == 0) return res;
        return res.substring(0, res.length() - 1); //this removes the last comma
    }

    private static <T extends Comparable<T>> String nameValue(Property<T> property, Comparable<?> value) {
        return property.name((T)value);
    }

    /**
     * adds spaces to the end of string s so it has amount length
     */
    public static String expandTo(String s, int amount) {
        int left = amount - s.length();
        if (left >= 0) {
            StringBuilder out = new StringBuilder().append(s);
            for (int i = 0; i < left; i++) {
                out.append(" ");
            }
            return out.toString();
        }
        return s;
    }

    public static String expandTo(Object s, int amount) {
        return expandTo(s.toString(), amount);
    }
}
