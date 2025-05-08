package dev.robaldo.mir_delivery

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    install(Authentication) {
        bearer {
            
        }
    }
}
