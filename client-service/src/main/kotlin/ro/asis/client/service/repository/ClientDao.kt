package ro.asis.client.service.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.commons.filters.ClientFilters
import java.util.Optional.ofNullable

@Repository
class ClientDao(private val mongo: MongoTemplate) {
    fun findClients(filters: ClientFilters): List<ClientEntity> {
        val query = Query()
        val criteria = buildCriteria(filters)

        if (criteria.isNotEmpty()) query.addCriteria(Criteria().andOperator(*criteria.toTypedArray()))

        return mongo.find(query, ClientEntity::class.java).toList()
    }

    private fun buildCriteria(filters: ClientFilters): List<Criteria> {
        val criteria = mutableListOf<Criteria>()

        ofNullable(filters.firstName)
            .ifPresent { criteria.add(Criteria.where("firstName").regex(".*$it.*", "i")) }
        ofNullable(filters.lastName)
            .ifPresent { criteria.add(Criteria.where("lastName").regex(".*$it.*", "i")) }
        ofNullable(filters.city)
            .ifPresent { criteria.add(Criteria.where("address.city").regex(".*$it.*", "i")) }
        ofNullable(filters.streetName)
            .ifPresent { criteria.add(Criteria.where("address.streetName").regex(".*$it.*", "i")) }
        ofNullable(filters.accountId)
            .ifPresent { criteria.add(Criteria.where("accountId").`is`(it)) }

        return criteria
    }
}
