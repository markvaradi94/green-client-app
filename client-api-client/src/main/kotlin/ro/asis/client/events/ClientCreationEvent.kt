package ro.asis.client.events

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.commons.enums.EventType
import ro.asis.commons.enums.EventType.CREATED

data class ClientCreationEvent(
    @JsonProperty("clientId")
    val clientId: String,

    @JsonProperty("accountId")
    val accountId: String,

    @JsonProperty("eventType")
    val eventType: EventType = CREATED
)
