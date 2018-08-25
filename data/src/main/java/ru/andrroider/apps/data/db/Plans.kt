package ru.andrroider.apps.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language

/**
 * Created by Jackson on 03/02/2018.
 */
@Entity(tableName = "plans")
data class Plans(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true) var id: Long = 0L,
        val planId: Long? = null,
        val title: String,
        val description: String,
        val colorInt: Int = 0,
        val fromTimestamp: Long? = null,
        val toTimestamp: Long? = null
)

@Dao
interface PlansDao {

    @Language("RoomSql")
    @Query("SELECT * FROM plans where planId IS NULL ")
    fun getAllPlans(): Flowable<List<Plans>>

    @Language("RoomSql")
    @Query("SELECT * FROM plans where fromTimestamp is not null and toTimestamp is not null")
    fun getScheduledTasks(): Flowable<List<Plans>>

    @Language("RoomSql")
    @Query("SELECT * FROM plans where id = :itemId ")
    fun getPlanById(itemId: Long): Flowable<Plans>

    @Insert
    fun insert(plan: Plans)

    @Update(onConflict = REPLACE)
    fun update(plan: Plans)

    @Language("RoomSql")
    @Query("SELECT * FROM plans where planId = :planId")
    fun getTasksByPlanId(planId: Long?): Flowable<List<Plans>>

    @Query("DELETE FROM plans WHERE id = :deleteItemId")
    fun deleteById(deleteItemId: Long)
}

@Database(entities = [(Plans::class)], version = 2)
abstract class PlansDatabase : RoomDatabase() {

    abstract fun plansDao(): PlansDao
}