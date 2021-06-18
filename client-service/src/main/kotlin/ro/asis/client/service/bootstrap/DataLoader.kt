package ro.asis.client.service.bootstrap

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ro.asis.client.service.model.entity.ClientEntity
import ro.asis.client.service.service.ClientService
import ro.asis.commons.model.Address

@Component
class DataLoader(private val service: ClientService) : CommandLineRunner {
    override fun run(vararg args: String?) {
//        service.addClient(
//            ClientEntity(
//                accountId = "60c99ee037593b352b4676b2",
//                firstName = "Mark",
//                lastName = "Varadi",
//                address = Address(
//                    city = "Oradea",
//                    streetName = "Coposu",
//                    streetNumber = "15",
//                    floor = 3
//                )
//            )
//        )
//        service.addClient(
//            ClientEntity(
//                accountId = "60c99ee137593b352b4676b3",
//                firstName = "Mark",
//                lastName = "Erdei",
//                address = Address(
//                    city = "Oradea",
//                    streetName = "Saldabagiu",
//                    streetNumber = "420",
//                    floor = 69
//                )
//            )
//        )
//        service.addClient(
//            ClientEntity(
//                accountId = "60c99ee137593b352b4676b4",
//                firstName = "Beluci",
//                lastName = "Sinko",
//                address = Address(
//                    city = "Oradea",
//                    streetName = "Velenta",
//                    streetNumber = "100"
//                )
//            )
//        )
//        service.addClient(
//            ClientEntity(
//                accountId = "60c99ee137593b352b4676b5",
//                firstName = "Renitzi",
//                lastName = "Olahtzi",
//                address = Address(
//                    city = "Oradea",
//                    streetName = "Sf Apostol Andrei",
//                    streetNumber = "9",
//                    floor = 2
//                )
//            )
//        )
    }
}
