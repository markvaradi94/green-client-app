package ro.asis.client.events

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.commons.enums.EventType
import ro.asis.commons.enums.EventType.DELETED

data class ClientDeletionEvent(
    @JsonProperty("clientId")
    val clientId: String,

    @JsonProperty("eventType")
    val eventType: EventType = DELETED
)
