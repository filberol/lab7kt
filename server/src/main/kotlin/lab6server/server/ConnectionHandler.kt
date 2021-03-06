package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.ConfigManager
import lab6server.data.utilities.LanguageManager
import lab6server.data.utilities.TokenManager
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.Executors
import kotlin.system.exitProcess

/**
 * Handles creation of socket, client streams and sending client process to the new Thread.
 */
class ConnectionHandler(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val users: TokenManager,
    config: ConfigManager
) {
    private val port: Int = config.getPort()
    private val soTimeout = config.getSoT()
    private lateinit var serverSocket: ServerSocket
    private lateinit var sender: ObjectOutputStream
    private lateinit var receiver: ObjectInputStream

    private val executor = Executors.newFixedThreadPool(2)

    fun openPort() {
        try {
            serverSocket = ServerSocket(port)
            serverSocket.soTimeout = soTimeout
        } catch (e: IOException) {
            println(language.getString("SocketError"))
            exitProcess(1)
        } catch (e: SecurityException) {
            println(language.getString("SocketError"))
            exitProcess(1)
        }  catch (e: IllegalArgumentException) {
            println(language.getString("ConnectionFalse"))
            exitProcess(1)
        }
    }

    /**
     * Stays in wait for request method until gets one or closes.
     * Sends processing request a new Thread.
     */
    fun waitForRequest() {
        try {
            println("Port $port")
            val clientSocket: Socket = serverSocket.accept()
            receiver = ObjectInputStream(clientSocket.getInputStream())
            sender = ObjectOutputStream(clientSocket.getOutputStream())
            executor.execute(ClientProcessorThread(collection, language, users, receiver, sender))
        } catch (e: IOException) {
            println(language.getString("SocketAccept"))
        } catch (e: SecurityException) {
            println(language.getString("SocketAccept"))
        }  catch (e: SocketTimeoutException) {
            println(language.getString("SoTimeout"))
        } catch (e: SocketException) {
            println(language.getString("Interrupt"))
        }
    }
}