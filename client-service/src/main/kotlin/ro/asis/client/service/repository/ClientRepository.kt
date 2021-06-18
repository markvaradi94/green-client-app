package ro.asis.client.service.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ro.asis.client.service.model.entity.ClientEntity

interface ClientRepository : MongoRepository<ClientEntity, String>
