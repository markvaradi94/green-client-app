package ro.asis.client.service.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ro.asis.commons.model.Address
import ro.asis.commons.model.Cart

@Document(collection = "clients")
class ClientEntity(
    @Id
    var id: String? = ObjectId.get().toHexString(),

    var accountId: String,
    var firstName: String,
    var lastName: String,
    var address: Address = Address(),
    var cart: Cart = Cart()
)
