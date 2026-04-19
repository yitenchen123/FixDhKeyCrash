package com.example.fixdhkeycrash.mixin;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    
    /**
     * 修复 getKey 方法（1.21.11 混淆名：method_15987）
     * DH 调用此方法时传入的 keyCode 可能越界
     */
    @Inject(
        method = "method_15987",  // getKey 的混淆名
        at = @At("HEAD"),
        cancellable = true
    )
    private void onGetKey(int keyCode, int scancode, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode < 0 || keyCode > 512) {
            System.out.println("[FixDhKeyCrash] Blocked invalid keyCode in KeyboardHandler: " + keyCode);
            cir.setReturnValue(false);
        }
    }
}
