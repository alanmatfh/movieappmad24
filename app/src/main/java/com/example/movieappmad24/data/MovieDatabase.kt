package com.example.movieappmad24.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.coroutineScope

@Database(
    entities = [Movie::class, MovieImage::class],  // tables in the db
    version = 1,                // schema version; whenever you change schema you have to increase the version number
    exportSchema = false        // for schema version history updates
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao // Dao instance so that the DB knows about the Dao
    // add more daos here if you have multiple tables

    // declare as singleton - companion objects are like static variables in Java
    companion object{
        @Volatile   // never cache the value of instance
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase{
            return instance ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration() // if schema changes wipe the whole db - there are better migration strategies for production usage
                    .addCallback(
                        object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                val request = OneTimeWorkRequest.from(SeedDatabaseWorker::class.java)
                                WorkManager.getInstance(context).enqueue(request)
                            }
                        }
                    )
                    .build() // create an instance of the db
                    .also {
                        instance = it   // override the instance with newly created db
                    }
            }
        }
    }
}

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            Log.i(TAG, "Seeding database")

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
