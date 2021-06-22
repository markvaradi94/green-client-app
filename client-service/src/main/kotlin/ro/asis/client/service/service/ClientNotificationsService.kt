package ro.asis.client.service.service

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import ro.asis.client.events.ClientCreationEvent
import ro.asis.client.events.ClientDeletionEvent
import ro.asis.client.events.ClientEditEvent
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
        val event = ClientCreationEvent(clientId = client.id!!, accountId = client.accountId)

        LOG.info("Sending event $event")
        rabbitTemplate.convertAndSend(clientExchange.name, "green.clients.new", event)
    }

    fun notifyClientDeleted(client: ClientEntity) {
        val event = ClientDeletionEvent(clientId = client.id!!)

        LOG.info("Sending event $event")
        rabbitTemplate.convertAndSend(clientExchange.name, "green.clients.delete", event)
    }

    fun notifyClientEdited(client: ClientEntity) {
        val event = ClientEditEvent(
            clientId = client.id!!,
            editedClient = mapper.toApi(client)
        )

        LOG.info("Sending event $event")
        rabbitTemplate.convertAndSend(clientExchange.name, "green.clients.edit", event)
    }
}
