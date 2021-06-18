package ro.asis.client.service.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import ro.asis.client.service.model.mappers.ClientMapper

@Service
class ClientNotificationsService(
    private val mapper: ClientMapper,
    private val rabbitTemplate: RabbitTemplate
) {
}
