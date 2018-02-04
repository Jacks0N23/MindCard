package ru.andrroider.apps.data.db

import android.arch.persistence.room.*
import io.reactivex.Flowable

/**
 * Created by Jackson on 03/02/2018.
 */

@Entity(tableName = "plans")
data class Plans(
        val title: String,
        val description: String) {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface PlansDao {

    @Query("SELECT * FROM plans")
    fun getAllPlans(): Flowable<List<Plans>>

    @Insert
    fun insert(person: Plans)
}


@Database(entities = [(Plans::class)], version = 1)
abstract class PlansDatabase : RoomDatabase() {
    abstract fun plansDao(): PlansDao
}