package ro.asis.client.service.service.validator

import com.cloudmersive.client.AddressApi
import com.cloudmersive.client.invoker.Configuration
import com.cloudmersive.client.invoker.auth.ApiKeyAuth
import com.cloudmersive.client.model.ValidateAddressRequest
import org.springframework.stereotype.Component
import ro.asis.account.client.AccountApiClient
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.repository.ClientRepository
import ro.asis.commons.exceptions.ValidationException
import ro.asis.commons.model.Address
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of

@Component
class ClientValidator(
    private val repository: ClientRepository,
    private val accountApiClient: AccountApiClient
) {

    fun validateReplaceOrThrow(clientId: String, newClient: ClientEntity) =
        exists(clientId)
            .or { validate(client = newClient) }
            .ifPresent { throw it }

    fun validateNewOrThrow(client: ClientEntity) =
        validate(client = client).ifPresent { throw it }

    fun validateExistsOrThrow(clientId: String) = exists(clientId).ifPresent { throw it }

    private fun validate(client: ClientEntity): Optional<ValidationException> {
        accountAlreadyIsInUseOrDoesNotExist(client).ifPresent { throw it }
        simpleAddressValidation(client.address).ifPresent { throw it }
        return empty()
    }

    private fun simpleAddressValidation(address: Address): Optional<ValidationException> {
        return when (true) {
            address.city.isBlank() -> of(ValidationException("City must be valid"))
            address.streetName.isBlank() -> of(ValidationException("Street name must be valid"))
            address.streetNumber.isBlank() -> of(ValidationException("Street number must be valid"))
            address.zipCode.isNullOrBlank() -> of(ValidationException("Zipcode must be valid"))
            else -> empty()
        }
    }

    private fun accountAlreadyIsInUseOrDoesNotExist(client: ClientEntity): Optional<ValidationException> {
        return if (!accountExists(client.accountId)) of(ValidationException("Client cannot be registered with an account"))
        else if (clientWithAccountExists(client.accountId)) of(ValidationException("There is already a client registered for this account"))
        else empty()
    }

    private fun clientWithAccountExists(accountId: String): Boolean = repository.existsByAccountId(accountId)

    private fun accountExists(accountId: String): Boolean = accountApiClient.getAccount(accountId).isPresent

    private fun exists(clientId: String): Optional<ValidationException> {
        return if (repository.existsById(clientId)) empty()
        else of(ValidationException("Client with id $clientId doesn't exist."))
    }

    private fun validateAddressWithAPI(address: Address): Optional<ValidationException> {
        val defaultClient = Configuration.getDefaultApiClient()
        val apiKey = defaultClient.getAuthentication("Apikey") as ApiKeyAuth
        apiKey.apiKey = "0cfb8e6c-9b70-4631-b69d-d4f921ad19f5"

        val apiInstance = AddressApi()
        val input = ValidateAddressRequest()

        input.city = address.city
        input.countryCode = "RO"
        input.countryFullName = "Romania"
        input.postalCode = address.zipCode
        input.streetAddress = "${address.streetName} ${address.streetNumber}"

        val result = apiInstance.addressValidateAddress(input)

        println(input)
        println("BORAT")
        println("result for address validation is: $result")
        println("BORAT")

        return if (result.isValidAddress) empty()
        else of(ValidationException("Address is invalid"))
    }
}
