package com.example.fixdhkeycrash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.class_3675")
public class KeyboardHelperMixin {  // ← 必须与文件名一致！
    
    @Inject(
        method = "method_15987",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onGetKey(int keyCode, int scancode, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode < 0 || keyCode > 512) {
            System.out.println("[FixDhKeyCrash] Blocked invalid keyCode: " + keyCode);
            cir.setReturnValue(false);
        }
    }
}
