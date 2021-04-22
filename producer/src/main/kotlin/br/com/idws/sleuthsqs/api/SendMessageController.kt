package br.com.idws.sleuthsqs.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class SendMessageController(
    val queueMessagingTemplate: QueueMessagingTemplate,
    val mapper: ObjectMapper,
    @Value("\${cloud.aws.sqs.queues.message-queue-1}")
    val messageQueue1Url: String,
) {

    private val logger: Logger = LoggerFactory.getLogger(SendMessageController::class.java)

    @PostMapping("/message")
    fun sendMessage() {
        val messageBody = """{"message": "Body of message"}"""
        queueMessagingTemplate.convertAndSend(messageQueue1Url, messageBody)
        logger.info("Message sent successfully  $messageBody")
    }

}