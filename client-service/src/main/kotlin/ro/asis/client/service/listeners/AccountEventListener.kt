package ro.asis.client.service.listeners

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import ro.asis.account.events.AccountCreationEvent
import ro.asis.account.events.AccountDeletionEvent
import ro.asis.account.events.AccountEditEvent
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
    fun processNewAccountCreation(event: AccountCreationEvent) = callClientCreationForAccount(event)

    @RabbitListener(queues = ["#{deleteAccountQueue.name}"])
    fun processAccountDeletion(event: AccountDeletionEvent) = callClientDeletionForAccount(event)

    @RabbitListener(queues = ["#{editAccountQueue.name}"])
    fun processAccountEdit(event: AccountEditEvent) = callClientEditForAccount(event)

    private fun callClientCreationForAccount(event: AccountCreationEvent) {
        if (event.accountType == CLIENT && event.eventType == CREATED) {
            LOG.info("Creating client for account with id ${event.accountId}")
            clientService.createClientForNewAccount(event.accountId)
        }
    }

    private fun callClientDeletionForAccount(event: AccountDeletionEvent) {
        if (event.accountType == CLIENT && event.eventType == DELETED) {
            LOG.info("Deleting client for account with id ${event.accountId}")
            clientService.deleteClientForAccount(event.accountId)
        }
    }

    private fun callClientEditForAccount(event: AccountEditEvent) {
        if (event.accountType == CLIENT && event.eventType == MODIFIED) {
            LOG.info("Client account was edited for account with id ${event.accountId}")
            LOG.info("$event")
            clientAccountService.editForAccountChange(event.accountId, event.editedAccount)
        }
    }
}
