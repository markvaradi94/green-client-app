package ro.asis.client.service.model.mappers

import org.springframework.stereotype.Component
import ro.asis.client.dto.Client
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.commons.utils.ModelMapper

@Component
class ClientMapper : ModelMapper<Client, ClientEntity> {
    override fun toApi(source: ClientEntity): Client {
        return Client(
            id = source.id,
            accountId = source.accountId,
            firstName = source.firstName,
            lastName = source.lastName,
            address = source.address,
            cart = source.cart
        )
    }

    override fun toEntity(source: Client): ClientEntity {
        return ClientEntity(
            id = source.id,
            accountId = source.accountId,
            firstName = source.firstName,
            lastName = source.lastName,
            address = source.address,
            cart = source.cart
        )
    }
}
