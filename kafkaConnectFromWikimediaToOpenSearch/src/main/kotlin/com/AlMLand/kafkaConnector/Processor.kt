package com.AlMLand.kafkaConnector

import com.launchdarkly.eventsource.MessageEvent
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.Properties
import java.util.concurrent.CompletableFuture

/**
 * Custom processor that produces to kafka
 */
class Processor(
    uri: String,
    private val topic: String,
    private val producerProps: Properties,
    private val closeTimeout: Duration,
    private var kafkaProducer: KafkaProducer<String, String>? = null,
    private var isRunning: Boolean = false
) : EventProcessor(uri) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * handle each message
     */
    override fun onEvent(event: String?, messageEvent: MessageEvent?) {
        kafkaProducer?.let { producer ->
            messageEvent?.let {
                producer.send(ProducerRecord(topic, messageEvent.data)).run {
                    logger.info(
                        """
                        received record: ${messageEvent.data} to topic: ${get().topic()}, partition: ${get().partition()}, offset: ${get().offset()}
                    """.trimIndent()
                    )
                }
            } ?: error("the record to receive is not present")
        } ?: error("kafka producer is not present")
    }

    /**
     * start the processor
     * @param reconnectTime reconnect if processor in case of error for this duration
     * @throws RuntimeException throws if already started
     * @return  a promise which the caller can wait on ; this promise will complete once the processor is shutdown
     */
    @Throws(RuntimeException::class)
    override fun start(reconnectTime: Duration?): CompletableFuture<Long> {
        if (kafkaProducer != null) throw RuntimeException("trying to start already started processor")
        kafkaProducer = KafkaProducer<String, String>(producerProps)
        return super.start(reconnectTime).also {
            isRunning = true
        }
    }

    /**
     * shuts down the processor
     * @throws RuntimeException throw if the processor hasn't started
     */
    @Throws(RuntimeException::class)
    override fun shutdown() {
        super.shutdown()
    }

    /**
     * on close callback
     */
    override fun onClosedEvent() {
        kafkaProducer?.let {
            it.flush()
            it.close(closeTimeout)
            isRunning = false
        }
    }
}