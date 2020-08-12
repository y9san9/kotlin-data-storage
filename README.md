# About
We are generating map `property name to property value` and then via gson and delegates, set values and store them to file
# Examples
```kotlin
// Defining KDS model 
object AppData : KDataStorage() {
    val someToken by property<String?>()
    val launches by property(0)
}

fun main() {
    // To edit data use commit method (else if you try to edit data TransactionError throwable will be thrown)
    AppData.commit {
        if(someToken == null)
            someToken = /* Requesting user input */
        launches++
    }
    // Get data you can anywhere
    println("Total launches ${AppData.launches}")
}
```
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
