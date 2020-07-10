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
package io.github.theepicblock.polymc.api.item;

import io.github.theepicblock.polymc.resource.ResourcePackMaker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ItemPoly {

    /**
     * Transforms an ItemStack to it's client version
     * @param input original ItemStack
     * @return ItemStack that should be sent to the client
     */
    ItemStack getClientItem(ItemStack input);

    /**
     * Callback to add all resources needed for this item to a resourcepack
     * @param item item this ItemPoly was registered to, for reference.
     * @param pack resourcepack to add to.
     */
    void AddToResourcePack(Item item, ResourcePackMaker pack);
}
