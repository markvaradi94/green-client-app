package ro.asis.client.service.listeners

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import ro.asis.account.events.AccountCreatedEvent
import ro.asis.account.events.AccountDeletedEvent
import ro.asis.account.events.AccountModifiedEvent
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.service.ClientAccountService
import ro.asis.client.service.service.ClientService
import ro.asis.commons.enums.AccountType.CLIENT
import ro.asis.commons.enums.EventType.*

@Component
class AccountEventListener(
    private val clientService: ClientService,
    private val clientAccountService: ClientAccountService
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(ClientEntity::class.java)
    }

    @RabbitListener(queues = ["#{newAccountQueue.name}"])
    fun processNewAccountCreation(event: AccountCreatedEvent) = callClientCreationForAccount(event)

    @RabbitListener(queues = ["#{deleteAccountQueue.name}"])
    fun processAccountDeletion(event: AccountDeletedEvent) = callClientDeletionForAccount(event)

    @RabbitListener(queues = ["#{editAccountQueue.name}"])
    fun processAccountEdit(event: AccountModifiedEvent) = callClientEditForAccount(event)

    private fun callClientCreationForAccount(event: AccountCreatedEvent) {
        if (event.accountType == CLIENT && event.eventType == CREATE) {
            LOG.info("Creating client for account with id ${event.accountId}")
            clientService.createClientForNewAccount(event.accountId)
        }
    }

    private fun callClientDeletionForAccount(event: AccountDeletedEvent) {
        if (event.accountType == CLIENT && event.eventType == DELETE) {
            LOG.info("Deleting client for account with id ${event.accountId}")
            clientService.deleteClientForAccount(event.accountId)
        }
    }

    private fun callClientEditForAccount(event: AccountModifiedEvent) {
        if (event.accountType == CLIENT && event.eventType == MODIFY) {
            LOG.info("Client account was edited for account with id ${event.accountId}")
            LOG.info("$event")
            clientAccountService.editForAccountChange(event.accountId, event.editedAccount)
        }
    }
}
