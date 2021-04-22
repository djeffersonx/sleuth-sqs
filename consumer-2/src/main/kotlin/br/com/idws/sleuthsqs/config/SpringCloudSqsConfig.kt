package br.com.idws.sleuthsqs.config

import brave.Tracing
import brave.instrumentation.aws.sqs.SqsMessageTracing
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableSqs
class SpringCloudSqsConfig(
    @Value("\${cloud.aws.sqs.endpoint}")
    val serviceEndpoint: String,
    @Value("\${cloud.aws.region.static}")
    val awsRegion: String
) {

    // https://github.com/robsonbittencourt/springboot-aws-sqs-sleuth-poc

    @Bean
    fun queueMessagingTemplate(): QueueMessagingTemplate? {

        return QueueMessagingTemplate(
            AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(serviceEndpoint, awsRegion))
                .build()
        )
    }

}