package ru.skillbranch.devintensive.utils

import java.lang.StringBuilder
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName == null || fullName.trim().isEmpty())
            return null to null

        val parts: List<String>? = fullName.trim().split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return "${firstName?.getOrNull(0)?.toUpperCase()
            ?: ""}${lastName?.getOrNull(0)?.toUpperCase() ?: ""}"
    }

    fun cyrillicToLat(ch : Char) : String {
        val latChar = when (ch) {
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ё' -> "e"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "i"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "h"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "sh'"
            'ъ' -> ""
            'ы' -> "i"
            'ь' -> ""
            'э' -> "e"
            'ю' -> "yu"
            'я' -> "ya"
            else -> ch.toString()
        }
        return latChar
    }

/*Реализуй метод Utils.transliteration(payload divider) принимающий в качестве аргумента строку (divider по умолчанию " ") и возвращающий преобразованную строку из латинских символов, словарь символов соответствия алфовитов доступен в ресурсах к заданию
Пример:
Utils.transliteration("Женя Стереотипов") //Zhenya Stereotipov
Utils.transliteration("Amazing Петр","_") //Amazing_Petr*/

    fun transliteration(payload: String, divider: String = " "): String {
        val words = payload.split(" ").toList()
        val sbTemp = StringBuilder()
        val sbResult = StringBuilder()

        words.getOrNull(0)?.toLowerCase(Locale.ROOT)?.forEach { ch ->
            sbTemp.append(cyrillicToLat(ch))
        }
        if (sbTemp.isNotEmpty()) {
            sbTemp[0] = sbTemp[0].toUpperCase()
            sbResult.append(sbTemp)
            sbTemp.clear()
        }
        words.getOrNull(1)?.toLowerCase(Locale.ROOT)?.forEach { ch ->
            sbTemp.append(cyrillicToLat(ch))
        }
        if (sbTemp.isNotEmpty()) {
            if (sbResult.isNotEmpty())
                sbResult.append(divider)
            sbTemp[0] = sbTemp[0].toUpperCase()
            sbResult.append(sbTemp)
        }
        return sbResult.toString()
    }
}
