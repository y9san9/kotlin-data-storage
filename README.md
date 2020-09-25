<a href="https://codebeat.co/projects/github-com-y9san9-kotlin-data-storage-master"><img alt="codebeat badge" src="https://codebeat.co/badges/eed41e0b-609b-4f23-b952-baef28513114" /></a>

# About
Kotlin Data Storage (KDS) - class that generates the json file and stores the data into there. You can use it as android SharedPreferences.

You can create a property by `property<VariableType>(defaultValue)` delegate.

KDS generates map `property name to property value` and then stores it into the file via Gson.
# Example
```kotlin
// Defining the KDS model 
class SomeClass(val someValue: String)

object AppData : KDataStorage() {
    val launches by property(0)
    val count by property(100)
    val available by property(625)
    val someClass by property<SomeClass>()
}

fun main() {
    // To edit the data, use the commit method (else if you will try to edit data, TransactionError will be thrown)
    AppData.commit {
        launches++
    }
    // To clear all data, use the clear method
    AppData.clear()
    // To clear a single property use the clearProperty method and pass name of the property that you want to delete (if you try to delete a non-existent property, ClearingError will be thrown)
    AppData.clearProperty("count")
    // To clear several properties, use the clearProperties method and pass their names (if you try to delete a non-existent properties, ClearingError will be thrown)
    AppData.clearProperties("someClass", "available")
    // You can use toString method to convert the Storage to String
    println(AppData.toString())
    // You can get the data from anywhere
    println("Total launches ${AppData.launches}")
}
```
Besides, check [it](https://github.com/y9san9/kotlin-data-storage/blob/master/src/test/kotlin/com/y9san9/kds/Main.kt)
# Quick start [![](https://jitpack.io/v/y9san9/kotlin-data-storage.svg)](https://jitpack.io/#y9san9/kotlin-data-storage)
Import the library with jitpack:
## Gradle
```gradle
repositories {
    maven { url "https://jitpack.io" }  // Connecting jitpack to import github repos
}
dependencies {
    implementation 'com.github.y9san9:kotlin-data-storage:-SNAPSHOT'
}
```
## Pro way (Kotlin Gradle DSL)
Add these 2 functions to top of your build.gradle.kts file:
```kotlin
/**
 * Import github repo; first add [jitpack] to repos
 * @param repo username/repo; e.g. y9san9/kotlogram-wrapper
 */
fun DependencyHandlerScope.github(repo: String, tag: String = "-SNAPSHOT") = implementation(
        repo.split("/").let { (username, repo) ->
            "com.github.${username}:${repo}:${tag}"
        }
)
/**
 * Jitpack maven
 */
fun RepositoryHandler.jitpack() = maven("https://jitpack.io")
```
Then, use pretty easy implementation code:
```kotlin
repositories {
    jitpack()
}
dependencies {
    github("y9san9/kotlin-data-storage")
}
```