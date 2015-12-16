package politikei.com.br.politikei.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.facebook.login.LoginManager;

import politikei.com.br.politikei.datatype.User;

public class DataBase extends SQLiteOpenHelper {
    //1 - Initial version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "politikei.db";

    //Tables
    private static final String TABLE_SETTINGS = "settings";

    //Player data
    private static final String SETTINGS_ID = "id";
    private static final String SETTINGS_USER = "user";
    private static final String SETTINGS_TOKEN = "token";
    private static final String SETTINGS_IS_FACEBOOK = "is_fb";
    private static final String SETTINGS_CREATE = "CREATE TABLE "
            + TABLE_SETTINGS + " ( "
            + SETTINGS_ID + " integer primary key autoincrement, "
            + SETTINGS_USER + " text UNIQUE not null, "
            + SETTINGS_TOKEN + " text, "
            + SETTINGS_IS_FACEBOOK + " integer );";

    private static DataBase mInstance = null;

    private DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(db);
    }

    public static  DataBase getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new DataBase(context);
        }
        return mInstance;
    }

    public void deleteEverything() {
        SQLiteDatabase sqlLite = getReadableDatabase();
        sqlLite.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(sqlLite);
        LoginManager.getInstance().logOut();
    }

    public boolean isAccountSet() {
        boolean response = false;

        SQLiteDatabase sqlLite = getReadableDatabase();

        Cursor cursor = sqlLite.query(TABLE_SETTINGS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            response = true;
        }

        if (cursor != null)
            cursor.close();

        return response;
    }

    public User getUserData() {
        User user = new User();

        SQLiteDatabase sqlLite = getReadableDatabase();

        Cursor cursor = sqlLite.query(TABLE_SETTINGS, new String[]{SETTINGS_USER, SETTINGS_TOKEN, SETTINGS_IS_FACEBOOK}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            user.setName(cursor.getString(cursor.getColumnIndex(SETTINGS_USER)));
            user.setToken(cursor.getString(cursor.getColumnIndex(SETTINGS_TOKEN)));
            if(cursor.getInt(cursor.getColumnIndex(SETTINGS_IS_FACEBOOK)) == 1)
                user.setForFacebook();
        }

        if (cursor != null)
            cursor.close();

        return user;
    }

    public boolean saveUserData(User user) {
        boolean result = false;

        SQLiteDatabase sqlLite = getReadableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(SETTINGS_USER, user.getName());
        initialValues.put(SETTINGS_TOKEN, user.getToken());
        initialValues.put(SETTINGS_IS_FACEBOOK, user.isFacebookAccount()?1:0);
        if(sqlLite.insert(TABLE_SETTINGS, null, initialValues) > 0) {
            result = true;
        }

        return result;
    }
}
