package com.example.fixdhkeycrash.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(value = GLFW.class, remap = false)
public class GLFWKeyboardMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("FixDhKeyCrash");

    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onGlfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        // 使用 GLFW 常量，更精确，也更易于适配不同版本
        if (key < 0 || key > GLFW.GLFW_KEY_LAST) {
            LOGGER.warn("Blocked illegal key input: {} (window: {}). Returning GLFW_KEY_UNKNOWN.", key, window);
            cir.setReturnValue(GLFW.GLFW_KEY_UNKNOWN);  // 直接返回安全值
        }
    }
}