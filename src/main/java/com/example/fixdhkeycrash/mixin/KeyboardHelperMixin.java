package com.example.fixdhkeycrash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 修复 Minecraft KeyboardHandler.getKey 方法
 * 1.21.11 混淆名：net.minecraft.class_3675 (KeyboardHandler)
 */
@Mixin(targets = "net.minecraft.class_3675")  // ← 使用混淆名字符串！
public class KeyboardHandlerMixin {
    
    /**
     * 拦截 getKey 方法（混淆名：method_15987）
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
