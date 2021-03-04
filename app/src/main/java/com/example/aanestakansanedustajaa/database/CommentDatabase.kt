package com.example.aanestakansanedustajaa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aanestakansanedustajaa.MyApp

@Database(entities = [CommentData::class], version = 1, exportSchema = false)
abstract class CommentDatabase: RoomDatabase() {

    abstract val commentDatabaseDao: CommentDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: CommentDatabase? = null

        fun getInstance(context: Context): CommentDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        MyApp.appContext,
                        CommentDatabase::class.java,
                        "comment_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}