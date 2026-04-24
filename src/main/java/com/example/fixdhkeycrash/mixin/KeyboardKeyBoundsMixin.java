package com.example.fixdhkeycrash.mixin;

import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(Keyboard.class)
public class KeyboardKeyBoundsMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("FixDhKeyCrash");

    /**
     * 拦截 Keyboard.onKey 中对 GLFW.glfwGetKey 的调用，
     * 将越界的 key 参数替换为安全的 GLFW_KEY_UNKNOWN (-1)。
     * 使用 @ModifyArg 不关心方法名是否映射正确，只要找到 INVOKE 目标即可。
     */
    @ModifyArg(
        method = "method_15987",              // Keyboard.onKey 的 intermediary 名
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwGetKey(JI)I"
        ),
        index = 1                            // key 参数在 glfwGetKey(long, int) 中的索引
    )
    private int safeKey(int originalKey) {
        if (originalKey < 0 || originalKey > GLFW.GLFW_KEY_LAST) {
            LOGGER.warn("Blocked illegal key, replaced {} with GLFW_KEY_UNKNOWN", originalKey);
            return GLFW.GLFW_KEY_UNKNOWN;    // 安全值
        }
        return originalKey;
    }
}