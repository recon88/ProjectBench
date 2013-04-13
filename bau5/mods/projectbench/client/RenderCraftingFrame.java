package bau5.mods.projectbench.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import bau5.mods.projectbench.common.EntityCraftingFrame;
import bau5.mods.projectbench.common.ProjectBench;

public class RenderCraftingFrame extends RenderItemFrame
{
	private final RenderBlocks renderBlocks = new RenderBlocks();
	private Icon icon;
	
	@Override
	public void updateIcons(IconRegister ir){
		icon = ir.registerIcon("projectbench:craftingFrame_back");
	}

	private void render(EntityCraftingFrame entity, double x, double y,
			double z, float par8, float par9) {
		if(renderBlocks.blockAccess == null)
			renderBlocks.blockAccess = entity.worldObj;
		GL11.glPushMatrix();
        float f2 = (float)(entity.posX - x) - 0.5F;
        float f3 = (float)(entity.posY - y) - 0.5F;
        float f4 = (float)(entity.posZ - z) - 0.5F;
        int i = (int)entity.posX;
        int j = (int)entity.posY;
        int k = (int)entity.posZ -1;
        float f5 = (float)i - f2; 
        float f6 = (float)j - f3;
        float f7 = (float)k - f4;
        GL11.glTranslatef((float)i - f2, (float)j - f3, (float)k - f4);
        renderFrameItemAsBlock(entity);
        renderItemInFrame(entity);
        GL11.glPopMatrix();
	}
	
	private void renderItemInFrame(EntityCraftingFrame entity) {
		ItemStack itemstack = entity.getDisplayedItem();

        if (itemstack != null)
        {
            EntityItem entityitem = new EntityItem(entity.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
            entityitem.getEntityItem().stackSize = 1;
            entityitem.hoverStart = 0.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.453125F * (float)Direction.offsetX[entity.hangingDirection], -0.18F, -0.453125F * (float)Direction.offsetZ[entity.hangingDirection]);
            GL11.glRotatef(180.0F + entity.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float)(-90 * entity.getRotation()), 0.0F, 0.0F, 1.0F);

            switch (entity.getRotation())
            {
                case 1:
                    GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
                    break;
                case 2:
                    GL11.glTranslatef(0.0F, -0.32F, 0.0F);
                    break;
                case 3:
                    GL11.glTranslatef(0.16F, -0.16F, 0.0F);
            }

            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
        }		
	}
	
	private void renderFrameItemAsBlock(EntityItemFrame eif)
    {
        GL11.glPushMatrix();
        this.renderManager.renderEngine.bindTexture("/terrain.png");
        GL11.glRotatef(eif.rotationYaw, 0.0F, 1.0F, 0.0F);
        Block block = Block.planks;
        float f = 0.0625F;
        float f1 = 0.75F;
        float f2 = f1 / 2.0F;
        GL11.glPushMatrix();
        this.renderBlocks.overrideBlockBounds(0.0D, (double)(0.5F - f2 + 0.0625F), (double)(0.5F - f2 + 0.0625F), (double)(f * 0.5F), (double)(0.5F + f2 - 0.0625F), (double)(0.5F + f2 - 0.0625F));
        this.renderBlocks.setOverrideBlockTexture(icon);
        this.renderBlocks.renderBlockAsItem(block, 0, 1.0F);
        this.renderBlocks.clearOverrideBlockTexture();
        this.renderBlocks.unlockBlockBounds();
        GL11.glPopMatrix();
        this.renderBlocks.setOverrideBlockTexture(Block.planks.getBlockTextureFromSideAndMetadata(1, 1));
        GL11.glPushMatrix();
        this.renderBlocks.overrideBlockBounds(0.0D, (double)(0.5F - f2), (double)(0.5F - f2), (double)(f + 1.0E-4F), (double)(f + 0.5F - f2), (double)(0.5F + f2));
        this.renderBlocks.renderBlockAsItem(block, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocks.overrideBlockBounds(0.0D, (double)(0.5F + f2 - f), (double)(0.5F - f2), (double)(f + 1.0E-4F), (double)(0.5F + f2), (double)(0.5F + f2));
        this.renderBlocks.renderBlockAsItem(block, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocks.overrideBlockBounds(0.0D, (double)(0.5F - f2), (double)(0.5F - f2), (double)f, (double)(0.5F + f2), (double)(f + 0.5F - f2));
        this.renderBlocks.renderBlockAsItem(block, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocks.overrideBlockBounds(0.0D, (double)(0.5F - f2), (double)(0.5F + f2 - f), (double)f, (double)(0.5F + f2), (double)(0.5F + f2));
        this.renderBlocks.renderBlockAsItem(block, 0, 1.0F);
        GL11.glPopMatrix();
        this.renderBlocks.unlockBlockBounds();
        this.renderBlocks.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void doRender(Entity entity, double x, double y,
			double z, float par8, float par9) {
		render((EntityCraftingFrame)entity, x, y, z, par8, par9);
	}

}
