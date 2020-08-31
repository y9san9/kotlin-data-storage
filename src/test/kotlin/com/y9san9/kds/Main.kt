package com.y9san9.kds


data class MyDataClass(
    val testString: String,
    val testBoolean: Boolean
)

object Test : KDataStorage() {
    var name by property<String?>()
    var id by property(0)
    var data by property<MyDataClass?>()
    var list by property(mutableListOf<Any>())
}

fun main() {
    Test.commit {
        data = null
        id = 123
    }
    println(Test.data)
    Test.clear()
    println(Test.id)
}
