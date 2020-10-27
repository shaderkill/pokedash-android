package com.example.poekedexapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private EditText userText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userText = (EditText) findViewById(R.id.user_text);
        passwordText = (EditText) findViewById(R.id.pwd_text);
    }

    /**
     * On click.
     *
     * @param view the view
     */
    public void onClick(View view) {
        String user = userText.getText().toString();
        String password = userText.getText().toString();

        if(!user.isEmpty() && !password.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario y/o Contraseña no ingresado(s)", Toast.LENGTH_SHORT).show();
        }
    }
}