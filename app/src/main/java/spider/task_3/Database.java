package spider.task_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vignesh on 18-Jun-18.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DataBase="ToDo";
    public static final String Table="ToDoTable";
    public static  final String Column1="Number";
    public static final String Column2="Work";
    public static final String Column3="Date";
    public static final String Column4="Time";

    public Database(Context context) {
        super(context, DataBase, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Table +" (Number INTEGER PRIMARY KEY AUTOINCREMENT,Work TEXT,Date TEXT,Time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table);
        onCreate(sqLiteDatabase);
    }

    public long insertData(String Query,String Date,String Time){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Column2,Query);
        content.put(Column3,Date);
        content.put(Column4,Time);
        return sqLiteDatabase.insert(Table,null,content);
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor data=sqLiteDatabase.rawQuery("Select * from " + Table,null);
        return data;
    }

    public int deleteData(int Number){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(Table, "Number = ?",new String[]{String.valueOf(Number)});

    }

}
