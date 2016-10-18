package com.bau5.projectbench.common;

import com.bau5.projectbench.common.item.ItemBlockProjectBench;
import com.bau5.projectbench.common.item.ItemPlan;
import com.bau5.projectbench.common.item.ItemUpgrade;
import com.bau5.projectbench.common.utils.Config;
import com.bau5.projectbench.common.utils.Reference;
import com.bau5.projectbench.common.utils.VersionCheckEventHandler;
import com.bau5.projectbench.common.utils.VersionChecker;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ProjectBench{

    @SidedProxy(clientSide = Reference.PROXY_CLIENT,
                serverSide = Reference.PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.Instance(Reference.MOD_ID)
    public static ProjectBench instance;
    public static SimpleNetworkWrapper network;

    public static Block projectBench;
    public static boolean tryOreDictionary = true;
    public static Item plan;
    public static Item upgrade;

    public Config config;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent ev){
        config = new Config(ev);
        registerItemsAndBlocks(ev);
        registerRecipes();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ProjectBench.instance, proxy);
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL);
        network.registerMessage(SimpleMessage.Handler.class, SimpleMessage.class, 0, Side.SERVER);
        proxy.registerRenderingInformation();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent ev){
        if(ev.getSide() == Side.CLIENT){
            if(Config.VERSION_CHECK) {
                VersionChecker.go();
                MinecraftForge.EVENT_BUS.register(new VersionCheckEventHandler());
            }else{
                FMLLog.info("[Project Bench] Version checking disabled.");
            }
        }
        FMLLog.info("[Project Bench] Initialization complete. Fluids found:");
        for(String name : FluidRegistry.getRegisteredFluids().keySet()){
            FMLLog.info("\t%s", name);
        }
        FMLLog.info("These fluids and their containers will be recognized by the Project Bench.");
    }

    private void registerItemsAndBlocks(FMLPreInitializationEvent ev){
        projectBench = new BlockProjectBench().setCreativeTab(CreativeTabs.DECORATIONS);
        plan = new ItemPlan().setCreativeTab(CreativeTabs.MISC);
        upgrade = new ItemUpgrade().setCreativeTab(CreativeTabs.MISC);
        GameRegistry.registerBlock(projectBench, ItemBlockProjectBench.class, "pb_block");
        GameRegistry.registerTileEntity(TileEntityProjectBench.class, "pb_te");
        GameRegistry.registerItem(plan, "plan");
        GameRegistry.registerItem(upgrade, "pb_upgrade");
    }

    private void registerRecipes(){
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(projectBench, 1, 0),
            " G ", "ICI", "WHW", 'G', "blockGlass", 'I', "ingotIron", 'C', Blocks.CRAFTING_TABLE,
            'W', "plankWood", 'H', Blocks.CHEST
        ));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(plan, 8, 0),
            " PS", "PNP", "SP ", 'P', Items.PAPER, 'S', Items.STICK, 'N', Items.GOLD_NUGGET
        ));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(upgrade, 1, 0),
            " G ", "I I", "WHW", 'G', "blockGlass", 'I', "ingotIron",
            'W', "plankWood", 'H', Blocks.CHEST
        ));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(upgrade, 1, 1),
            "SGS", "GBG", "SGS", 'S', "stone", 'G', "blockGlass", 'B', Items.BUCKET
        ));
        CraftingManager.getInstance().addRecipe(new ShapedOreRecipe(new ItemStack(upgrade, 1, 2),
            " I ", "ICI", " I ", 'I', Items.IRON_INGOT, 'C', Blocks.CHEST
        ));

        if (Config.DEBUG_RECIPE) {
            CraftingManager.getInstance().addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE), new ItemStack(Blocks.COBBLESTONE), new ItemStack(Items.WATER_BUCKET)));
        }
    }
}
