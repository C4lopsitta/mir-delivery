package dev.robaldo.routing

import dev.robaldo.InventoryService
import dev.robaldo.Item
import dev.robaldo.configureDatabases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import java.rmi.server.UID
import java.util.UUID

fun Application.configureRoutingInventory(database: Database) {
    val inventoryService = InventoryService(database)


    routing {
        get("/api/v1/inventory/seed") {
            inventoryService.create(
                Item("56f22ab6992246d2b30b479df17384d7", "White Chalk", 10, "")
            )
            inventoryService.create(
                Item("bbb45f7ddd184fa9bcd2b398a789d285", "Black Whiteboard Marker", 5, "")
            )

            call.respond(HttpStatusCode.OK)
        }

        get("/api/v1/inventory") {
            val items = inventoryService.read()

            call.respond(items)
        }

        get("/api/v1/inventory/{uid}") {
            val itemUid = call.parameters["uid"]

            if (itemUid == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    """
                        {
                            "error": "Invalid item UID",
                        }
                    """.trimIndent()
                )
            }

            val item = inventoryService.read(itemUid!!)

            if (item == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    """
                        {
                            "error": "Item not found"
                        }
                    """.trimIndent()
                )
                return@get
            }

            call.respond(item)
        }

        post("/api/v1/inventory") {
            val item = call.receive<Item>()
            item.uid = UUID.randomUUID().toString().replace("-", "")

            try {
                inventoryService.create(item)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
            }

            call.respond( HttpStatusCode.Created, item )
        }

        delete("/api/v1/inventory/{uid}") {
            val itemUid = call.parameters["uid"]
            if (itemUid == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    """
                        {
                            "error": "Item not found"
                        }
                    """.trimIndent()
                )
                return@delete
            }

            inventoryService.delete(itemUid)
            call.respond(HttpStatusCode.OK)
        }
    }
}