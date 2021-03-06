package lab6server.run

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.LanguageManager
import lab6server.data.utilities.ConfigManager
import lab6server.server.ConnectionHandler
import lab6server.data.utilities.TokenManager
import java.io.Closeable

class RunServerThread(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val users: TokenManager,
    private val config: ConfigManager
): Runnable, Closeable {
    private var run = true
    override fun run() {
        val connection = ConnectionHandler(language, collection, users, config)
        connection.openPort()
        while (run) {
            connection.waitForRequest()
        }
    }

    override fun close() { run = false}
}