package lab6server.data.commands

import lab6server.data.utilities.LanguageManager


/**
 * Abstract command when executing anything will return a boolean value as a feedback of the execution of the command.
 */
sealed class AbstractCommand(lang: LanguageManager) {
    /**
     * Specified langHolder returns language needed to print to console.
     */
    private var language: LanguageManager

    /**
     * Some commands not needing arguments will throw exception if try to use them with.
     * You will be asked to proceed or not the following command.
     */
    init {
        language = lang
    }

    open fun execute(): Boolean {return true}
}