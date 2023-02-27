package team.redrock.rain.agent.utils

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting

/**
 * Agent
 * team.redrock.rain.agent.utils
 *
 * @author 寒雨
 * @since 2023/2/26 下午11:03
 */
fun EntityPlayerSP.info(message: String) {
    sendMessage(
        TextComponentString(message).setStyle(
            Style().setItalic(true)
                .setColor(TextFormatting.GRAY)
        )
    )
}

fun EntityPlayerSP.warn(message: String) {
    sendMessage(
        TextComponentString(message).setStyle(
            Style().setItalic(true)
                .setColor(TextFormatting.RED)
        )
    )
}