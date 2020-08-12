# About
Kotlin Data Storage (KDS) - class that generates json file and stores data there. You can use it as android SharedPreferences
Create property you can by `property<VariableType>(defaultValue)` delegate

KDS generates map `property name to property value` and then via gson and delegates store them to file
# Example
```kotlin
// Defining KDS model 
object AppData : KDataStorage() {
    val launches by property(0)
}

fun main() {
    // To edit data use commit method (else if you will try to edit data, TransactionError will be thrown)
    AppData.commit {
        launches++
    }
    // You can get data anywhere
    println("Total launches ${AppData.launches}")
}
```
Also check [it](https://github.com/y9san9/kotlin-data-storage/blob/master/src/test/kotlin/com/y9san9/kds/Main.kt)
# Quick start [![](https://jitpack.io/v/y9san9/kotlin-data-storage.svg)](https://jitpack.io/#y9san9/kotlin-data-storage)
Import library with jitpack
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
Add this 2 functions to top of your build.gradle.kts
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
then use pretty easy implementation code
```kotlin
repositories {
    jitpack()
}
dependencies {
    github("y9san9/kotlin-data-storage")
}
```
