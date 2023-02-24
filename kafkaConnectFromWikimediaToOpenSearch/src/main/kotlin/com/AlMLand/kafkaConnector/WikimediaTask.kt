package com.AlMLand.kafkaConnector

import com.launchdarkly.eventsource.MessageEvent
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.errors.ConnectException
import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.source.SourceTask
import java.time.Duration
import java.util.Collections
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit.SECONDS

class WikimediaTask : SourceTask() {
    private var processor: EventProcessor? = null
    private var queue: BlockingQueue<SourceRecord>? = null

    /**
     * will use a static version for now
     */
    override fun version(): String {
        return WikimediaConnector.VERSION
    }

    override fun start(map: Map<String, String>) {
        //get all configs and store it in local variables
        val topic = map[WikimediaConnector.TOPIC_CONFIG]
        val uri = map[WikimediaConnector.URL_CONFIG]
        val reconnectDuration = Duration.ofMillis(
            map[WikimediaConnector.RECONNECT_TIME_CONFIG]!!.toInt().toLong()
        )

        // initiate a new processor
        processor = object : EventProcessor(uri!!) {
            override fun onClosedEvent() {}
            override fun onEvent(event: String?, messageEvent: MessageEvent?) {
                //each event will be added to stash
                queue?.add(
                    SourceRecord(
                        Collections.singletonMap("source", "wikimedia"),
                        Collections.singletonMap("offset", 0),
                        topic,
                        Schema.STRING_SCHEMA,
                        messageEvent!!.data
                    )
                ) ?: error("queue is empty")
            }
        }

        //start the processor
        try {
            queue = LinkedBlockingDeque()
            processor?.start(reconnectDuration)
        } catch (e: Exception) {
            throw ConnectException("unable to start processor", e)
        }
    }

    @Throws(InterruptedException::class)
    override fun poll(): List<SourceRecord> =
        mutableListOf<SourceRecord>().apply {
            queue?.let { blockingQueue ->
                blockingQueue.poll(1L, SECONDS).also { sourceRecord ->
                    if (sourceRecord != null) add(sourceRecord).also {
                        blockingQueue.drainTo(this)
                    }
                }
            } ?: return this
        }

    // stop the processor on shutdown
    override fun stop() {
        try {
            processor?.shutdown()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }
}