package ro.asis.client.service.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ro.asis.client.service.model.entity.ClientAccountEntity
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.repository.ClientDao
import ro.asis.client.service.repository.ClientRepository
import ro.asis.client.service.service.validator.ClientValidator
import ro.asis.commons.exceptions.ResourceNotFoundException
import ro.asis.commons.filters.ClientFilters
import java.util.*

@Service
class ClientService(
    private val dao: ClientDao,
    private val mapper: ObjectMapper,
    private val repository: ClientRepository,
    private val validator: ClientValidator,
    private val clientAccountService: ClientAccountService
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(ClientEntity::class.java)
    }

    fun findAllClients(filters: ClientFilters): List<ClientEntity> = dao.findClients(filters)

    fun findClient(clientId: String): Optional<ClientEntity> = repository.findById(clientId)

    fun findClientAccount(clientId: String): ClientAccountEntity {
        val client = getOrThrow(clientId)
        return clientAccountService.findClientAccountByClient(client)
    }

    fun addClient(client: ClientEntity): ClientEntity {
        validator.validateNewOrThrow(client)
        return repository.save(client)
    }

    fun deleteClient(clientId: String): Optional<ClientEntity> {
        validator.validateExistsOrThrow(clientId)
        val clientToDelete = repository.findById(clientId)
        clientToDelete.ifPresent { deleteExistingClient(it) }
        return clientToDelete
    }

    fun patchClient(clientId: String, patch: JsonPatch): ClientEntity {
        validator.validateExistsOrThrow(clientId)

        val dbClient = getOrThrow(clientId)
        val patchedClientJson = patch.apply(mapper.valueToTree(dbClient))
        val patchedClient = mapper.treeToValue(patchedClientJson, ClientEntity::class.java)

        validator.validateReplaceOrThrow(clientId, patchedClient)

        copyClient(patchedClient, dbClient)
        return repository.save(dbClient)
    }

    private fun copyClient(newClient: ClientEntity, dbClient: ClientEntity) {
        LOG.info("Copying client: $newClient")
        dbClient.firstName = newClient.firstName
        dbClient.lastName = newClient.lastName
        dbClient.address = newClient.address
        dbClient.cart = newClient.cart
    }

    private fun deleteExistingClient(client: ClientEntity) {
        LOG.info("Deleting client: $client")
        repository.delete(client)
    }

    private fun getOrThrow(clientId: String): ClientEntity = repository
        .findById(clientId)
        .orElseThrow { ResourceNotFoundException("Could not find client with id $clientId") }
}

