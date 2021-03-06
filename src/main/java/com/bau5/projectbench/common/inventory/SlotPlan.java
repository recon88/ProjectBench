package com.bau5.projectbench.common.inventory;

import com.bau5.projectbench.common.ProjectBench;
import com.bau5.projectbench.common.TileEntityProjectBench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by bau5 on 4/17/2015.
 */
public class SlotPlan extends Slot {
    private TileEntityProjectBench theTile;

    public SlotPlan(TileEntityProjectBench tile, int id, int x, int y) {
        super(tile, id, x, y);
        theTile = tile;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public ItemStack getStack() {
        return theTile.getStackInSlot(theTile.getSizeInventory() -1);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem().equals(ProjectBench.plan);
    }
}
