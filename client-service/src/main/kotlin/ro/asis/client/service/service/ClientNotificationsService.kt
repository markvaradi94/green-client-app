package ro.asis.client.service.service

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import ro.asis.client.events.ClientCreatedEvent
import ro.asis.client.events.ClientDeletedEvent
import ro.asis.client.events.ClientModifiedEvent
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.model.mappers.ClientMapper

@Service
class ClientNotificationsService(
    private val mapper: ClientMapper,
    private val rabbitTemplate: RabbitTemplate,
    private val clientExchange: TopicExchange
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(ClientEntity::class.java)
    }

    fun notifyClientCreated(client: ClientEntity) {
        val event = ClientCreatedEvent(clientId = client.id, accountId = client.accountId)

        LOG.info("Sending event $event")
        rabbitTemplate.convertAndSend(clientExchange.name, "green.clients.new", event)
    }

    fun notifyClientDeleted(client: ClientEntity) {
        val event = ClientDeletedEvent(clientId = client.id)

        LOG.info("Sending event $event")
        rabbitTemplate.convertAndSend(clientExchange.name, "green.clients.delete", event)
    }

    fun notifyClientEdited(client: ClientEntity) {
        val event = ClientModifiedEvent(
            clientId = client.id,
            editedClient = mapper.toApi(client)
        )

        LOG.info("Sending event $event")
        rabbitTemplate.convertAndSend(clientExchange.name, "green.clients.edit", event)
    }
}
