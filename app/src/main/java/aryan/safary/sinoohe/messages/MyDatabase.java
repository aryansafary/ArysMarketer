package aryan.safary.sinoohe.messages;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {


    public MyDatabase(@Nullable Context context) {
        super(context, "MyDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table messages(id integer primary key autoincrement,sender text,message text,date text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<MessageModel> select(){

        SQLiteDatabase db = getReadableDatabase();
        List<MessageModel> list = new ArrayList<>();

        Cursor c = db.rawQuery("select * from messages order by id DESC",null);

        if(c.moveToFirst()){
            do{
                @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
                @SuppressLint("Range") String date = c.getString(c.getColumnIndex("date"));
                @SuppressLint("Range") String sender = c.getString(c.getColumnIndex("sender"));
                @SuppressLint("Range") String message = c.getString(c.getColumnIndex("message"));
                MessageModel model = new MessageModel(id,sender,message,date);
                list.add(model);


            }while (c.moveToNext());



        }
        c.close();
        return list;

    }

    public void insert(MessageModel model){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("date",model.getDate());
        contentValues.put("sender",model.getSender());
        contentValues.put("message",model.getMessage());

        db.insert("messages",null,contentValues);
    }

}
