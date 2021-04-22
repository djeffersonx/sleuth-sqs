package br.com.idws.sleuthsqs.config

import brave.Span
import brave.Tracing
import brave.instrumentation.aws.sqs.SqsMessageTracing
//import brave.instrumentation.aws.sqs.SqsMessageTracing
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.cloud.sleuth.SpanName
import org.springframework.cloud.sleuth.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
@EnableSqs
class SpringCloudSqsConfig(
    @Value("\${cloud.aws.sqs.endpoint}")
    val serviceEndpoint: String,
    @Value("\${cloud.aws.region.static}")
    val awsRegion: String,
    val tracing: Tracing
) {

    // https://github.com/robsonbittencourt/springboot-aws-sqs-sleuth-poc

    @Bean
    @Primary
    fun queueMessagingTemplate(tracer: Tracer): QueueMessagingTemplate? {

        val sqsMessageTracing: SqsMessageTracing = SqsMessageTracing.create(tracing)

        return object : QueueMessagingTemplate(
            AmazonSQSAsyncClientBuilder
                .standard()
                .withRequestHandlers(sqsMessageTracing.requestHandler())
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(serviceEndpoint, awsRegion))
                .build()
        ) {
            override fun processHeadersToSend(headers: MutableMap<String, Any>?): MutableMap<String, Any>? {
                val result = headers ?: mutableMapOf()
                val span = tracer.currentSpan() ?: tracer.nextSpan()
                span.context()?.spanId()?.let { result.put("spanId", it) }
                span.context()?.traceId()?.let { result.put("traceId", it) }
                return result
            }
        }
    }

}