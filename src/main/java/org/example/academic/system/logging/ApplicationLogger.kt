package org.example.academic.system.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * TUS-2390 - Configure application logging infrastructure
 * Logging centralizado usando SLF4J + Logback.
 */
object ApplicationLogger {
    private val logger: Logger = LoggerFactory.getLogger(ApplicationLogger::class.java)

    @JvmStatic
    fun info(message: String?) {
        logger.info(message)
    }

    @JvmStatic
    fun warn(message: String?) {
        logger.warn(message)
    }

    @JvmStatic
    fun error(message: String?) {
        logger.error(message)
    }

    @JvmStatic
    fun debug(message: String?) {
        logger.debug(message)
    }
}