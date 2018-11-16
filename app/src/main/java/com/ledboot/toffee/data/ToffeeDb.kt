package com.ledboot.toffee.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ledboot.toffee.model.Topics


@Database(entities = [Topics::class], version = 1, exportSchema = false)
abstract class ToffeeDb : RoomDatabase() {

}