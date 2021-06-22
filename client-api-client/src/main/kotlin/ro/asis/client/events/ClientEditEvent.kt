package ro.asis.client.events

import com.fasterxml.jackson.annotation.JsonProperty
import ro.asis.client.dto.Client
import ro.asis.commons.enums.EventType
import ro.asis.commons.enums.EventType.MODIFIED

class ClientEditEvent(
    @JsonProperty("clientId")
    val clientId: String,

    @JsonProperty("eventType")
    val eventType: EventType = MODIFIED,

    @JsonProperty("editedClient")
    val editedClient: Client
)
