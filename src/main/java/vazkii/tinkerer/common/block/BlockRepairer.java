/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Nov 30, 2013, 5:36:26 PM (GMT)]
 */
package vazkii.tinkerer.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.compat.TinkersConstructCompat;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRepairer extends BlockModContainer {

	Random random;

	protected BlockRepairer(int par1) {
		super(par1, Material.iron);
        setHardness(5F);
        setResistance(10F);

        random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileRepairer repairer = (TileRepairer) par1World.getBlockTileEntity(par2, par3, par4);
		ItemStack stack = repairer.getStackInSlot(0);

		if(stack == null) {
			ItemStack playerStack = par5EntityPlayer.getCurrentEquippedItem();
			if(repairer.canInsertItem(0, playerStack, 1)) {
				repairer.setInventorySlotContents(0, playerStack.splitStack(1));

				if(playerStack.stackSize <= 0)
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);

				repairer.onInventoryChanged();
				return true;
			}
		} else {
			if(!par5EntityPlayer.inventory.addItemStackToInventory(stack))
				par5EntityPlayer.dropPlayerItem(stack);

			repairer.setInventorySlotContents(0, null);
			repairer.onInventoryChanged();
			return true;
		}

		return false;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		byte b0 = 0;
		int l1 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0)
			b0 = 2;

		if (l1 == 1)
			b0 = 5;

		if (l1 == 2)
			b0 = 3;

		if (l1 == 3)
			b0 = 4;

		par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileRepairer repairer = (TileRepairer) par1World.getBlockTileEntity(par2, par3, par4);

		if (repairer != null) {
			for (int j1 = 0; j1 < repairer.getSizeInventory(); ++j1) {
				ItemStack itemstack = repairer.getStackInSlot(j1);

				if (itemstack != null) {
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
						int k1 = random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
							k1 = itemstack.stackSize;

						itemstack.stackSize -= k1;
						int dmg;
						if(Loader.isModLoaded("TConstruct"))
						{
							if(TinkersConstructCompat.isTConstructTool(itemstack))
							{
								dmg=TinkersConstructCompat.getDamage(itemstack);
							}
							else
								dmg=itemstack.getItemDamage();
						}
						else
							dmg=itemstack.getItemDamage();
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.itemID, k1, dmg));
						float f3 = 0.05F;
						entityitem.motionX = (float)random.nextGaussian() * f3;
						entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float)random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
					}
				}
			}

			par1World.func_96440_m(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return ConfigBlocks.blockCosmeticSolid.getIcon(par1, 4);
	}

	@Override
	public int getRenderType() {
		return LibRenderIDs.idRepairer;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileRepairer();
	}

}
