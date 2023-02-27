package team.redrock.rain.agent.utils

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.EntityLivingBase
import team.redrock.rain.agent.utils.location.getLocation
import team.redrock.rain.agent.utils.location.setDirection
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Agent
 * team.redrock.rain.agent.utils
 *
 * @author 寒雨
 * @since 2023/2/27 下午2:03
 */
fun EntityPlayerSP.faceBow(entity: EntityLivingBase) {
//    val posX = entity.posX
//    val posY = entity.entityBoundingBox.minY + entity.eyeHeight - 0.15
//    val posZ = entity.posZ
//    val posSqrt = sqrt(posX * posX + posZ * posZ)
//
//    var velocity = itemInUseMaxCount / 20
//    velocity = ((velocity * velocity + velocity * 2) / 3).coerceAtMost(1)
//
//    val rotation = Rotation(
//        (atan2(posZ, posX) * 180 / PI).toFloat() - 90,
//        (-Math.toDegrees(atan((velocity * velocity - sqrt(velocity * velocity * velocity * velocity - 0.006F * (0.006F * (posSqrt * posSqrt) + 2 * posY * (velocity * velocity)))) / (0.006F * posSqrt)))).toFloat()
//    )
//
//    println("entity: $posX $posY $posZ")
//    println("player: ${this.posX} ${this.posY} ${this.posZ}")

//    setPositionAndRotation(this.posX, this.posY, this.posZ, rotation.yaw, rotation.pitch)

//    limitAngleChange(Rotation(pitchYaw.y, pitchYaw.x), rotation, 10 + Random.nextInt(6).toFloat())
//        .toPlayer(this)
    val from = getLocation()
    val to = entity.getLocation()
    val direction = to.clone().subtract(from).toVector()
    setDirection(direction)
}

private fun limitAngleChange(currentRotation: Rotation, targetRotation: Rotation, turnSpeed: Float): Rotation {
    val yawDiff = getAngleDifference(currentRotation.yaw, targetRotation.yaw)
    val pitchDiff = getAngleDifference(currentRotation.pitch, currentRotation.pitch)
    return Rotation(
        currentRotation.yaw + if (yawDiff > turnSpeed) turnSpeed else yawDiff.coerceAtLeast(-yawDiff),
        currentRotation.pitch + if (pitchDiff > turnSpeed) turnSpeed else pitchDiff.coerceAtLeast(-pitchDiff)
    )
}

private fun getAngleDifference(a: Float, b: Float): Float {
    return ((((a - b) % 360F) + 540F) % 360F) - 180F
}

/**
 * Rotations
 */
data class Rotation(var yaw: Float, var pitch: Float) : MinecraftInst {

    /**
     * Set rotations to [player]
     */
    fun toPlayer(player: EntityPlayerSP) {
        if (yaw.isNaN() || pitch.isNaN())
            return

        fixedSensitivity(mc.gameSettings.mouseSensitivity)

        player.rotationYaw = yaw
        player.rotationPitch = pitch
    }

    /**
     * Patch gcd exploit in aim
     *
     * @see net.minecraft.client.renderer.EntityRenderer.updateCameraAndRender
     */
    private fun fixedSensitivity(sensitivity: Float) {
        val f = sensitivity * 0.6F + 0.2F
        val gcd = f * f * f * 1.2F

        yaw -= yaw % gcd
        pitch -= pitch % gcd
    }
}