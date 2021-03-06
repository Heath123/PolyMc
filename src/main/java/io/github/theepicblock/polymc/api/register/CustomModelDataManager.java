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
package io.github.theepicblock.polymc.api.register;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;

/**
 * Helper class to prevent CustomModelData values from conflicting
 * For example, a mod can request 100 CustomModelData values for a specific item. Then those will be reserved and another mod will get different values
 */
public class CustomModelDataManager {
    private final Object2IntMap<Item> CustomModelDataCurrent = new Object2IntOpenHashMap<>();
    private final Item[] DEFAULT_ITEMS = {
            Items.STICK,
            Items.GLISTERING_MELON_SLICE,
            Items.EMERALD,
            Items.IRON_NUGGET,
            Items.GOLD_NUGGET,
            Items.GOLD_INGOT,
            Items.NETHER_STAR
    };

    /**
     * Request an amount of CMD values you need for a specific item. To prevent CMD values from conflicting
     * If you don't specifically need this item it's recommended to use {@link #RequestCMDwithItem}
     * Example: you request 5 values for a carrot on a stick. You get the number 2 back. You can now use the values 2,3,4,5,6 in your code and resourcepack.
     * @param item   the item you need CMD for
     * @param amount the amount of CMD values you're requesting
     * @return the first value you can use.
     * @throws ArithmeticException if the limit of CustomModelData is reached
     */
    public int RequestCMDValue(Item item, int amount) throws ArithmeticException {
        int current = CustomModelDataCurrent.getInt(item); //this is the current CMD that we're at for this item/
        if (current == 0) {
            current = 1; //we should start at 1. Never 0
        }
        int newValue = Math.addExact(current, amount);
        CustomModelDataCurrent.put(item, newValue);
        return current;
    }

    /**
     * Request 1 CMD value for a specific item. To prevent CMD values from conflicting
     * Example: you request 1 CMD value for a carrot on a stick. You get the number 5 back. You can now use that number in your code and resourcepacks.
     * @param item the item you need CMD for
     * @return the value you can use.
     */
    public int RequestCMDValue(Item item) {
        return RequestCMDValue(item, 1);
    }

    /**
     * Request an amount of CustomModelData values. To prevent CMD values from conflicting.
     * This will also allocate an item, in case we run out of CMD values on one item.
     * Example: you request 5 values. You get the number 2 and "stick" back. You can now use sticks with CMD values 2,3,4,5 and 6 in your code and resourcepack.
     * Items that will be used are in {@link #DEFAULT_ITEMS}
     * @param amount amount of CMD values you'd like to allocate
     * @return the first number you can use and for which item that is.
     * @throws ArithmeticException if there have been a rediculous amount of CMD values allocated
     */
    public Pair<Item,Integer> RequestCMDwithItem(int amount) {
        for (Item item : DEFAULT_ITEMS) {
            try {
                int value = RequestCMDValue(item, amount);
                return new Pair<>(item, value);
            } catch (ArithmeticException ignored) {}
        }
        throw new ArithmeticException("Reached limit off CustomModelData items!");
    }

    /**
     * Request an item with a CustomModelData. To prevent CMD values from conflicting.
     * This will also allocate an item, in case we run out of CMD values on one item
     * Example: you request an item. You get the number 5 and the Glistering water melon slice item back. You can now use glistering water melon slices with CMD of 5 in your code and resourcepack
     * Items that will be used are in {@link #DEFAULT_ITEMS}
     * @return the number you can use and for which item.
     */
    public Pair<Item,Integer> RequestCMDwithItem() {
        return RequestCMDwithItem(1);
    }
}
