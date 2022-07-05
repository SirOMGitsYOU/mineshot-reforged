package com.siromgitsyou.minecraft.mineshotreforged;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.siromgitsyou.minecraft.mineshotreforged.client.OrthoViewHandler;
import com.siromgitsyou.minecraft.mineshotreforged.client.ScreenshotHandler;
import com.siromgitsyou.minecraft.mineshotreforged.client.config.MyModConfig;

@Mod("mineshotreforged")

public class Mineshot {
	public Mineshot() {
		// Register the clientSetup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

		// Register the configuration
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MyModConfig.CLIENT_SPEC);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		OrthoViewHandler ovh = new OrthoViewHandler();
		MinecraftForge.EVENT_BUS.register(ovh);

		ScreenshotHandler sch = new ScreenshotHandler();
		MinecraftForge.EVENT_BUS.register(sch);
	}
}
