package com.example.fixdhkeycrash.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GLFW.class)
public class GLFWKeyboardMixin {

    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onGlfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        // GLFW 键码有效范围通常是 0..349 (GLFW_KEY_LAST = 349)
        if (key < 0 || key > 349) {
            // 返回 GLFW_RELEASE (0) 避免越界访问
            cir.setReturnValue(0);
        }
    }
}