package com.example.aanestakansanedustajaa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aanestakansanedustajaa.MyApp

@Database(entities = [VotingData::class], version = 1, exportSchema = false)
abstract class VotingDatabase: RoomDatabase() {

    abstract val votingDatabaseDao: VotingDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: VotingDatabase? = null

        fun getInstance(context: Context): VotingDatabase {
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
                        VotingDatabase::class.java,
                        "voting_database"
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