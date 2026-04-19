package com.example.fixdhkeycrash.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GLFW.class, remap = false)
public class GLFWKeyboardMixin {  // ← 必须与文件名一致！
    
    @Inject(method = "glfwGetKey", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onGlfwGetKey(long window, int key, CallbackInfoReturnable<Integer> cir) {
        if (key < 0 || key > 349) {
            cir.setReturnValue(0);
        }
    }
}
