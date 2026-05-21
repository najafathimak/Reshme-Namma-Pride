package com.example.reshmenammapride.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ClimateLogDao_Impl implements ClimateLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ClimateLog> __insertionAdapterOfClimateLog;

  private final EntityDeletionOrUpdateAdapter<ClimateLog> __deletionAdapterOfClimateLog;

  public ClimateLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfClimateLog = new EntityInsertionAdapter<ClimateLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `climate_logs` (`id`,`batchId`,`batchName`,`temperature`,`humidity`,`stage`,`status`,`advice`,`loggedAt`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClimateLog entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBatchId());
        statement.bindString(3, entity.getBatchName());
        statement.bindDouble(4, entity.getTemperature());
        statement.bindDouble(5, entity.getHumidity());
        statement.bindString(6, entity.getStage());
        statement.bindString(7, entity.getStatus());
        statement.bindString(8, entity.getAdvice());
        statement.bindLong(9, entity.getLoggedAt());
      }
    };
    this.__deletionAdapterOfClimateLog = new EntityDeletionOrUpdateAdapter<ClimateLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `climate_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClimateLog entity) {
        statement.bindString(1, entity.getId());
      }
    };
  }

  @Override
  public Object insertLog(final ClimateLog log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfClimateLog.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLog(final ClimateLog log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfClimateLog.handle(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ClimateLog>> observeLogs() {
    final String _sql = "SELECT * FROM climate_logs ORDER BY loggedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"climate_logs"}, new Callable<List<ClimateLog>>() {
      @Override
      @NonNull
      public List<ClimateLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfBatchName = CursorUtil.getColumnIndexOrThrow(_cursor, "batchName");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfStage = CursorUtil.getColumnIndexOrThrow(_cursor, "stage");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAdvice = CursorUtil.getColumnIndexOrThrow(_cursor, "advice");
          final int _cursorIndexOfLoggedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "loggedAt");
          final List<ClimateLog> _result = new ArrayList<ClimateLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClimateLog _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBatchId;
            _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            final String _tmpBatchName;
            _tmpBatchName = _cursor.getString(_cursorIndexOfBatchName);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final float _tmpHumidity;
            _tmpHumidity = _cursor.getFloat(_cursorIndexOfHumidity);
            final String _tmpStage;
            _tmpStage = _cursor.getString(_cursorIndexOfStage);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpAdvice;
            _tmpAdvice = _cursor.getString(_cursorIndexOfAdvice);
            final long _tmpLoggedAt;
            _tmpLoggedAt = _cursor.getLong(_cursorIndexOfLoggedAt);
            _item = new ClimateLog(_tmpId,_tmpBatchId,_tmpBatchName,_tmpTemperature,_tmpHumidity,_tmpStage,_tmpStatus,_tmpAdvice,_tmpLoggedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ClimateLog>> observeLogsForBatch(final String batchId) {
    final String _sql = "SELECT * FROM climate_logs WHERE batchId = ? ORDER BY loggedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, batchId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"climate_logs"}, new Callable<List<ClimateLog>>() {
      @Override
      @NonNull
      public List<ClimateLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfBatchName = CursorUtil.getColumnIndexOrThrow(_cursor, "batchName");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfStage = CursorUtil.getColumnIndexOrThrow(_cursor, "stage");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAdvice = CursorUtil.getColumnIndexOrThrow(_cursor, "advice");
          final int _cursorIndexOfLoggedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "loggedAt");
          final List<ClimateLog> _result = new ArrayList<ClimateLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClimateLog _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBatchId;
            _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            final String _tmpBatchName;
            _tmpBatchName = _cursor.getString(_cursorIndexOfBatchName);
            final float _tmpTemperature;
            _tmpTemperature = _cursor.getFloat(_cursorIndexOfTemperature);
            final float _tmpHumidity;
            _tmpHumidity = _cursor.getFloat(_cursorIndexOfHumidity);
            final String _tmpStage;
            _tmpStage = _cursor.getString(_cursorIndexOfStage);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpAdvice;
            _tmpAdvice = _cursor.getString(_cursorIndexOfAdvice);
            final long _tmpLoggedAt;
            _tmpLoggedAt = _cursor.getLong(_cursorIndexOfLoggedAt);
            _item = new ClimateLog(_tmpId,_tmpBatchId,_tmpBatchName,_tmpTemperature,_tmpHumidity,_tmpStage,_tmpStatus,_tmpAdvice,_tmpLoggedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}



