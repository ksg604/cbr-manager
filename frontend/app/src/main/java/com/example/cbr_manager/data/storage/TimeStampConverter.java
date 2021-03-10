package com.example.cbr_manager.data.storage;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class TimeStampConverter {
    @TypeConverter
    public static Timestamp fromLong(long value) {
        return new Timestamp(value);
    }

    @TypeConverter
    public static long fromTimestamp(Timestamp date){
        return date.getTime();
    }
}
