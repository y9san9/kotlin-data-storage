package com.y9san9.kds


object Test : KDataStorage() {
    var name by property<String?>()
    var id by property(0)
}

fun main() {
    Test.commit {
        name = "test"
        id = 120
    }
}
