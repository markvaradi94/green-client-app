package ro.asis.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import ro.asis.commons.enums.AccountType

data class ClientAccount(
    @JsonProperty("id")
    var id: String = ObjectId.get().toHexString(),

    @JsonProperty("accountId")
    var accountId: String,

    @JsonProperty("clientId")
    var clientId: String,

    @JsonProperty("name")
    var name: String,

    @JsonProperty("username")
    var username: String,

    @JsonProperty("email")
    var email: String,

    @JsonProperty("phoneNumber")
    var phoneNumber: String,

    @JsonProperty("type")
    var type: AccountType
)
