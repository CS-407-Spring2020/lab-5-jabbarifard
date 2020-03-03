package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //public static usernameKey = "username";

    public void clickFunction(View v) {
        Log.i("info", "Button Pressed!");
        EditText myTextField = (EditText) findViewById(R.id.EditText1);
        String str = myTextField.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", MODE_PRIVATE);
        sharedPreferences.edit().putString("username",str).apply();
        goToActivity2(str);
    }

    public void goToActivity2(String s){
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("message",s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(usernameKey, "").equals("")){
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        } else{
            setContentView(R.layout.activity_main);
        }
    }

}
