package ro.asis.client.service.listeners

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import ro.asis.client.events.ClientCreationEvent
import ro.asis.client.events.ClientDeletionEvent
import ro.asis.client.events.ClientEditEvent
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.service.ClientAccountService
import ro.asis.commons.enums.EventType.*

@Component
class ClientEventListener(
    private val clientAccountService: ClientAccountService
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(ClientEntity::class.java)
    }

    @RabbitListener(queues = ["#{newClientQueue.name}"])
    fun processNewClientCreation(event: ClientCreationEvent) = callClientAccountCreationForClient(event)

    @RabbitListener(queues = ["#{deleteClientQueue.name}"])
    fun processClientDeletion(event: ClientDeletionEvent) = callClientAccountDeletionForClient(event)

    @RabbitListener(queues = ["#{editClientQueue.name}"])
    fun processClientEdit(event: ClientEditEvent) = callClientAccountEditForClient(event)

    private fun callClientAccountCreationForClient(event: ClientCreationEvent) {
        if (event.eventType == CREATED) {
            LOG.info("Creating client_account for client with id ${event.clientId}")
            clientAccountService.createClientAccountForNewClient(event.clientId, event.accountId)
        }
    }

    private fun callClientAccountDeletionForClient(event: ClientDeletionEvent) {
        if (event.eventType == DELETED) {
            LOG.info("Deleting client_account for client with id ${event.clientId}")
            clientAccountService.deleteForClient(event.clientId)
        }
    }

    private fun callClientAccountEditForClient(event: ClientEditEvent) {
        if (event.eventType == MODIFIED) {
            LOG.info("Client_account was edited for client with id ${event.clientId}")
            LOG.info("$event")
            clientAccountService.editForClientChange(event.clientId, event.editedClient)
        }
    }
}
