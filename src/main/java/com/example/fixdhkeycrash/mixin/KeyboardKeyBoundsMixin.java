package com.example.fixdhkeycrash.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(targets = "net.minecraft.class_3675")   // Keyboard 的 intermediary 名
public class KeyboardKeyBoundsMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("FixDhKeyCrash");

    /**
     * 拦截 Keyboard.onKey 中对 GLFW.glfwGetKey 的静态调用，
     * 若 key 越界则直接返回安全值，不调用原生方法。
     */
    @Redirect(
        method = "method_15987",                // onKey
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwGetKey(JI)I"
        )
    )
    private static int safeGlfwGetKey(long window, int key) {  // ← 必须有 static
        if (key < 0 || key > GLFW.GLFW_KEY_LAST) {
            LOGGER.warn("Blocked illegal key, replaced {} with GLFW_KEY_UNKNOWN", key);
            return GLFW.GLFW_KEY_UNKNOWN;
        }
        return GLFW.glfwGetKey(window, key);
    }
}