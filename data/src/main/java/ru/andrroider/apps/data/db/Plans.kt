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
        val description: String, val planId: Long?) {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}

@Dao
interface PlansDao {

    @Language("RoomSql")
    @Query("SELECT * FROM plans where planId IS NULL ")
    fun getAllPlans(): Flowable<List<Plans>>

    @Insert
    fun insert(person: Plans)

    @Language("RoomSql")
    @Query("SELECT * FROM plans where planId=:planId")
    fun getTasksByPlanId(planId: Long?): Flowable<List<Plans>>

}


@Database(entities = [(Plans::class)], version = 1)
abstract class PlansDatabase : RoomDatabase() {
    abstract fun plansDao(): PlansDao
}