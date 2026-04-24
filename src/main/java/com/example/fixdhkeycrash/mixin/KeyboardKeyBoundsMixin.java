package com.example.fixdhkeycrash.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(targets = "net.minecraft.class_3675") // 直接指定 Keyboard 的 intermediary 名
public class KeyboardKeyBoundsMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("FixDhKeyCrash");

    /**
     * 拦截 Keyboard.onKey (method_15987) 中对 GLFW.glfwGetKey 的调用，
     * 若 key 越界则直接返回安全值，不执行原生调用。
     */
    @Redirect(
        method = "method_15987",                           // onKey 的 intermediary 名
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwGetKey(JI)I"
        )
    )
    private int safeGlfwGetKey(long window, int key) {
        if (key < 0 || key > GLFW.GLFW_KEY_LAST) {
            LOGGER.warn("Blocked illegal key, replaced {} with GLFW_KEY_UNKNOWN", key);
            return GLFW.GLFW_KEY_UNKNOWN;                 // -1，直接返回，不调用原生 GLFW
        }
        return GLFW.glfwGetKey(window, key);              // 合法则正常调用
    }
}