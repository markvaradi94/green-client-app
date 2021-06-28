package ro.asis.client.events

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.client.dto.Client
import ro.asis.commons.enums.EventType
import ro.asis.commons.enums.EventType.MODIFY

class ClientModifiedEvent(
    @JsonProperty("clientId")
    val clientId: String,

    @JsonProperty("eventType")
    val eventType: EventType = MODIFY,

    @JsonProperty("editedClient")
    val editedClient: Client
)
