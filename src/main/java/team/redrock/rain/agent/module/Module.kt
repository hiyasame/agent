package team.redrock.rain.agent.module

import net.minecraftforge.client.event.ClientChatEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import team.redrock.rain.agent.utils.MinecraftInst
import team.redrock.rain.agent.utils.info
import team.redrock.rain.agent.utils.transfer
import team.redrock.rain.agent.utils.warn
import kotlin.properties.ReadOnlyProperty

/**
 * Agent
 * team.redrock.rain.agent.module
 *
 * @author 寒雨
 * @since 2023/2/26 上午11:42
 */
abstract class Module(private val name: String) : MinecraftInst {

    private val options = hashMapOf<String, String>()

    /**
     * 只能在类字段中使用，因为会注册默认值
     */
    protected fun option(name: String, def: String) : ReadOnlyProperty<Any?, String> {
        options[name] = def
        return ReadOnlyProperty { _, _ ->
            // 这里不可能为null了
            options[name]!!
        }
    }

    val enabled: Boolean by option("enable", "false")
        .transfer { it == "true" }

    open fun onEnable() { }

    open fun onDisable() { }

    @SubscribeEvent
    fun onCommand(e: ClientChatEvent) {
        if (e.message.startsWith(".$name ", ignoreCase = true)) {
            e.isCanceled = true
            val args = e.message.removePrefix(".$name ")
                .removePrefix(".${name.lowercase()} ")
                .split(" ")
            if (args.size != 2) {
                mc.player.warn("参数错误")
                return
            }
            options[args[0]] ?: return let {
                mc.player.warn("未知的配置项")
            }
            options[args[0]] = args[1]
            mc.player.info("配置项 ${args[0]} 的值已更新为 ${args[1]}")
            if (args[0] == "enable") {
                if (args[1] == "true") onEnable() else onDisable()
            }
        }
    }
}