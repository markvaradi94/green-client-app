package ro.asis.client.service.service

import org.springframework.stereotype.Service
import ro.asis.account.client.AccountApiClient
import ro.asis.account.dto.Account
import ro.asis.client.client.ClientApiClient
import ro.asis.client.dto.Client
import ro.asis.client.service.model.entity.ClientAccountEntity
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.repository.ClientAccountRepository
import ro.asis.commons.exceptions.ResourceNotFoundException

@Service
class ClientAccountService(
    private val accountApiClient: AccountApiClient,
    private val clientApiClient: ClientApiClient,
    private val repository: ClientAccountRepository
) {
    fun createClientAccount(client: ClientEntity): ClientAccountEntity {
        val account = accountApiClient.getAccount(client.accountId)
            .orElseThrow { ResourceNotFoundException("Could not find account with id ${client.accountId}") }
        return repository.save(
            ClientAccountEntity(
                clientId = client.id,
                accountId = account.id,
                email = account.email,
                phoneNumber = account.phoneNumber
            )
        )
    }

    fun createClientAccountForNewClient(clientId: String, accountId: String): ClientAccountEntity {
        val account = accountApiClient.getAccount(accountId)
            .orElseThrow { ResourceNotFoundException("Could not find account with id $accountId") }
        val client = clientApiClient.getClient(clientId)
            .orElseThrow { ResourceNotFoundException("Could not find client with id $clientId") }

        val newClientAccount = ClientAccountEntity(
            clientId = client.id,
            accountId = account.id,
            email = account.email,
            phoneNumber = account.phoneNumber
        )

        return repository.save(newClientAccount)
    }

    fun editForAccountChange(accountId: String, editedAccount: Account): ClientAccountEntity {
        val clientAccount = findClientAccountByAccountId(accountId)
        clientAccount.email = editedAccount.email
        clientAccount.phoneNumber = editedAccount.phoneNumber
        return repository.save(clientAccount)
    }

    fun editForClientChange(clientId: String, editedClient: Client): ClientAccountEntity {
        val clientAccount = findClientAccountByClientId(clientId)
        editedClient.firstName
        editedClient.lastName
        //TODO rethink if we need to add any specific client details to client_account
        return clientAccount
    }

    fun deleteForClient(clientId: String): ClientAccountEntity {
        val clientAccountToDelete = findClientAccountByClientId(clientId)
        repository.delete(clientAccountToDelete)
        return clientAccountToDelete
    }

    fun findClientAccountByClientId(clientId: String): ClientAccountEntity =
        repository.findByClientId(clientId)
            .orElseThrow { ResourceNotFoundException("Could not find client account for client with id $clientId") }

    fun findClientAccountByAccountId(accountId: String): ClientAccountEntity =
        repository.findByAccountId(accountId)
            .orElseThrow { ResourceNotFoundException("Could not find client account for account with id $accountId") }

    fun findClientAccountByClient(client: ClientEntity): ClientAccountEntity =
        repository.findByClientId(client.id)
            .orElseThrow { ResourceNotFoundException("Could not find client account for client with id ${client.id}") }

    fun findClientForClientAccount(clientAccount: ClientAccountEntity): Client =
        clientApiClient.getClient(clientAccount.clientId)
            .orElseThrow { ResourceNotFoundException("Could not find client with id ${clientAccount.clientId}") }

    fun findAccountForClientAccount(clientAccount: ClientAccountEntity): Account =
        accountApiClient.getAccount(clientAccount.accountId)
            .orElseThrow { ResourceNotFoundException("Could not find account with id ${clientAccount.accountId}") }

    fun findClientAccountByClientIdAndAccountId(clientId: String, accountId: String): ClientAccountEntity =
        repository.findByAccountIdAndClientId(accountId, clientId)
            .orElseThrow {
                ResourceNotFoundException(
                    "Could not find client account for client with id $clientId " +
                            "and account with id $accountId"
                )
            }
}
