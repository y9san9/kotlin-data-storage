package com.y9san9.kds.utils

import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson = GsonBuilder()
    .setPrettyPrinting()
    .create()!!

fun Any?.toJson() = gson.toJson(this)!!
inline fun <reified T> String.fromJson(): T = gson.fromJson<T>(this, object : TypeToken<T>() {}.type)