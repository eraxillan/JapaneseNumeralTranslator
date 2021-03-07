package ktn.jnt

import kotlinx.cinterop.*
import platform.linux.*
import platform.posix.*

class CurrentLocale {
    companion object {
        fun thousandsSeparator(): String {
            val currentLocale = localeconv()
            var result = currentLocale?.pointed?.thousands_sep?.toKString() ?: ""
            if (result.isEmpty())
                result = currentLocale?.pointed?.mon_thousands_sep?.toKString() ?: ""

            return result
        }

        fun isInt(value: String): Boolean {
            try {
                value.replace(thousandsSeparator(), "", true).toInt()
            }
            catch (exc: NumberFormatException) {
                return false
            }
            return true
        }

        fun name(): String = getenv("LANG")?.toKString() ?: "en_US.UTF-8"

        fun isTranslated(): Boolean = name() == "ru_RU.UTF-8" || name() == "en_US.UTF-8"

        fun initLocale() {
            // gettext setup
            setlocale(LC_ALL, "")
            bindtextdomain("jnt", Application.dirPath()) // "/usr/share/locale/"
            //bind_textdomain_codeset("jnt", "UTF-8")
            textdomain("jnt")

            // locale-specific thousand separator setup
            setlocale(LC_MONETARY, name())
        }

        fun fallback() {
            setenv("LANG", "en_US.UTF-8", 1)
            setenv("LANGUAGE", "en_US.UTF-8", 1)
        }

        fun tr(key: String): String = gettext(key)?.toKString() ?: ""

        /*
        fun tr(key: String, lang: String): String {
            val utfLang = if (lang.endsWith(".UTF-8", true)) lang else "$lang.UTF-8"
            setenv("LANGUAGE", utfLang, 1)
            val result = gettext(key)?.toKString() ?: ""
            setenv("LANGUAGE", "en_US.UTF-8", 1)
            return result
        }
        */
    }
}

fun tr(key: String) = CurrentLocale.tr(key)

/*
fun tr(key: String, lang: String = CurrentLocale.name()): String {
    return CurrentLocale.tr(key, lang)
}
*/
