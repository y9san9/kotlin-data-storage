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
        data = MyDataClass("asd", false)
        id = 123
        name = "asdasd"
    }
    Test.clearProperties("data", "id")
    println(Test.toString())
    println(Test.data)
    println(Test.id)
}