package ro.asis.client.events

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.commons.enums.EventType
import ro.asis.commons.enums.EventType.CREATE

data class ClientCreatedEvent(
    @JsonProperty("clientId")
    val clientId: String,

    @JsonProperty("accountId")
    val accountId: String,

    @JsonProperty("eventType")
    val eventType: EventType = CREATE
)
