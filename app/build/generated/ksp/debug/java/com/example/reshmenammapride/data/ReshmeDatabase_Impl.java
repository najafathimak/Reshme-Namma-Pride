package com.example.reshmenammapride.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReshmeDatabase_Impl extends ReshmeDatabase {
  private volatile BatchDao _batchDao;

  private volatile ClimateLogDao _climateLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `silkworm_batches` (`id` TEXT NOT NULL, `batchName` TEXT NOT NULL, `breed` TEXT NOT NULL, `startDate` TEXT NOT NULL, `stage` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `climate_logs` (`id` TEXT NOT NULL, `batchId` TEXT NOT NULL, `batchName` TEXT NOT NULL, `temperature` REAL NOT NULL, `humidity` REAL NOT NULL, `stage` TEXT NOT NULL, `status` TEXT NOT NULL, `advice` TEXT NOT NULL, `loggedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`batchId`) REFERENCES `silkworm_batches`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_climate_logs_batchId` ON `climate_logs` (`batchId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3793b1d6dab330993239bcd4fa3dd45a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `silkworm_batches`");
        db.execSQL("DROP TABLE IF EXISTS `climate_logs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSilkwormBatches = new HashMap<String, TableInfo.Column>(6);
        _columnsSilkwormBatches.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSilkwormBatches.put("batchName", new TableInfo.Column("batchName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSilkwormBatches.put("breed", new TableInfo.Column("breed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSilkwormBatches.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSilkwormBatches.put("stage", new TableInfo.Column("stage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSilkwormBatches.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSilkwormBatches = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSilkwormBatches = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSilkwormBatches = new TableInfo("silkworm_batches", _columnsSilkwormBatches, _foreignKeysSilkwormBatches, _indicesSilkwormBatches);
        final TableInfo _existingSilkwormBatches = TableInfo.read(db, "silkworm_batches");
        if (!_infoSilkwormBatches.equals(_existingSilkwormBatches)) {
          return new RoomOpenHelper.ValidationResult(false, "silkworm_batches(com.example.reshmenammapride.data.SilkBatch).\n"
                  + " Expected:\n" + _infoSilkwormBatches + "\n"
                  + " Found:\n" + _existingSilkwormBatches);
        }
        final HashMap<String, TableInfo.Column> _columnsClimateLogs = new HashMap<String, TableInfo.Column>(9);
        _columnsClimateLogs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("batchId", new TableInfo.Column("batchId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("batchName", new TableInfo.Column("batchName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("humidity", new TableInfo.Column("humidity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("stage", new TableInfo.Column("stage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("advice", new TableInfo.Column("advice", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateLogs.put("loggedAt", new TableInfo.Column("loggedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClimateLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysClimateLogs.add(new TableInfo.ForeignKey("silkworm_batches", "CASCADE", "NO ACTION", Arrays.asList("batchId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesClimateLogs = new HashSet<TableInfo.Index>(1);
        _indicesClimateLogs.add(new TableInfo.Index("index_climate_logs_batchId", false, Arrays.asList("batchId"), Arrays.asList("ASC")));
        final TableInfo _infoClimateLogs = new TableInfo("climate_logs", _columnsClimateLogs, _foreignKeysClimateLogs, _indicesClimateLogs);
        final TableInfo _existingClimateLogs = TableInfo.read(db, "climate_logs");
        if (!_infoClimateLogs.equals(_existingClimateLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "climate_logs(com.example.reshmenammapride.data.ClimateLog).\n"
                  + " Expected:\n" + _infoClimateLogs + "\n"
                  + " Found:\n" + _existingClimateLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3793b1d6dab330993239bcd4fa3dd45a", "5cbfbd26ac96a0802d8ae809dc1ef955");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "silkworm_batches","climate_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `silkworm_batches`");
      _db.execSQL("DELETE FROM `climate_logs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BatchDao.class, BatchDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ClimateLogDao.class, ClimateLogDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public BatchDao batchDao() {
    if (_batchDao != null) {
      return _batchDao;
    } else {
      synchronized(this) {
        if(_batchDao == null) {
          _batchDao = new BatchDao_Impl(this);
        }
        return _batchDao;
      }
    }
  }

  @Override
  public ClimateLogDao climateLogDao() {
    if (_climateLogDao != null) {
      return _climateLogDao;
    } else {
      synchronized(this) {
        if(_climateLogDao == null) {
          _climateLogDao = new ClimateLogDao_Impl(this);
        }
        return _climateLogDao;
      }
    }
  }
}
