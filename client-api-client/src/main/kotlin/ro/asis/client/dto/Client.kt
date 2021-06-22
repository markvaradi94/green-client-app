package ro.asis.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.commons.model.Address
import ro.asis.commons.model.Cart

data class Client(
    @JsonProperty("id")
    val id: String?,

    @JsonProperty("accountId")
    val accountId: String,

    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("address")
    val address: Address,

    @JsonProperty("cart")
    val cart: Cart,
)
