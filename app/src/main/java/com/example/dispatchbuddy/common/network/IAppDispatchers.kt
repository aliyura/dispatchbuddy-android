package com.example.dispatchbuddy.common.network

import kotlinx.coroutines.CoroutineDispatcher

interface IAppDispatchers {
    fun io() : CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}

