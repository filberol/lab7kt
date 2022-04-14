package lab6server.run

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.LanguageManager
import lab6server.data.utilities.ConfigManager
import lab6server.server.ConnectionHandler
import lab6server.server.SqlHandler
import lab6server.server.TokenManager
import java.io.Closeable

class RunPortThread(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val users: TokenManager,
    private val config: ConfigManager,
    private val sqlHandler: SqlHandler
): Runnable, Closeable {
    private var run = true
    override fun run() {
        while (run) {
            val connection = ConnectionHandler(language, collection, users, sqlHandler, config)
            connection.openPort()

            while (true) {
                connection.waitForRequest()
            }
        }
    }

    override fun close() { run = false}
}