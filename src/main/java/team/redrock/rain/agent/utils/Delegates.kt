package team.redrock.rain.agent.utils

import kotlin.properties.ReadOnlyProperty

/**
 * Agent
 * team.redrock.rain.agent.utils
 *
 * @author 寒雨
 * @since 2023/2/26 下午12:38
 */
fun <T, B, A> ReadOnlyProperty<T, B>.transfer(trans: (B) -> A): ReadOnlyProperty<T, A> {
    return ReadOnlyProperty { thisRef, property -> trans(getValue(thisRef, property)) }
}