package ro.asis.client.dto

import ro.asis.commons.model.Address
import ro.asis.commons.model.Cart

data class Client(
    val id: String?,
    val accountId: String,
    val firstName: String,
    val lastName: String,
    val address: Address,
    val cart: Cart,
)
