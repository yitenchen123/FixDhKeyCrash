package com.example.fixdhkeycrash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 修复 Minecraft KeyboardHandler.keyPress 方法
 * 1.21.11 混淆名：net.minecraft.class_3675 (KeyboardHandler)
 */
@Mixin(targets = "net.minecraft.class_3675")  // ← 改为字符串引用！
public class ExampleMixin {
    
    @Inject(
        method = "keyPress",  // 或 method_22677 等，需要确认混淆名
        at = @At("HEAD"),
        cancellable = true
    )
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (key < 0 || key > 512) {
            System.out.println("[FixDhKeyCrash] Blocked invalid key in keyPress: " + key);
            ci.cancel();
        }
    }
}
