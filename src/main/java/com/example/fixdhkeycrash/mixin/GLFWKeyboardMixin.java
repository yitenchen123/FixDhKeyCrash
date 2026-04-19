package com.example.fixdhkeycrash.mixin;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)  // ← 改为 KeyboardHandler！
public class ExampleMixin {
    
    @Inject(
        method = "keyPress",  // 处理按键事件
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
