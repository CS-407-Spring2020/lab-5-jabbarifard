package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //1.get EditText view.
        EditText textView2 = (EditText) findViewById(R.id.editText2);
        //2.get Intent.
        //Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        Intent intent = getIntent();
        //3.get the value of integer "noteid" from intent.
        noteid = intent.getIntExtra("noteid",-1);
        //startActivity(intent);
        //4.initialize class variable "noteid" with the value from intent.
        if(noteid != -1){
            //display content of note by retrieving "notes" ArrayList in SecondActivity.
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            //use editText.setText() to display the contents of this note on screen.
            EditText editText = (EditText) findViewById(R.id.editText2);
            editText.setText(noteContent);
        }
    }

//    public void onClick(){
//
//
//    }
    public void savedMethod(View view){
        //1.get editText view and the content that user entered.
        String usernameKey = "username";
        //2.initialise SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",context.MODE_PRIVATE, null);
        //3.initialise c.sakshi.lab5.DBHelper class.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        //4.set username in the following variable by fetching it from SharedPreferences.
        //String username = "<get from shared preferences>";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        //5.save information to database.
        String title;
        EditText textView2 = (EditText) findViewById(R.id.editText2);
        String content = textView2.getText().toString();
        Log.i("DEBUG*****",content);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1) {//add note.
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            Log.i("DEBUG11111*****",content);
            dbHelper.saveNotes(username, title, content, date);
        } else {//update note.
            title = "NOTE_" + (noteid + 1);
            Log.i("DEBUG222222*****",content);
            Log.i("noteid*****",title);
            Log.i("username*****",username);
            dbHelper.updateNote(title, date, content, username);
        }
        //6.go to second activity using intents.
        goToActivity2();
    }
    public void goToActivity2(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
