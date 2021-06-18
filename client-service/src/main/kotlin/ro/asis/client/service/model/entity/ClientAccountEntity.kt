package ro.asis.client.service.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "client_accounts")
class ClientAccountEntity(
    @Id
    var id: String? = ObjectId.get().toHexString(),

    var clientId: String,
    var accountId: String,
    var username: String,
    var email: String,
    var phoneNumber: String
)
