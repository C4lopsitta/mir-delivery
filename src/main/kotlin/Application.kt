package dev.robaldo.mir_delivery

import dev.robaldo.mir_delivery.routing.configureRoutingInventory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )

    install(ContentNegotiation) {
        json()
    }

    configureSecurity()
    configureSerialization()
//    configureDatabases()
    configureRouting()

    configureRoutingInventory(database)
}
