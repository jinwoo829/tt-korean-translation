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
 * File Created @ [8 Sep 2013, 15:48:07 (GMT)]
 */
package vazkii.tinkerer.common.core.handler;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibMisc;
import cpw.mods.fml.common.Loader;

public class ModCreativeTab extends CreativeTabs {

	public static ModCreativeTab INSTANCE = new ModCreativeTab();
	ItemStack displayItem;
	List list;

	public ModCreativeTab() {
		super(LibMisc.MOD_ID);
	}

	@Override
	public ItemStack getIconItemStack() {
		if(displayItem == null)
			addWand();

		return displayItem;
	}

	@Override
	public void displayAllReleventItems(List list) {
		this.list = list;

		addWand();

		addItem(ModItems.darkQuartz);
		addBlock(ModBlocks.darkQuartz);
		addBlock(ModBlocks.darkQuartzStairs);
		addBlock(ModBlocks.darkQuartzSlab);

		addItem(ModItems.connector);
		addBlock(ModBlocks.interfase);
		addBlock(ModBlocks.dislocator);

		addBlock(ModBlocks.funnel);
		addBlock(ModBlocks.platform);
		addBlock(ModBlocks.repairer);
		addBlock(ModBlocks.magnet);
		addItem(ModItems.soulMould);
		addBlock(ModBlocks.animationTablet);

		addItem(ModItems.gaseousLight);
		addItem(ModItems.gaseousShadow);
		addItem(ModItems.gasRemover);

		addItem(ModItems.revealingHelm);
		addItem(ModItems.cleansingTalisman);
		addItem(ModItems.bloodSword);

		addItem(ModItems.focusFlight);
		addItem(ModItems.focusDislocation);
		addItem(ModItems.focusTelekinesis);
		addItem(ModItems.focusDeflect);
		addItem(ModItems.focusSmelt);
		addItem(ModItems.focusHeal);
		addItem(ModItems.focusEnderChest);

		addBlock(ModBlocks.enchanter);
		addItem(ModItems.xpTalisman);
		addItem(ModItems.spellCloth);

		if(Loader.isModLoaded("ComputerCraft"))
			addBlock(ModBlocks.aspectAnalyzer);

		if(ConfigHandler.enableKami) {
			addItem(ModItems.kamiResource);

			addItem(ModItems.ichorHelm);
			addItem(ModItems.ichorHelmGem);
			addItem(ModItems.ichorChest);
			addItem(ModItems.ichorChestGem);
			addItem(ModItems.ichorLegs);
			addItem(ModItems.ichorLegsGem);
			addItem(ModItems.ichorBoots);
			addItem(ModItems.ichorBootsGem);
			
			addItem(ModItems.ichorPick);
			addItem(ModItems.ichorPickGem);
			addItem(ModItems.ichorShovel);
			addItem(ModItems.ichorAxe);
			addItem(ModItems.ichorSword);

			addItem(ModItems.catAmulet);
		}
	}

	private void addWand() {
		ItemStack wand = new ItemStack(ConfigItems.itemWandCasting);
		((ItemWandCasting) wand.getItem()).setRod(wand, ConfigItems.WAND_ROD_SILVERWOOD);
		((ItemWandCasting) wand.getItem()).setCap(wand, ConfigItems.WAND_CAP_THAUMIUM);
		((ItemWandCasting) wand.getItem()).storeAllVis(wand, new AspectList().add(Aspect.AIR, 10000).add(Aspect.EARTH, 10000).add(Aspect.FIRE, 10000).add(Aspect.WATER, 10000).add(Aspect.ORDER, 10000).add(Aspect.ENTROPY, 10000));
		if(list != null)
			list.add(wand);
		displayItem = wand;

		if(ConfigHandler.enableKami) {
			ItemStack wand1 = new ItemStack(ConfigItems.itemWandCasting);
			((ItemWandCasting) wand1.getItem()).setRod(wand1, ModItems.rodIchorcloth);
			((ItemWandCasting) wand1.getItem()).setCap(wand1, ModItems.capIchor);
			((ItemWandCasting) wand1.getItem()).storeAllVis(wand1, new AspectList().add(Aspect.AIR, 100000).add(Aspect.EARTH, 100000).add(Aspect.FIRE, 100000).add(Aspect.WATER, 100000).add(Aspect.ORDER, 100000).add(Aspect.ENTROPY, 100000));
			if(list != null)
				list.add(wand1);
			displayItem = wand1;
		}
	}

	private void addItem(Item item) {
		item.getSubItems(item.itemID, this, list);
	}

	private void addBlock(Block block) {
		block.getSubBlocks(block.blockID, this, list);
	}

}
