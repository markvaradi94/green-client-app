package ro.asis.client.service.model.mappers

import org.springframework.stereotype.Component
import ro.asis.client.dto.ClientAccount
import ro.asis.client.service.model.entity.ClientAccountEntity
import ro.asis.client.service.service.ClientAccountService
import ro.asis.commons.utils.ModelMapper

@Component
class ClientAccountMapper(
    private val service: ClientAccountService
) : ModelMapper<ClientAccount, ClientAccountEntity> {

    override fun toApi(source: ClientAccountEntity): ClientAccount {
        val client = service.findClientForClientAccount(source)
        val account = service.findAccountForClientAccount(source)
        return ClientAccount(
            id = source.id,
            name = "${client.firstName} ${client.lastName}",
            email = source.email,
            phoneNumber = account.phoneNumber,
            type = account.type,
            accountId = source.accountId,
            clientId = source.clientId
        )
    }

    override fun toEntity(source: ClientAccount): ClientAccountEntity {
        return ClientAccountEntity(
            id = source.id,
            clientId = source.clientId,
            accountId = source.accountId,
            email = source.email,
            phoneNumber = source.phoneNumber
        )
    }
}
