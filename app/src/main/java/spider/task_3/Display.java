package spider.task_3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Display extends AppCompatActivity {

    ListView listView;
    Database db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listView = (ListView) findViewById(R.id.list);
        db = new Database(this);
        ArrayList<String> list=new ArrayList<>();
        cursor = db.getData();
        if(cursor.getCount()==0){
            Toast.makeText(Display.this,"No Data",Toast.LENGTH_LONG).show();
        }
        else{
            while(cursor.moveToNext()){
                list.add("Task:"+ cursor.getString(0) + "\n" +cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(1));
                ListAdapter listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(listAdapter);
            }
        }

    }
}
