package com.aura.enfocabita.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun obtenerRangoDelDia(fecha: LocalDate = LocalDate.now()): Pair<Date, Date> {
    val zone = ZoneId.systemDefault()
    val start = Date.from(fecha.atStartOfDay(zone).toInstant())
    val end = Date.from(fecha.plusDays(1).atStartOfDay(zone).toInstant())
    return start to end
}