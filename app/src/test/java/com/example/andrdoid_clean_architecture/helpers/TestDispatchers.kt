package com.example.andrdoid_clean_architecture.helpers

import com.example.andrdoid_clean_architecture.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class TestDispatchers : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val io: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val default: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val unconfined: CoroutineDispatcher
        get() = StandardTestDispatcher()
}