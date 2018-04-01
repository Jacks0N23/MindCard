package ru.andrroider.apps.data.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language

/**
 * Created by Jackson on 03/02/2018.
 */
@Entity(tableName = "plans")
data class Plans(
    val title: String,
    val description: String,
    val planId: Long? = null,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0L
)

@Dao
interface PlansDao {

    @Language("RoomSql")
    @Query("SELECT * FROM plans where planId IS NULL ")
    fun getAllPlans(): Flowable<List<Plans>>

    @Language("RoomSql")
    @Query("SELECT * FROM plans where id = :itemId ")
    fun getPlanById(itemId: Long): Flowable<Plans>

    @Insert
    fun insert(plan: Plans)

    @Update
    fun update(plan: Plans)

    @Language("RoomSql")
    @Query("SELECT * FROM plans where planId = :planId")
    fun getTasksByPlanId(planId: Long?): Flowable<List<Plans>>

    @Query("DELETE FROM plans WHERE id = :deleteItemId")
    fun deleteById(deleteItemId: Long): Int
}

@Database(entities = [(Plans::class)], version = 1)
abstract class PlansDatabase : RoomDatabase() {

    abstract fun plansDao(): PlansDao
}