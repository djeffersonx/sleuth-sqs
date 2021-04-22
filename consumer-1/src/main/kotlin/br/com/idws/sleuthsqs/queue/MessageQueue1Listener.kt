package br.com.idws.sleuthsqs.queue

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class MessageQueue1Listener(
    @Value("\${cloud.aws.sqs.queues.message-queue-1}")
    val messageQueue2Url: String,
    val queueMessagingTemplate: QueueMessagingTemplate,
) {

    private val logger: Logger = LoggerFactory.getLogger(MessageQueue1Listener::class.java)

    @SqsListener("\${cloud.aws.sqs.queues.message-queue-1}")
    fun receiveMessageFromQueue1(message: String) {
        logger.info("Message Received from message-queue-1: $message")
        queueMessagingTemplate.convertAndSend(messageQueue2Url, message)
    }

}