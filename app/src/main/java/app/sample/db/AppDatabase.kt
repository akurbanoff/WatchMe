package app.sample.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import app.sample.db.dao.SampleDao
import app.sample.db.models.Sample

@Database(
    entities = [Sample::class],
    version = 1,
    exportSchema = false,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)//, spec = AppDatabase.AutoMigrationFrom2To3::class)
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract val sampleDao: SampleDao

//    @RenameTable(fromTableName = "samples", toTableName = "projectModel")
//    class AutoMigrationFrom2To3: AutoMigrationSpec
}