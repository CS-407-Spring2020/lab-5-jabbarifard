package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    public static ArrayList<Note> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //1.display welcome message. Fetch username from sharedreferences.
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        //Intent intent = getIntent();//use sharedPreferences
        //String str = intent.getStringExtra("message");
        textView2.setText("Welcome "+ username+ "!");
        //2.Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",context.MODE_PRIVATE, null);
        //3.Initiate the "notes" class variable using readNotes method implemented in c.sakshi.lab5.DBHelper class. Use the username
        //you got from SharedPreferences as a parameter to readNotes method.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);
        //4.Create an ArrayList<String> object by itereating over notes object.
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }
        //5.Use ListView view to display notes on screen.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //6. add onItemClickListener for ListView item, a note in our case.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //Initialise intent to take user to third activity (NoteActivity in this case).
            Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
            //Add the position of the item that was clicked on as "noteid".
            intent.putExtra("noteid", position);
            startActivity(intent);
        }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.item3){
            //Erase username from shared preferences.
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.item2){
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
            return true;
        }
//        switch(item.getItemId()) {
//            case R.id.item1:
//                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item2:
//                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
//                goToActivity3();
//                return true;
//            case R.id.item3:
//                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
//                goToActivity();
//                return true;
//
//            default: return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }

    public void goToActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToActivity3(){
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }
}
