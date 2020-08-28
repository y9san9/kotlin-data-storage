package com.y9san9.kds.utils

import java.io.File

internal fun File.refresh(directory: Boolean = false, defaultFileText: String = "") = when {
    exists() -> Unit
    directory -> mkdirs().let { }
    else -> {
        parentFile.mkdirs()
        createNewFile()
        writeText(defaultFileText)
    }
}