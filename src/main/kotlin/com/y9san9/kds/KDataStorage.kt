package com.y9san9.kds

import com.y9san9.kds.utils.fromJson
import com.y9san9.kds.utils.refresh
import com.y9san9.kds.utils.toJson
import java.io.File
import kotlin.reflect.KProperty


private val dataDir = File(System.getProperty("user.dir"), "data")

fun <T: KDataStorage> T.commit(transaction: T.() -> Unit) {
    beginTransaction()
    transaction()
    endTransaction()
}

open class KDataStorage(source: File? = null) {
    private val source = (source ?: File(dataDir, "${this::class.simpleName?.toLowerCase()}.json"))
        .apply { refresh(defaultFileText = "{}") }

    protected var transaction = false
    protected val variableValuesMap = load()

    internal fun beginTransaction() {
        transaction = true
    }
    internal fun endTransaction() = commit().apply {
        transaction = false
    }
    private fun commit() = source.writeText(variableValuesMap.toJson())
    private fun load() = source.readText().fromJson<MutableMap<String, String>>()

    protected inline fun <reified T> property(default: T = null as T) = object : Delegate<T> {
        /**
         * @throws TransactionError if there is no transaction in context
         * Usage:
         * kDataFile.commit {
         *     variable = ...
         * }
         */
        override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            if(transaction) {
                variableValuesMap[property.name] = value.toJson()
            } else {
                throw TransactionError
            }
        }
        override operator fun getValue(thisRef: Any, property: KProperty<*>) =
            variableValuesMap[property.name]?.fromJson() ?: default
    }
}

interface Delegate<T> {
    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T)
    operator fun getValue(thisRef: Any, property: KProperty<*>): T
}

object TransactionError : Throwable("No transaction in context")
