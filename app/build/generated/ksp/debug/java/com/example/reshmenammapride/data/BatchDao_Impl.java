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
public final class BatchDao_Impl implements BatchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SilkBatch> __insertionAdapterOfSilkBatch;

  private final EntityDeletionOrUpdateAdapter<SilkBatch> __deletionAdapterOfSilkBatch;

  public BatchDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSilkBatch = new EntityInsertionAdapter<SilkBatch>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `silkworm_batches` (`id`,`batchName`,`breed`,`startDate`,`stage`,`createdAt`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SilkBatch entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBatchName());
        statement.bindString(3, entity.getBreed());
        statement.bindString(4, entity.getStartDate());
        statement.bindString(5, entity.getStage());
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfSilkBatch = new EntityDeletionOrUpdateAdapter<SilkBatch>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `silkworm_batches` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SilkBatch entity) {
        statement.bindString(1, entity.getId());
      }
    };
  }

  @Override
  public Object insertBatch(final SilkBatch batch, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSilkBatch.insert(batch);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBatch(final SilkBatch batch, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSilkBatch.handle(batch);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SilkBatch>> observeBatches() {
    final String _sql = "SELECT * FROM silkworm_batches ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"silkworm_batches"}, new Callable<List<SilkBatch>>() {
      @Override
      @NonNull
      public List<SilkBatch> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchName = CursorUtil.getColumnIndexOrThrow(_cursor, "batchName");
          final int _cursorIndexOfBreed = CursorUtil.getColumnIndexOrThrow(_cursor, "breed");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfStage = CursorUtil.getColumnIndexOrThrow(_cursor, "stage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<SilkBatch> _result = new ArrayList<SilkBatch>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SilkBatch _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBatchName;
            _tmpBatchName = _cursor.getString(_cursorIndexOfBatchName);
            final String _tmpBreed;
            _tmpBreed = _cursor.getString(_cursorIndexOfBreed);
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpStage;
            _tmpStage = _cursor.getString(_cursorIndexOfStage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new SilkBatch(_tmpId,_tmpBatchName,_tmpBreed,_tmpStartDate,_tmpStage,_tmpCreatedAt);
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
