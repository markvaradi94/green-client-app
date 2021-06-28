package ro.asis.client.events

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.commons.enums.EventType
import ro.asis.commons.enums.EventType.DELETE

data class ClientDeletedEvent(
    @JsonProperty("clientId")
    val clientId: String,

    @JsonProperty("eventType")
    val eventType: EventType = DELETE
)
