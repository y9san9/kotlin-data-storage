package com.y9san9.kds

import com.y9san9.kds.utils.fromJson
import com.y9san9.kds.utils.refresh
import com.y9san9.kds.utils.toJson
import java.io.File
import kotlin.reflect.KProperty

private val dataDir = File(System.getProperty("user.dir"), "data")

fun <T : KDataStorage> T.commit(transaction: T.() -> Unit) {
    beginTransaction()
    transaction()
    endTransaction()
}

open class KDataStorage(source: File? = null) {
    constructor(name: String, dir: File = dataDir) : this(File(dir, "$name.json"))

    private val source = (source ?: File(dataDir, "${this::class.simpleName?.toLowerCase()}.json"))
        .apply { refresh(defaultFileText = "{}") }

    /**
     * always true inside [commit] block
     */
    private var transaction = false
    private val variableValuesMap = load()

    internal fun beginTransaction() {
        transaction = true
    }

    internal fun endTransaction() = commit().apply {
        transaction = false
    }

    private fun commit() = source.writeText(variableValuesMap.toJson())
    private fun load() = source.readText().fromJson<MutableMap<String, String>>()
    fun clear() {
        source.delete()
        variableValuesMap.clear()
    }


    @Suppress("DEPRECATION")
    inline fun <reified T> property(default: T = null as T) = property(default) { it.fromJson() }
    @Deprecated("That is an internal call, do not use it", ReplaceWith("property(default)"))
    fun <T> property(default: T, jsonConverter: (String) -> T) = object : Delegate<T> {
        /**
         * @throws TransactionError if there is no transaction in context
         * Usage:
         * kDataFile.commit {
         *     variable = ...
         * }
         */
        override operator fun setValue(thisRef: KDataStorage, property: KProperty<*>, value: T) {
            if (transaction) {
                variableValuesMap[property.name] = value.toJson()
            } else {
                throw TransactionError
            }
        }

        override operator fun getValue(thisRef: KDataStorage, property: KProperty<*>): T {
            return jsonConverter(variableValuesMap[property.name] ?: return default)
        }
    }
}

interface Delegate<T> {
    operator fun setValue(thisRef: KDataStorage, property: KProperty<*>, value: T)
    operator fun getValue(thisRef: KDataStorage, property: KProperty<*>): T
}

object TransactionError : Throwable("No transaction in context")