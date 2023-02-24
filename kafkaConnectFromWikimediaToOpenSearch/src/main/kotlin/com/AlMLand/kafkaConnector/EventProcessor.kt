package com.AlMLand.kafkaConnector

import com.launchdarkly.eventsource.EventHandler
import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.MessageEvent
import java.net.URI
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class EventProcessor(
    private val uri: String,
    private var count: Long = 0L,
    private var eventSource: EventSource? = null,
    var promise: CompletableFuture<Long>? = null
) {

    /**
     * override this method to do on close callback
     */
    protected abstract fun onClosedEvent()

    /**
     * override this method to handle each message
     */
    protected abstract fun onEvent(event: String?, messageEvent: MessageEvent?)

    /**
     * shuts down the processor
     * @throws Exception throw if the processor hasn't started
     */
    @Throws(RuntimeException::class)
    open fun shutdown() = eventSource?.close() ?: run {
        promise?.complete(count)
        throw RuntimeException("trying to stop EventProcessor without starting")
    }

    /**
     * start the processor
     * @param reconnectTime reconnect if processor in case of error for this duration
     * @throws RuntimeException throws if already started
     * @return a promise which the caller can wait on ; this promise will complete once the processor is shutdown
     */
    @Throws(RuntimeException::class)
    open fun start(reconnectTime: Duration?): CompletableFuture<Long> =
        (reconnectTime ?: Duration.ofMillis(3000)).let {
            if (eventSource != null) throw RuntimeException("trying to start already started EventProcessor")
            EventSource.Builder(CustomEventHandler(), URI.create(uri))
                .reconnectTime(reconnectTime!!.toMillis(), MILLISECONDS).build().also {
                    promise = CompletableFuture()
                    it.start()
                }
            promise!!
        }

    /**
     * a custom event handler that delegates to processor methods
     */
    inner class CustomEventHandler : EventHandler {
        override fun onOpen() {}

        override fun onComment(comment: String?) {}

        override fun onClosed() = onClosedEvent().also { promise!!.complete(count) }

        override fun onError(t: Throwable) = println("onError").also { t.printStackTrace() }

        override fun onMessage(event: String?, messageEvent: MessageEvent?) {
            count++
            onEvent(event, messageEvent)
        }
    }
}