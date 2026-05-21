package com.example.reshmenammapride.engine

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

data class StageRule(
    val temperatureMin: Float,
    val temperatureMax: Float,
    val humidityMin: Float,
    val humidityMax: Float
)

data class ClimateAdvice(
    val status: String,
    val advice: String
)

object ClimateAdvisor {
    val stages = listOf(
        "1st Instar",
        "2nd Instar",
        "3rd Instar",
        "4th Instar",
        "5th Instar"
    )

    fun ruleFor(stage: String): StageRule {
        return when (stage) {
            "1st Instar" -> StageRule(27f, 28f, 85f, 90f)
            "2nd Instar" -> StageRule(26f, 27f, 80f, 85f)
            "3rd Instar" -> StageRule(25f, 26f, 75f, 80f)
            "4th Instar" -> StageRule(24f, 25f, 70f, 75f)
            else -> StageRule(23f, 24f, 65f, 70f)
        }
    }

    fun analyze(stage: String, temperature: Float, humidity: Float): ClimateAdvice {
        if (temperature < 10f || temperature > 45f) {
            return ClimateAdvice(
                status = "INVALID",
                advice = "Temperature must be between 10°C and 45°C."
            )
        }

        if (humidity < 20f || humidity > 100f) {
            return ClimateAdvice(
                status = "INVALID",
                advice = "Humidity must be between 20% and 100%."
            )
        }

        val rule = ruleFor(stage)
        val adviceParts = mutableListOf<String>()
        var score = 0

        if (temperature > rule.temperatureMax) {
            adviceParts.add("Temperature is high for $stage. Open windows and spread wet gunny bags.")
            score += if (temperature > rule.temperatureMax + 2f) 2 else 1
        }

        if (temperature < rule.temperatureMin) {
            adviceParts.add("Temperature is low for $stage. Close windows and keep the room warm.")
            score += if (temperature < rule.temperatureMin - 2f) 2 else 1
        }

        if (humidity > rule.humidityMax) {
            adviceParts.add("Humidity is high. Improve ventilation and avoid extra water spray.")
            score += if (humidity > rule.humidityMax + 10f) 2 else 1
        }

        if (humidity < rule.humidityMin) {
            adviceParts.add("Humidity is low. Spray a small amount of water around the room and use wet cloth.")
            score += if (humidity < rule.humidityMin - 10f) 2 else 1
        }

        return when {
            score == 0 -> ClimateAdvice(
                status = "SAFE",
                advice = "Climate is safe for $stage. Continue monitoring three times a day."
            )
            score <= 2 -> ClimateAdvice(
                status = "CAUTION",
                advice = adviceParts.joinToString(" ")
            )
            else -> ClimateAdvice(
                status = "DANGER",
                advice = adviceParts.joinToString(" ") + " Take action immediately to protect the silkworm batch."
            )
        }
    }

    fun idealRangeText(stage: String): String {
        val rule = ruleFor(stage)
        return "Ideal for $stage: ${rule.temperatureMin.toInt()}-${rule.temperatureMax.toInt()}°C, ${rule.humidityMin.toInt()}-${rule.humidityMax.toInt()}% humidity"
    }
}

fun todayDate(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(Date())
}

fun dateTimeText(timeMillis: Long): String {
    val format = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
    return format.format(Date(timeMillis))
}

fun daysFromDate(dateText: String): Long? {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = format.parse(dateText) ?: return null
        TimeUnit.MILLISECONDS.toDays(Date().time - start.time)
    } catch (_: Exception) {
        null
    }
}

fun harvestMessage(startDate: String): String {
    val days = daysFromDate(startDate) ?: return "Harvest timer: Use date format yyyy-MM-dd"
    val remaining = 25 - days

    return if (remaining > 0) {
        "Harvest timer: Around $remaining days left for cocoon transfer."
    } else {
        "Harvest timer: Batch is ready for spinning tray / cocoon transfer."
    }
}
