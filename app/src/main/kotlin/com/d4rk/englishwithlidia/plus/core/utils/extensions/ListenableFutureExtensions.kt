package com.d4rk.englishwithlidia.plus.core.utils.extensions

import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> ListenableFuture<T>.await(): T = suspendCancellableCoroutine { cont ->
    this.addListener({
        try {
            cont.resume(this.get())
        } catch (e: Exception) {
            cont.resumeWithException(e)
        }
    }, MoreExecutors.directExecutor())
    cont.invokeOnCancellation { this.cancel(true) }
}
