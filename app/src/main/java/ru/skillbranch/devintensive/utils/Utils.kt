package ru.skillbranch.devintensive.utils

import java.util.*

val map = hashMapOf(
    "а" to "a",
    "б" to "b",
    "в" to "v",
    "г" to "g",
    "д" to "d",
    "е" to "e",
    "ё" to "e",
    "ж" to "zh",
    "з" to "z",
    "и" to "i",
    "й" to "i",
    "к" to "k",
    "л" to "l",
    "м" to "m",
    "н" to "n",
    "о" to "o",
    "п" to "p",
    "р" to "r",
    "с" to "s",
    "т" to "t",
    "у" to "u",
    "ф" to "f",
    "х" to "h",
    "ц" to "c",
    "ч" to "ch",
    "ш" to "sh",
    "щ" to "sh'",
    "ъ" to "",
    "ы" to "i",
    "ь" to "",
    "э" to "e",
    "ю" to "yu",
    "я" to "ya"
)

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.trim()?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (lastName == "")
            lastName = null
        if (firstName == "")
            firstName = null

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?) : String? {
        val f: String? = firstName?.trim()
        val l: String? = lastName?.trim()

        if (f == null && l == null)
            return null

        if (f == "" && l == "")
            return null

        var initials = ""
        if (f != "" && f != null)
            initials += f[0].toUpperCase()
        if (l != "" && l != null)
            initials += l[0].toUpperCase()

        return if (initials == "") null else initials

    }

    fun transliteration(payload: String, divider: String = " ") : String {
        var translit = ""
        for (i in payload.indices) {
            var char = payload[i].toString()
            val isLowerCase: Boolean =
                if (char[0].isLowerCase())
                    true
                else {
                    char = char.toLowerCase(Locale.getDefault())
                    false
                }

            translit += when (char) {
                in "а".."я" -> {
                    if (isLowerCase)
                        map[char]
                    else {
                        val replacement = map[char]
                        if (replacement != "")
                            replacement!![0].toString().toUpperCase(Locale.getDefault()) +
                                replacement.slice(1 until replacement.length)
                        else
                            replacement
                    }
                }
                "ё" -> {
                    if (isLowerCase)
                        map[char]
                    else {
                        map[char].toString().toUpperCase()
                    }
                }
                " " -> divider
                else -> if (isLowerCase) char else char.toUpperCase(Locale.getDefault())
            }
        }
        return translit
    }
}