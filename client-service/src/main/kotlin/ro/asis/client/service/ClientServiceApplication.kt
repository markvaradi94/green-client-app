package ro.asis.client.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@EnableEurekaClient
@SpringBootApplication(
    scanBasePackages = [
        "ro.asis.commons",
        "ro.asis.account.client",
        "ro.asis.client"
    ]
)
class ClientServiceApplication

fun main(args: Array<String>) {
    runApplication<ClientServiceApplication>(*args)

}
