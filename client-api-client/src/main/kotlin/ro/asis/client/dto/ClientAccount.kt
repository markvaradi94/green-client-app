package ro.asis.client.dto

import ro.asis.commons.enums.AccountType

data class ClientAccount(
    var id: String?,
    var accountId: String,
    var clientId: String,
    var name: String,
    var username: String,
    var email: String,
    var phoneNumber: String,
    var type: AccountType
)
