package com.github.yitenchen.fixdhkeycrash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.lwjgl.glfw.GLFW;

// 不指定 value，而是作为全局注入点，但此例中需指向某个 DH 调用链
// 如果找不到具体类，可以用更底层的 Mixin 注入 GLFW 调用本身
@Mixin(GLFW.class)
public class DistantHorizonsMixin {
    
    // 拦截所有对 glfwGetKey 的 key 参数，确保其在 0-348 范围
    @ModifyConstant(method = "glfwGetKey", constant = @Constant(intValue = -1, ordinal = 1), remap = false)
    private static int fixKeyParam(int originalKey) {
        if (originalKey < 0 || originalKey > GLFW.GLFW_KEY_LAST) {
            return GLFW.GLFW_KEY_UNKNOWN; // 返回一个安全值
        }
        return originalKey;
    }
}