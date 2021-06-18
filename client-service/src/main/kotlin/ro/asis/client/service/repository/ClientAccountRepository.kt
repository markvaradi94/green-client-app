package ro.asis.client.service.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ro.asis.client.service.model.entity.ClientAccountEntity
import java.util.*

interface ClientAccountRepository : MongoRepository<ClientAccountEntity, String> {
    fun findByClientId(clientId: String): Optional<ClientAccountEntity>
    fun findByAccountId(accountId: String): Optional<ClientAccountEntity>
    fun findByAccountIdAndClientId(accountId: String, clientId: String): Optional<ClientAccountEntity>
}
