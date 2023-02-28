package team.redrock.rain.agent.utils.location

import net.minecraft.entity.EntityLivingBase

/**
 * Agent
 * team.redrock.rain.agent.utils.location
 *
 * @author 寒雨
 * @since 2023/2/27 下午7:19
 */
fun EntityLivingBase.getLocation(): Location {
    return Location(null, posX, posY, posZ, pitchYaw.y, pitchYaw.x)
}

fun EntityLivingBase.setDirection(direction: Vector) {
    val loc = getLocation().clone()
    loc.direction = direction.normalize()
    setPositionAndRotation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)
}