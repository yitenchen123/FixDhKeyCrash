package com.example.fixdhkeycrash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.KeyboardHandler;

/**
 * 修复 Minecraft KeyboardHandler.getKey 方法（method_15987）
 */
@Mixin(KeyboardHandler.class)
public class KeyboardHelperMixin {
    
    /**
     * 拦截 getKey 方法
     * 1.21.11 混淆名：method_15987
     */
    @Inject(
        method = "method_15987",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onGetKey(int keyCode, int scancode, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode < 0 || keyCode > 512) {
            System.out.println("[FixDhKeyCrash] Blocked invalid getKey: " + keyCode);
            cir.setReturnValue(false);
        }
    }
}
