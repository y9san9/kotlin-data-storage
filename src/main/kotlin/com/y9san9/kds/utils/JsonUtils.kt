package com.y9san9.kds.utils
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


val gson = Gson()

fun Any?.toJson() = gson.toJson(this)!!
inline fun <reified T> String.fromJson() = gson.fromJson<T>(this, object : TypeToken<T>(){}.type)!!
