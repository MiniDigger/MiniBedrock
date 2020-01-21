package me.minidigger.minibedrock;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = MiniBedrock.MODID, name = MiniBedrock.NAME, version = MiniBedrock.VERSION)
public class MiniBedrock {

    public static final String MODID = "minibedrock";
    public static final String NAME = "MiniBedrock";
    public static final String VERSION = "1.0";

    private Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Blocks.BEDROCK.setHardness(MiniBedrockConfig.hardness);
        logger.info("Setting hardness to " + Blocks.BEDROCK.getBlockHardness(null, null, null));
    }

    @SubscribeEvent
    public void onBlockbreak(BlockEvent.HarvestDropsEvent event) {
        if (event.getState().getBlock().equals(Blocks.BEDROCK)) {
            event.getDrops().add(new ItemStack(Blocks.BEDROCK));
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
            Blocks.BEDROCK.setHardness(MiniBedrockConfig.hardness);
            logger.info("Setting hardness to " + Blocks.BEDROCK.getBlockHardness(null, null, null));
        }
    }

    @Config(modid = MiniBedrock.MODID, name = MiniBedrock.NAME)
    static class MiniBedrockConfig {

        @Config.Comment("Stone: 1.5, Diamond Ore: 3, Obsidian: 50. [Default: 160]")
        @Config.RangeDouble(min = 0.0)
        @Config.Name("Hardness of Bedrock")
        public static float hardness;

        static {
            hardness = 160f;
        }
    }
}
