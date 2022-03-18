package lab5.data.commands

import lab5.data.utilities.HistoryManager
import lab5.data.utilities.LanguageManager

/**
 * Prints executed commands and their execution time.
 * Number of stored commands is stored in the config file.
 */
class History(
    private val language: LanguageManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, history: HistoryManager) {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            history.printHistory()
        }
    }
}