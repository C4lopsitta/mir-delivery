package dev.robaldo

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class Item(var uid: String?, val name: String, val availableQuantity: Int, val img: String)

class InventoryService(database: Database) {
    object Inventory: Table() {
        val uid = varchar("uid", 32)
        val name = varchar("name", 255)
        val availableQuantity = integer("available_quantity")
        val img = varchar("img", 255)

        override val primaryKey: PrimaryKey = PrimaryKey(uid)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Inventory)
        }
    }

    suspend fun create(item: Item): String = dbQuery {
        Inventory.insert {
            it[uid] = item.uid!!
            it[name] = item.name
            it[img] = item.img
            it[availableQuantity] = item.availableQuantity
        }[Inventory.uid]
    }

    suspend fun read(uid: String): Item? = dbQuery {
        Inventory.selectAll()
            .where { Inventory.uid eq uid }
            .map { Item(it[Inventory.uid], it[Inventory.name], it[Inventory.availableQuantity], it[Inventory.img]) }
            .singleOrNull()
    }

    suspend fun read(): List<Item> = dbQuery {
        Inventory.selectAll()
            .map { Item(it[Inventory.uid], it[Inventory.name], it[Inventory.availableQuantity], it[Inventory.img]) }
    }

    suspend fun update(uid: String, item: Item) {
        dbQuery {
            Inventory.update({ Inventory.uid eq uid }) {
                it[name] = item.name
                it[availableQuantity] = item.availableQuantity
                it[img] = item.img
            }
        }
    }

    suspend fun delete(uid: String) {
        Inventory.deleteWhere { Inventory.uid.eq(uid) }
    }

    private suspend fun <T> dbQuery(block: () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

