package team.redrock.rain.agent.utils.entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityIronGolem
import net.minecraft.entity.monster.EntityPigZombie
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer

/**
 * Agent
 * team.redrock.rain.agent.utils.entity
 *
 * @author 寒雨
 * @since 2023/2/27 下午11:08
 */
enum class EntityType(val judge: (EntityLivingBase) -> Boolean) {
    MONSTER(judge = { it is IMob }),
    PLAYER(judge = { it is EntityPlayer }),
    ANIMAL(judge = { it is EntityAnimal }),
    NETURAL(judge = { it is EntityIronGolem })
}