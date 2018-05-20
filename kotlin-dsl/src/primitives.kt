@file:JvmName("KotlinDsl")
@file:JvmMultifileClass

package org.ice1000.jimgui.dsl

import org.ice1000.jimgui.*

operator fun NativeInt.inc() = apply { increaseValue(1) }
operator fun NativeInt.dec() = apply { increaseValue(-1) }
operator fun NativeInt.plusAssign(value: Int) = increaseValue(value)
operator fun NativeInt.minusAssign(value: Int) = increaseValue(-value)

operator fun NativeLong.inc() = apply { increaseValue(1) }
operator fun NativeLong.dec() = apply { increaseValue(-1) }
operator fun NativeLong.plusAssign(value: Long) = increaseValue(value)
operator fun NativeLong.minusAssign(value: Long) = increaseValue(-value)

operator fun NativeDouble.plusAssign(value: Double) = increaseValue(value)
operator fun NativeDouble.minusAssign(value: Double) = increaseValue(-value)

operator fun NativeFloat.plusAssign(value: Float) = increaseValue(value)
operator fun NativeFloat.minusAssign(value: Float) = increaseValue(-value)

operator fun NativeBool.not() = apply { invertValue() }
