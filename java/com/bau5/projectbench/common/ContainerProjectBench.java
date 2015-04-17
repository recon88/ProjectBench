package com.bau5.projectbench.common;

import com.bau5.projectbench.client.TileEntityProjectBench;
import com.bau5.projectbench.common.inventory.SlotModifiedCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

/**
 * Created by Rick on 4/15/2015.
 */
public class ContainerProjectBench extends Container {

    private TileEntityProjectBench tile;

    public ContainerProjectBench(InventoryPlayer invPlayer, TileEntityProjectBench tpb){
        this.tile = tpb;
        bindPlayerInventory(invPlayer);
        int i;
        int j;
        int index = 0;
        addSlotToContainer(new SlotModifiedCrafting(tile.getCraftingItemsProvider(),
                            invPlayer.player, tile, tile.getCraftResult(), 0, 124, 35));
        for(i = 0; i < 3; i++){
            for(j = 0; j < 3; j++){
                addSlotToContainer(new Slot(tile, index++, 30 + j * 18, 17 + i * 18));
            }
        }
        for(i = 0; i < 2; i++){
            for(j = 0; j < 9; j++){
                if(i == 1){
                    addSlotToContainer(new Slot(tile, 18 + j, 8 + j * 18, (i * 2 -1) + 77 + i * 18));
                }else{
                    addSlotToContainer(new Slot(tile, 9 + j, 8 + j * 18, 77 + i * 18));
                }
            }
        }
    }


    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
        if(slotId == 36 && mode == 6)
            mode = 0;
        return super.slotClick(slotId, clickedButton, mode, playerIn);
    }

    private void bindPlayerInventory(InventoryPlayer invPlayer) {
        int i, j;
        for(i = 0; i < 3; i++){
            for(j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(invPlayer, j + i*9 + 9, 8 + j * 18, 121+ i * 18));
            }
        }
        for(i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i*18, 179));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()){
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if(index >= 46 && index <= 63){
                if(!this.mergeItemStack(stack, 0, 36, false))
                    return null;
            }else if(index >= 36 && index <= 45){
                if(!this.mergeItemStack(stack, 46, 64, false)) {
                    if (!this.mergeItemStack(stack, 0, 36, false)) {
                        return null;
                    }
                }
            }else if (index >= 0 && index <= 35){
               if(!this.mergeItemStack(stack, 46, 64, false))
                   return null;
            }

            if(stack.stackSize == 0) {
                slot.putStack(null);
            }else{
                slot.onSlotChanged();
            }
            if (stack.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, stack);
        }


        return itemstack;
    }
}
