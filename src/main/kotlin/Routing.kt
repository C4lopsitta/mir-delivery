package dev.robaldo

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("""
                {
                    "mirDeliveryVersion": "1.0",
                    "success": true
                }
            """.trimIndent())
        }



        get("/api/v1/classrooms") {
            call.respondText("CLASSROOMS")
        }

        get("/api/v1/orders") {
            call.respondText("AUTHED ORDERS")
        }

        post("/api/v1/orders") {
            call.respondText("AUTHED POST ORDER")
        }

        delete("/api/v1/orders/{uid}") {
            call.respondText("ABORT ORDER")
        }
    }
}
