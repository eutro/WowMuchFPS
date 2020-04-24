package eutros.wowmuchfps;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("wowmuchfps")
public class WowMuchFPS {

    public WowMuchFPS() {
        Pair<Object, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure((builder) -> {
            builder.comment("Simply write in here how much you wish to multiply your FPS by!");
            multiplier = builder.define("FPS Multiplier", 2.0);

            return null;
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, specPair.getRight());
    }

    private static ForgeConfigSpec.ConfigValue<Number> multiplier;

    @SuppressWarnings("unused")
    public static int increaseFPS(int fps) {
        return (int) (fps * multiplier.get().doubleValue());
    }

}
