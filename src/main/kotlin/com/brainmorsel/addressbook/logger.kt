package com.brainmorsel.addressbook

import org.slf4j.LoggerFactory
import org.slf4j.Logger

internal inline fun <reified T> T.logger(): Logger {
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java.enclosingClass)
    }
    return LoggerFactory.getLogger(T::class.java)
}
