package ru.nsu.fit.lesson12;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.nsu.fit.lesson12.db.StudentDatabase;

public class StudentsContentProvider extends ContentProvider {

    private static final int ALL = 1;
    private static final int BY_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        String authority = "ru.nsu.fit.lesson12.provider";
        uriMatcher.addURI(authority, "student", ALL);
        uriMatcher.addURI(authority, "student/#", BY_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final StudentDatabase db = App.getInstance().getDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL:
                return db.studentDao().selectAllProvider();
            case BY_ID:
                return db.studentDao().selectByIds(new int[]{Integer.parseInt(uri.getLastPathSegment())});
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
