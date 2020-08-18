package cr.ac.ucr.primeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import cr.ac.ucr.primeraapp.utils.AppPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean isLoggedIn = AppPreferences.getInstance(this).getBoolean(AppPreferences.keys.IS_LOGGED_IN, false);
        Intent intent;

        if(isLoggedIn){
            intent = new Intent(this, MainActivity.class);
        }
        else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);

        // se usa solo si no quiero volver a esta activite (al usar el atras)
        finish();
    }
}