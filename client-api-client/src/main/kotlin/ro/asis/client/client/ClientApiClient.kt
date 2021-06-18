package ro.asis.client.client

import com.github.fge.jsonpatch.JsonPatch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpEntity.EMPTY
import org.springframework.http.HttpMethod.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder
import ro.asis.client.dto.Client
import ro.asis.client.dto.ClientAccount
import ro.asis.commons.filters.ClientFilters
import java.util.*
import java.util.Optional.ofNullable

@Component
class ClientApiClient(
    @Value("\${client-service-location:NOT_DEFINED}")
    private val baseUrl: String,
    private val restTemplate: RestTemplate
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(Client::class.java)
    }

    fun getAllClients(filters: ClientFilters): List<Client> {
        val url = buildQueriedUrl(filters)
        return restTemplate.exchange(
            url,
            GET,
            EMPTY,
            object : ParameterizedTypeReference<List<Client>>() {}
        ).body ?: listOf()
    }

    fun getClient(clientId: String): Optional<Client> {
        val url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/clients/$clientId")
            .toUriString()

        return ofNullable(restTemplate.getForObject(url, Client::class.java))
    }

    fun getClientAccount(clientId: String): Optional<ClientAccount> {
        val url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/clients/$clientId/account")
            .toUriString()

        return ofNullable(restTemplate.getForObject(url, ClientAccount::class.java))
    }

    fun addClient(client: Client): Client {
        val url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/clients")
            .toUriString()
        return restTemplate.postForObject(url, client, Client::class)
    }

    fun patchClient(clientId: String, patch: JsonPatch): Optional<Client> {
        val url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/clients/$clientId")
            .toUriString()
        val patchedClient = HttpEntity(patch)

        return ofNullable(
            restTemplate.exchange(
                url,
                PATCH,
                patchedClient,
                Client::class.java
            ).body
        )
    }

    fun deleteClient(clientId: String): Optional<Client> {
        val url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/clients/$clientId")
            .toUriString()
        return ofNullable(
            restTemplate.exchange(
                url,
                DELETE,
                EMPTY,
                Client::class.java
            ).body
        )
    }

    private fun buildQueriedUrl(filters: ClientFilters): String {
        val builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/clients/")

        ofNullable(filters.id)
            .ifPresent { builder.queryParam("id", it) }
        ofNullable(filters.city)
            .ifPresent { builder.queryParam("city", it) }
        ofNullable(filters.streetName)
            .ifPresent { builder.queryParam("streetName", it) }
        ofNullable(filters.firstName)
            .ifPresent { builder.queryParam("firstName", it) }
        ofNullable(filters.lastName)
            .ifPresent { builder.queryParam("lastName", it) }

        return builder.toUriString()
    }
}
