package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

const val MINUTES = 60
const val HOURS = MINUTES * 60
const val DAYS = HOURS * 24



fun Date.add(value: Int, unit: TimeUnits) : Date {
    var time = this.time
    time += when (unit) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.humanizeDiff(date: Date? = Date()) : String {
    val time = this.time
    var seconds: Long = (date!!.time - time) / 1000

    if (abs(seconds) / DAYS > 360)
        return if (seconds > 0)
            "более года назад"
        else
            "более чем через год"

    if (abs(seconds) in 0..1)
        return "только что"

    val isPositive = seconds > 0
    seconds = abs(seconds)

    val difference = when (seconds) {
        in 1..45 -> "несколько секунд"
        in 45..75 -> "минуту"
        in 75..45 * MINUTES -> TimeUnits.MINUTE.plural((seconds / MINUTES).toInt()) //N минут
        in 45 * MINUTES..75 * MINUTES -> "час" // час
        in 75 * MINUTES..22 * HOURS -> TimeUnits.HOUR.plural((seconds / HOURS).toInt()) // N часов
        in 22 * HOURS..26 * HOURS -> "день" // день
        else -> TimeUnits.DAY.plural((seconds / DAYS).toInt())
    }
    return if (isPositive)
        "$difference назад"
    else
        "через $difference"
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int) : String {
        return "$value " + when (this) {
            SECOND -> {
                when {
                    value.rem(100) in 11..19 -> "секунд"
                    value.rem(10) == 1 -> "секунду"
                    value.rem(10) in 2..4 -> "секунды"
                    else -> "секунд"
                }
            }
            MINUTE -> {
                when {
                    value.rem(100) in 11..19 -> "минут"
                    value.rem(10) == 1 -> "минуту"
                    value.rem(10) in 2..4 -> "минуты"
                    else -> "минут"
                }
            }
            HOUR -> {
                when {
                    value.rem(100) in 11..19 -> "часов"
                    value.rem(10) == 1 -> "час"
                    value.rem(10) in 2..4 -> "часа"
                    else -> "часов"
                }

            }
            DAY -> {
                when {
                    value.rem(100) in 11..19 -> "дней"
                    value.rem(10) == 1 -> "день"
                    value.rem(10) in 2..4 -> "дня"
                    else -> "дней"
                }

            }
        }
    }
}

