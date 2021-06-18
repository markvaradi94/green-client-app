package ro.asis.client.events

import ro.asis.client.dto.Client
import ro.asis.commons.enums.EventType

data class ClientEvent(
    private val client: Client,
    private val type: EventType
)
