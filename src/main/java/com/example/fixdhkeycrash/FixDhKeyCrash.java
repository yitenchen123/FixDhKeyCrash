package com.example.fixdhkeycrash;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixDhKeyCrash implements ModInitializer {
    public static final String MOD_ID = "fixdhkeycrash";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("FixDhKeyCrash initialized - patching GLFW key index bounds");
    }
}