package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.ConfigManager
import lab6server.data.utilities.LanguageManager
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Scanner
import kotlin.system.exitProcess

/**
 * Class provides basic connection with database.
 * Program cannot work without database.
 * Database holds user table and element history table, for each has a different manager.
 */
class SqlHandler(
    private val language: LanguageManager,
    private val config: ConfigManager,
    collection: CollectionManager
) {
    private val dbConnection: Connection by lazy { settleDatabaseConnection() }
    private lateinit var dbPassword: String
    private lateinit var dblogin: String
    val dataManager = SqlDataManager(dbConnection, collection)
    val userManager = SqlUserManager(dbConnection)

    //Команда для прокида порта
    //ssh -L <порт>:pg:5432 s<ISU>@se.ifmo.ru -p 2222
    private fun settleDatabaseConnection(): Connection {
        val databaseDriver = "org.postgresql.Driver"
        Class.forName(databaseDriver)
        try {
            setVars()
            return DriverManager.getConnection(
                config.getString("dbAddress"),
                dblogin,
                dbPassword
            )
        } catch (e: SQLException) {
            println(language.getString("DBError"))
            exitProcess(1)
        }

    }
    private fun setVars() {
        val scan = Scanner(System.`in`)
        print("login")
        dblogin = scan.nextLine()
        print("password")
        dbPassword = scan.nextLine()
    }
}