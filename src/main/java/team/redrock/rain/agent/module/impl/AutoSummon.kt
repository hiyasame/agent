package team.redrock.rain.agent.module.impl

import baritone.api.BaritoneAPI
import baritone.api.pathing.goals.GoalXZ
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import team.redrock.rain.agent.module.Module
import team.redrock.rain.agent.utils.location.Location
import team.redrock.rain.agent.utils.location.getLocation
import team.redrock.rain.agent.utils.location.setDirection

/**
 * Agent
 * team.redrock.rain.agent.module.impl
 *
 * @author 寒雨
 * @since 2023/2/28 下午1:07
 */
class AutoSummon : Module("AutoSummon") {

    private var nextJob = -1
    private val summonLocations = listOf(
        Location(null, 565.0, 16.0, 8.0),
        Location(null, 563.0, 16.0, 4.0),
        Location(null, 560.0, 16.0, 2.0),
        Location(null, 556.0, 16.0, 1.0)
    )
    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (!enabled) return
        val customGoalProcess = BaritoneAPI.getProvider().primaryBaritone.customGoalProcess

        fun startNavigate() {
            // 只需要靠近即可，不需要踩到召唤方块上面去
            val goal = summonLocations[nextJob].let { loc -> GoalXZ(loc.blockX, loc.blockZ + 1) }
            customGoalProcess.setGoalAndPath(goal)
            println(goal)
        }

        // 第一次
        if (nextJob == -1) {
            nextJob = 0
            startNavigate()
            return
        }

        // 寻路完了 说明在召唤方块的旁边
        if (!customGoalProcess.isActive) {
            val blockLoc = summonLocations[nextJob].clone()
            val playerLoc = mc.player.getLocation()
            playerLoc.y += 1.5
            // 面向这个方块
            mc.player.setDirection(blockLoc.subtract(playerLoc).toVector().normalize())
            // 右键
            KeyBinding.onTick(mc.gameSettings.keyBindUseItem.keyCode)
            // 更新下一个目标
            nextJob = (nextJob + 1) % 4
            startNavigate()
        }
    }

    override fun onDisable() {
        BaritoneAPI.getProvider().primaryBaritone.customGoalProcess.setGoalAndPath(null)
        nextJob = -1
    }
}