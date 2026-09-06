package com.cleaner.mediacleaner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MediaCleanerApplication

fun main(args: Array<String>) {
    runApplication<MediaCleanerApplication>(*args)
}
