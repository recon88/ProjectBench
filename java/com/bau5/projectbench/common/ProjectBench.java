package com.bau5.projectbench.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = ProjectBench.MOD_ID, name = ProjectBench.NAME, version = ProjectBench.VERSION)
public class ProjectBench {
    public final static String MOD_ID = "projectbench";
    public final static String VERSION = "0.4.1";
    public final static String NAME = "Project Bench";

    @SidedProxy(clientSide = "com.bau5.projectbench.client.ClientProxy",
                serverSide = "com.bau5.projectbench.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(ProjectBench.MOD_ID)
    public static ProjectBench instance;
    public static SimpleNetworkWrapper network;

    public static Block projectBench;
    public static boolean tryOreDictionary = true;
    public static Item plan;
    public static Item upgrade;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent ev){

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        projectBench = new BlockProjectBench().setCreativeTab(CreativeTabs.tabDecorations);
        plan = new ItemPlan().setCreativeTab(CreativeTabs.tabMisc);
        upgrade = new ItemUpgrade().setCreativeTab(CreativeTabs.tabMisc);

        GameRegistry.registerBlock(projectBench, ItemBlockProjectBench.class, "pb_block");
        GameRegistry.registerTileEntity(TileEntityProjectBench.class, "pb_te");
        GameRegistry.registerItem(plan, "plan");
        GameRegistry.registerItem(upgrade, "pb_upgrade");

        NetworkRegistry.INSTANCE.registerGuiHandler(ProjectBench.instance, proxy);
        network = NetworkRegistry.INSTANCE.newSimpleChannel("ProjectBench");
        network.registerMessage(SimpleMessage.Handler.class, SimpleMessage.class, 0, Side.SERVER);

        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(projectBench, 1, 0), new Object[]{
                " G ", "ICI", "WHW", 'G', "blockGlass", 'I', "ingotIron", 'C', Blocks.crafting_table,
                'W', "plankWood", 'H', Blocks.chest
        }));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(plan, 8, 0), new Object[]{
                " PS", "PNP", "SP ", 'P', Items.paper, 'S', Items.stick, 'N', Items.gold_nugget
        }));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(upgrade, 1, 0), new Object[]{
                " G ", "I I", "WHW", 'G', "blockGlass", 'I', "ingotIron",
                                     'W', "plankWood", 'H', Blocks.chest
        }));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(upgrade, 1, 1), new Object[]{
                "SGS", "GBG", "SGS", 'S', "stone", 'G', "blockGlass", 'B', Items.bucket
        }));
        /*CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.dirt), new Object[]{
                "PGB", 'P', "plankWood", 'G', "blockGlass", 'B', new ItemStack(Blocks.planks, 1, 1)
        }));
        CraftingManager.getInstance().addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.cobblestone), "blockGlass", "plankWood", new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.glass, 1, 0)));
        CraftingManager.getInstance().addRecipe(new ShapelessOreRecipe(
                new ItemStack(Blocks.mossy_cobblestone, 1, 0),
                new ItemStack(Blocks.cobblestone, 1, 0),
                new ItemStack(Items.water_bucket, 1, 0)

        ));*/

        proxy.registerRenderingInformation();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent ev){
        FMLLog.info("Project Bench initialization complete. Fluids found:");
        for(String name : FluidRegistry.getRegisteredFluids().keySet()){
            FMLLog.info("\tPB: %s", name);
        }
        FMLLog.info("These fluids and their containers will be recognized by the Project Bench.");
    }
}
