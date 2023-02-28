package team.redrock.rain.agent.module

import net.minecraftforge.common.MinecraftForge
import team.redrock.rain.agent.module.impl.AutoBow
import team.redrock.rain.agent.module.impl.AutoSummon
import team.redrock.rain.agent.module.impl.BowAim
import kotlin.reflect.KClass

/**
 * Agent
 * team.redrock.rain.agent.module
 *
 * @author 寒雨
 * @since 2023/2/26 上午11:42
 */
object ModuleManager {
    val modules: HashMap<Class<*>, Module> = hashMapOf()

    fun init() {
        listOf(
            AutoBow(),
            BowAim(),
            AutoSummon()
        ).forEach {
            register(it, it.javaClass)
        }
    }

    private fun register(inst: Module, clazz: Class<*>) {
        modules[clazz] = inst
        // 注册模块中所有事件
        MinecraftForge.EVENT_BUS.register(inst)
    }

}

@Suppress("UNCHECKED_CAST")
val <T: Module> KClass<T>.impl: T
    get() = ModuleManager.modules[this.java] as? T ?: error("the module has not been register")