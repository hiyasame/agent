package team.redrock.rain.agent.utils

import net.minecraft.client.Minecraft

/**
 * Agent
 * team.redrock.rain.agent.utils
 * 实现此接口的类自带一个 Minecraft 实例成员
 *
 * @author 寒雨
 * @since 2023/2/25 下午11:27
 */
interface MinecraftInst {
    val mc: Minecraft
        get() = Minecraft.getMinecraft()
}