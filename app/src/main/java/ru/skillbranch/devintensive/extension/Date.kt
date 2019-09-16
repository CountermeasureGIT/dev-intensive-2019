package ru.skillbranch.devintensive.extension

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

fun getPlural(value: Int): Int {
    return if (value % 10 == 1 && value % 100 != 11) //1, 21, 31, 41...
        0
    else if (value % 10 in 2..4 && (value % 100 < 10 || value % 100 >= 20)) //2, 3, 4, 12, 13, 14, 22, 23, 24...
        1
    else
        2 //5, 6, 7, 8, 9, 10, 15, 16, 17, 18, 19, 20...
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return when(getPlural(value)) {
                0 -> "$value секунду"
                1 -> "$value секунды"
                2 -> "$value секунд"
                else -> "$value сек."
            }
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return when(getPlural(value)) {
                0 -> "$value минуту"
                1 -> "$value минуты"
                2 -> "$value минут"
                else -> "$value мин."
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return when(getPlural(value)) {
                0 -> "$value час"
                1 -> "$value часа"
                2 -> "$value часов"
                else -> "$value ч."
            }
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return when(getPlural(value)) {
                0 -> "$value день"
                1 -> "$value дня"
                2 -> "$value дней"
                else -> "$value д."
            }
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
fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = this.time - date.time
    var str: String
    str = if (diff >= 0) {
        "через $diff миллисекунд"
    } else {
        "${diff.absoluteValue} миллисекунд назад"
    }
    return str
}