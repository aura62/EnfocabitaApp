package com.aura.enfocabita.data.local.database.entidades

import androidx.room.TypeConverter
import java.util.Date

class ConvertType {

    @TypeConverter fun fromTimestamp(vTime: Long?): Date? =
        vTime?.let { Date(it) }

    @TypeConverter fun dateToTimestamp(date: Date?): Long? =
        date?.time

    @TypeConverter fun toUserType(vUser: String): UserType =
        runCatching { UserType.valueOf(vUser) }
            .getOrDefault(UserType.STANDARD)

    @TypeConverter fun fromUsertype(typeUsr: UserType): String =
        typeUsr.name

    @TypeConverter
    fun toPeriodUnit(value: String): PeriodUnit =
        runCatching { PeriodUnit.valueOf(value) }.getOrDefault(PeriodUnit.DIARIO)

    @TypeConverter
    fun fromPeriodUnit(unit: PeriodUnit): String =
        unit.name

    @TypeConverter fun toHabitCategory(value: String): TypeHab =
        runCatching { TypeHab.valueOf(value) }.getOrDefault(TypeHab.ADQUIRIR)

    @TypeConverter fun fromHabitCategory(category: TypeHab): String =
        category.name
}
