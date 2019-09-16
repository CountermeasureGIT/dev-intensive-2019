package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L        //1_000
const val MINUTE = 60 * SECOND  //60_000
const val HOUR = 60 * MINUTE    //3_600_000
const val DAY = 24 * HOUR       //86_400_000

fun Date.format(pattern : String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when(units) {
         TimeUnits.SECOND -> value * SECOND
         TimeUnits.MINUTE -> value * MINUTE
         TimeUnits.HOUR -> value * HOUR
         TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

/*Реализуй метод plural для всех перечислений TimeUnits следующего вида TimeUnits.SECOND.plural(value:Int) возвращающую значение в виде строки с праильно склоненной единицой измерения
Пример:
TimeUnits.SECOND.plural(1) //1 секунду
TimeUnits.MINUTE.plural(4) //4 минуты
TimeUnits.HOUR.plural(19) //19 часов
TimeUnits.DAY.plural(222) //222 дня*/

fun getPlural(value: Int, one: String, few: String, many: String, other: String): String {
    val mode = if (value % 10 == 1 && value % 100 != 11) //1, 21, 31, 41...
        0
    else if (value % 10 in 2..4 && (value % 100 < 10 || value % 100 >= 20)) //2, 3, 4, 12, 13, 14, 22, 23, 24...
        1
    else
        2 //5, 6, 7, 8, 9, 10, 15, 16, 17, 18, 19, 20...

    return when(mode) {
        0 -> "$value $one"
        1 -> "$value $few"
        2 -> "$value $many"
        else -> "$value $other"
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return getPlural(value, "секунду", "секунды", "секунд", "сек.")
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return getPlural(value, "минуту", "минуты", "минут", "мин.")
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return getPlural(value, "час", "часа", "часов", "ч.")
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return getPlural(value, "день", "дня", "дней", "д.")
        }
    };

    abstract fun plural(value: Int): String
}

/*Реализуй extension Date.humanizeDiff(date) (значение по умолчанию текущий момент времени) для
форматирования вывода разницы между датами в человекообразном формате, с учетом склонения числительных.
Временные интервалы преобразований к человекообразному формату доступны в ресурсах к заданию
Пример:
Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год*/

/*0с - 1с "только что"
1с - 45с "несколько секунд назад"
45с - 75с "минуту назад"
75с - 45мин "N минут назад"
45мин - 75мин "час назад"
75мин 22ч "N часов назад"
22ч - 26ч "день назад"
26ч - 360д "N дней назад"
>360д "более года назад"*/
fun Date.humanizeDiff(date: Date = Date()): String {
    var diff = this.time - date.time
    val pastTime = diff < 0
    diff = diff.absoluteValue
    return when {
        diff > 360 * DAY -> if (pastTime) "более года назад" else "более чем через год"
        diff in 26 * HOUR..360 * DAY -> return if (pastTime) "${TimeUnits.DAY.plural((diff / DAY).toInt())} назад" else "через ${TimeUnits.DAY.plural((diff / DAY).toInt())}"
        diff in 22 * HOUR..26 * HOUR -> return if (pastTime) "день назад" else "через день"
        diff in 75 * MINUTE..22 * HOUR -> return if (pastTime) "${TimeUnits.HOUR.plural((diff / HOUR).toInt())} назад" else "через ${TimeUnits.HOUR.plural((diff / HOUR).toInt())}"
        diff in 45 * MINUTE..75 * MINUTE -> return if (pastTime) "час назад" else "через час"
        diff in 75 * SECOND..45 * MINUTE -> return if (pastTime) "${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())} назад" else "через ${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())}"
        diff in 45 * SECOND..75 * SECOND -> return if (pastTime) "минуту назад" else "через минуту"
        diff in 1 * SECOND..45 * SECOND -> return if (pastTime) "несколько секунд назад" else "через несколько секунд"
        diff in 0..1 * SECOND -> return "только что"
        else -> ""
    }
}