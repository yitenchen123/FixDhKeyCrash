package com.example.fixdhkeycrash.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.seibel.distanthorizons.fabric.FabricClientProxy", remap = false)
public class DistantHorizonsMixin {
    
    @Redirect(
        method = "onKeyInput",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwGetKey(JI)I",
            remap = false
        )
    )
    private static int safeGlfwGetKey(long window, int key) {
        if (key < 0 || key > 349) {
            System.out.println("[FixDhKeyCrash] Blocked invalid key in DH: " + key);
            return GLFW.GLFW_RELEASE;
        }
        return GLFW.glfwGetKey(window, key);
    }
}
