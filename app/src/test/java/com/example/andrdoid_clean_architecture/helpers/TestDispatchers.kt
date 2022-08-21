package com.example.andrdoid_clean_architecture.helpers

import com.example.andrdoid_clean_architecture.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatchers : DispatcherProvider {
    val testDispatchers = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatchers
    override val io: CoroutineDispatcher
        get() = testDispatchers
    override val default: CoroutineDispatcher
        get() = testDispatchers
    override val unconfined: CoroutineDispatcher
        get() = testDispatchers
}