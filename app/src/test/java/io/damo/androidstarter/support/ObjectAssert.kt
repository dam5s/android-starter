package io.damo.androidstarter.support

import org.assertj.core.api.ObjectAssert

inline fun <reified T> ObjectAssert<*>.hasType(): ObjectAssert<*> =
    isInstanceOf(T::class.java)
