package spider.task_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete extends AppCompatActivity {

    Database mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        mdb=new Database(this);

        Button btn4=(Button)findViewById(R.id.btn1);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et1=(EditText)findViewById(R.id.et1);
                String Text=et1.getText().toString();
                if(Text.length()!=0) {
                    int DeltedRows=mdb.deleteData(Integer.parseInt(Text));
                    ListAppWidget.updateAppWidget();
                    if (DeltedRows>0)
                        Toast.makeText(Delete.this,"Task Deleted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Delete.this,"oops!,something went wrong",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Delete.this,"Enter something in the Textfield",Toast.LENGTH_LONG).show();
                et1.setText("");
            }
        });
    }
}
