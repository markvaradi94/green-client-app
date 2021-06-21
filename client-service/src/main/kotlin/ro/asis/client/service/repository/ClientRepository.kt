package ro.asis.client.service.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ro.asis.client.service.model.entity.ClientEntity
import java.util.*

interface ClientRepository : MongoRepository<ClientEntity, String> {
    fun existsByAccountId(accountId: String): Boolean
    fun findByAccountId(accountId: String): Optional<ClientEntity>
}
