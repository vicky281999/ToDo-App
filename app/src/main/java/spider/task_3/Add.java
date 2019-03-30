package spider.task_3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Add extends AppCompatActivity {

    Database mdb;
    int d,m,y,h,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mdb=new Database(this);

        Button Date=(Button)findViewById(R.id.Date_btn);
        Date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                d=calendar.get(Calendar.DAY_OF_MONTH);
                m=calendar.get(Calendar.MONTH);
                y=calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog=new DatePickerDialog(Add.this,new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
                        TextView Date=(TextView) findViewById(R.id.tt2);
                        if(y<i){
                            Date.setText(i2+"/"+(i1+1)+"/"+i);
                        }
                        else if(y==i){
                            if(m<i1){
                                Date.setText(i2+"/"+(i1+1)+"/"+i);
                            }
                            else if(m==i1){
                                if (d<=i2){
                                    Date.setText(i2+"/"+(i1+1)+"/"+i);
                                }
                                else
                                {
                                    Toast.makeText(Add.this,"Enter a valid date",Toast.LENGTH_LONG).show();
                                    Date.setText("..................");
                                }
                            }
                            else
                            {
                                Toast.makeText(Add.this,"Enter a valid date",Toast.LENGTH_LONG).show();
                                Date.setText("..................");
                            }
                        }
                        else
                        {
                            Toast.makeText(Add.this,"Enter a valid date",Toast.LENGTH_LONG).show();
                            Date.setText("..................");
                        }
                    }
                },y,m,d);
                pickerDialog.show();
            }
        });

        Button Time=(Button)findViewById(R.id.Time_btn);
        Time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                h=calendar.get(Calendar.HOUR_OF_DAY);
                min=calendar.get(Calendar.MINUTE);
                TimePickerDialog pickerDialog=new TimePickerDialog(Add.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        TextView Time=(TextView)findViewById(R.id.tt4);
                        Time.setText(i+":"+i1);
                    }
                },h,min,true);
                pickerDialog.show();
            }

        });

        Button btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et1=(EditText)findViewById(R.id.et1);
                TextView tt2=(TextView)findViewById(R.id.tt2);
                TextView tt4=(TextView) findViewById(R.id.tt4);
                String Task=et1.getText().toString();
                String Date=tt2.getText().toString();
                String Time=tt4.getText().toString();
                if(Task.length()!=0&&Date!=".................."&&Time!=".................."){
                    long l=mdb.insertData(Task,Date,Time);
                    if(l>0) {
                        ListAppWidget.updateAppWidget();
                        Toast.makeText(Add.this, "Task saved", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Add.this, "oops!,Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(Add.this,"All the fields are mandatory",Toast.LENGTH_LONG).show();
                et1.setText(" ");
                tt2.setText("..................");
                tt4.setText("..................");
            }
        });
    }

}
