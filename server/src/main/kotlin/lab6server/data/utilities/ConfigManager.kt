package lab6server.data.utilities

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.Properties

/**
 * Class stores the config for client, saved in file.
 * Supports common values.
 */
class ConfigManager(private val language: LanguageManager) {
    private var config: Properties = Properties()
    init {
        setDefaults()
        try {
            val stream = FileInputStream("./server/src/main/resources/app.config")
            val reader = InputStreamReader(stream)
            config.load(reader)
        } catch (e: FileNotFoundException) {
            println(language.getString("ConfigCorrupt"))
        } catch (e: IOException) {
            println(language.getString("ConfigError"))
        }
        changeLanguage(config.getProperty("Language"))
    }
    private fun setDefaults() {
        config["Language"] = "en"
        config["CollectionPath"] = "./server/src/main/resources/"
        config["port"] = "5428"
        config["SoTimeout"] = "600000"
        config["dbAddress"] = "jdbc:postgresql://localhost:5555/studs"
    }

    fun getString(arg: String): String = config.getProperty(arg)
    fun getDataPath() = config.getProperty("CollectionPath") + "collection.csv"
    fun getPort() = config.getProperty("port").toInt()
    fun getSoT() = config.getProperty("SoTimeout").toInt()
    private fun changeLanguage(lang: String) {language.setLanguage(lang)}
}