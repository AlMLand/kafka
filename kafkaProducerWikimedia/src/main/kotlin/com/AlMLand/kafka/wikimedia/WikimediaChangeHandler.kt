package com.AlMLand.kafka.wikimedia

import com.launchdarkly.eventsource.EventHandler
import com.launchdarkly.eventsource.MessageEvent
import org.apache.kafka.clients.producer.KafkaProducer
import org.slf4j.LoggerFactory

class WikimediaChangeHandler(private val kafkaProducer: KafkaProducer<String, String>, private val topic: String) :
    EventHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun onClosed() {
        kafkaProducer.close()
    }

    override fun onMessage(event: String?, messageEvent: MessageEvent?) {
        messageEvent?.data.let { data ->
            logger.info("Data: $data")
            data?.let { sendTooLargeRecord(data, kafkaProducer, topic, logger) }
                ?: logger.info("The event data is null")
        }
    }

    override fun onError(t: Throwable?) {
        logger.error("Error in stream reading", t)
    }

    override fun onOpen() {}

    override fun onComment(comment: String?) {}
}
