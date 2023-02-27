package team.redrock.rain.agent.module.impl

import com.google.common.base.Enums
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBow
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.registry.EntityRegistry
import team.redrock.rain.agent.module.Module
import team.redrock.rain.agent.utils.entity.EntityType
import team.redrock.rain.agent.utils.faceBow
import team.redrock.rain.agent.utils.transfer

/**
 * Agent
 * team.redrock.rain.agent.module.impl
 *
 * @author 寒雨
 * @since 2023/2/27 上午12:40
 */
class BowAim : Module("BowAim") {

    private val filter: List<String> by option("filter", "monster,netural")
        .transfer { it.split(",") }
    private var target: EntityLivingBase? = null

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (!enabled) return

        val player = mc.player ?: return

        if (// 正在使用物品
            player.isHandActive
            // 手上使用的物品是弓
            && player.activeItemStack.item is ItemBow
        ) {
            // 没有目标就尝试选取一个目标进行锁定
            if (target == null || target!!.isDead) {
                target = selectTarget(player)
            }
            // 面向这个目标
            if (target != null) {
                player.faceBow(target!!)
            }
        } else {
            target = null
        }
    }

    fun hasTarget(): Boolean {
        return target != null
    }

    private fun selectTarget(player: EntityPlayerSP): EntityLivingBase? {
        return mc.world.loadedEntityList
            // 是生物实体
            .filterIsInstance<EntityLivingBase>()
            // 可见
            .filter { player.canEntityBeSeen(it) }
            .filter { entity ->
                filter.any { type ->
                    Enums.getIfPresent(EntityType::class.java, type.uppercase())
                        .orNull()?.let { it.judge(entity) } == true
                }
            }.minByOrNull { player.getDistance(it) }
    }
}