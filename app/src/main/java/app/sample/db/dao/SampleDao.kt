package app.sample.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.sample.db.models.Sample
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao {

    @Query("SELECT * FROM samples")
    fun getAll(): Flow<List<Sample>>

    @Insert
    fun insert(sample: Sample)

}