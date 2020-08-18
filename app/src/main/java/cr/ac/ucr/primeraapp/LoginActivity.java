package cr.ac.ucr.primeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.ucr.primeraapp.utils.AppPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etPassword;

    public static final String PREFERENCES = "myapp_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_goto_register:
                gotoRegister();
                break;
            default:
                break;
        }
    }

    public void login(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.requiered_email));
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.requiered_password));
            return;
        }
        //TODO: Se tiene que sustituir con la lógica de la autentificación de la aplicación
        if(email.equalsIgnoreCase("admin@email.com") && password.equalsIgnoreCase("123")) {
            //TODO: Enviarlo al main activiy
            // TODO: almacenar en el storage el usuario logueado

            AppPreferences.getInstance(this).put(AppPreferences.keys.IS_LOGGED_IN, true);

            Toast.makeText(this, getString(R.string.logged), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, getString(R.string.error_match), Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}