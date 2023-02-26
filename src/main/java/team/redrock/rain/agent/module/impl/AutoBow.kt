package team.redrock.rain.agent.module.impl

import net.minecraft.client.Minecraft
import net.minecraft.item.ItemBow
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import team.redrock.rain.agent.module.Module

/**
 * Agent
 * team.redrock.rain.agent.module
 *
 * @author 寒雨
 * @since 2023/2/25 下午11:23
 */
class AutoBow : Module() {
    @SubscribeEvent
    fun onTick(e: TickEvent) {
        val player = Minecraft.getMinecraft().player ?: return

        if (// 正在使用物品
            player.isHandActive
            // 手上使用的物品是弓
            && player.activeItemStack.item is ItemBow
            // 蓄满力
            && player.itemInUseMaxCount > 20) {
            // 停止使用物品 即发射
            Minecraft.getMinecraft().playerController.onStoppedUsingItem(player)
        }
    }
}