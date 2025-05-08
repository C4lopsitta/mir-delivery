package dev.robaldo.mir_delivery.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

sealed interface BaseService<T> {
    object DbTable: Table()

    suspend fun create(item: T): String
    suspend fun read(uid: String): T?
    suspend fun read(): List<T>
    suspend fun update(item: T): String
    suspend fun update(uid: String, item: T)
    suspend fun delete(item: T)

    suspend fun <T2> dbQuery(block: () -> T2): T2 =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
