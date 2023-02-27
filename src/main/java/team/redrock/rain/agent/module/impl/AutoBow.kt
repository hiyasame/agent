package team.redrock.rain.agent.module.impl

import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraft.item.ItemBow
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import team.redrock.rain.agent.module.Module
import team.redrock.rain.agent.module.impl
import team.redrock.rain.agent.utils.transfer

/**
 * Agent
 * team.redrock.rain.agent.module
 *
 * @author 寒雨
 * @since 2023/2/25 下午11:23
 */
class AutoBow : Module("AutoBow") {

    private val autoDrawBow: Boolean by option("autoDrawBow", "false")
        .transfer { it == "true" }

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (!enabled) return

        val player = Minecraft.getMinecraft().player ?: return
        if (!player.isHandActive
            && player.heldItemMainhand.item is ItemBow
            && autoDrawBow) {
            // 自动拉弓
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.keyCode, true)
        }

        if (// 正在使用物品
            player.isHandActive
            // 手上使用的物品是弓
            && player.activeItemStack.item is ItemBow
            // 蓄满力
            && player.itemInUseMaxCount > 20
        ) {
            val bowAim = BowAim::class.impl
            // 等待 BowAim 模块选取到目标
            if (bowAim.enabled && !bowAim.hasTarget()) return
            // 停止使用物品 即发射
            Minecraft.getMinecraft().playerController.onStoppedUsingItem(player)
        }
    }
}