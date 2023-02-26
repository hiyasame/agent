package team.redrock.rain.agent

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger
import team.redrock.rain.agent.module.ModuleManager

@Mod(modid = Agent.MOD_ID, name = Agent.MOD_NAME, version = Agent.VERSION)
class Agent {

    init {
        ModuleManager.init()
    }

    companion object {
        const val MOD_ID = "agent"
        const val MOD_NAME = "Agent"
        const val VERSION = "1.0-SNAPSHOT"

        @Mod.Instance(MOD_ID)
        @JvmStatic
        lateinit var INSTANCE: Agent

        lateinit var logger: Logger
    }

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        logger.info("SacredCraft Player Agent 已加载")
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        
    }
}