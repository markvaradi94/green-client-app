package ro.asis.client.service.controller

import com.github.fge.jsonpatch.JsonPatch
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import ro.asis.client.dto.Client
import ro.asis.client.dto.ClientAccount
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.model.mappers.ClientAccountMapper
import ro.asis.client.service.model.mappers.ClientMapper
import ro.asis.client.service.service.ClientService
import ro.asis.commons.exceptions.ResourceNotFoundException
import ro.asis.commons.filters.ClientFilters
import javax.validation.Valid

@RestController
@RequestMapping("clients")
class ClientController(
    private val service: ClientService,
    private val clientMapper: ClientMapper,
    private val clientAccountMapper: ClientAccountMapper
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(ClientEntity::class.java)
    }

    @GetMapping
    fun getAllClients(filters: ClientFilters): List<Client> = clientMapper.toApi(service.findAllClients(filters))

    @GetMapping("{clientId}")
    fun getClient(@PathVariable clientId: String): Client = service.findClient(clientId)
        .map { clientMapper.toApi(it) }
        .orElseThrow { ResourceNotFoundException("Could not find client with id $clientId") }

    @GetMapping("{clientId}/account")
    fun getClientAccount(@PathVariable clientId: String): ClientAccount =
        clientAccountMapper.toApi(service.findClientAccount(clientId))

    @PostMapping
    fun addClient(@Valid @RequestBody client: Client): Client =
        clientMapper.toApi(service.addClient(clientMapper.toEntity(client)))

    @PatchMapping("{clientId}")
    fun patchClient(@PathVariable clientId: String, @RequestBody patch: JsonPatch): Client =
        clientMapper.toApi(service.patchClient(clientId, patch))

    @DeleteMapping("{clientId}")
    fun deleteClient(@PathVariable clientId: String): Client = service.deleteClient(clientId)
        .map { clientMapper.toApi(it) }
        .orElseGet { null }
}
