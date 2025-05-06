package dev.robaldo.routing

import dev.robaldo.InventoryService
import dev.robaldo.Item
import dev.robaldo.models.ErrorResponse
import dev.robaldo.models.SuccessObjectResponse
import dev.robaldo.models.SuccessResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import java.util.UUID

fun Application.configureRoutingInventory(database: Database) {
    val inventoryService = InventoryService(database)


    routing {
        get("/api/v1/inventory/seed") {
            inventoryService.create(
                Item(UUID.randomUUID().toString(), "White Chalk", 10, "")
            )
            inventoryService.create(
                Item(UUID.randomUUID().toString(), "Black Whiteboard Marker", 5, "")
            )

            call.respond(HttpStatusCode.OK, SuccessResponse("Successfully seeded database") )
        }

        get("/api/v1/inventory") {
            val items = inventoryService.read()

            call.respond( SuccessObjectResponse("Successfully fetched all items", item = items, httpStatus = 200 ) )
        }

        get("/api/v1/inventory/{uid}") {
            val itemUid = call.parameters["uid"]

            if (itemUid == null) {
                call.respond( HttpStatusCode.BadRequest, ErrorResponse("Invalid UID", httpStatus = 400) )
            }

            val item = inventoryService.read(itemUid!!)

            if (item == null) {
                call.respond( HttpStatusCode.NotFound, ErrorResponse("Item not found", httpStatus = 404) )
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

            call.respond( HttpStatusCode.Created, SuccessObjectResponse( "Item successfully created", item = item ) )
        }

        delete("/api/v1/inventory/{uid}") {
            val itemUid = call.parameters["uid"]
            if (itemUid == null) {
                call.respond( HttpStatusCode.BadRequest, ErrorResponse("Invalid UID", httpStatus = 400) )
                return@delete
            }

            inventoryService.delete(itemUid)
            call.respond( HttpStatusCode.OK, SuccessResponse("Item $itemUid successfully deleted", httpStatus = 200) )
        }
    }
}