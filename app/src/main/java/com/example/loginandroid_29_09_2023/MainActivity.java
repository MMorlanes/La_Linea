package com.example.loginandroid_29_09_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginandroid_29_09_2023.lstMov.model.LstMoviesModel;
import com.example.loginandroid_29_09_2023.lstMov.view.LstMovies;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_movies4);

        button = findViewById(R.id.button_update_title);
        button.setOnClickListener(v -> {
            String newTitle = ((EditText) findViewById(R.id.edit_movie_title)).getText().toString();
            int movieId = 1; // Obtén el ID de la película que quieres actualizar.

            LstMoviesModel lstMoviesModel = new LstMoviesModel(null); // Pasa el Presenter si es necesario
            lstMoviesModel.updateMovieTitle(movieId, newTitle, new ContractListMovies.Model.OnUpdateMovieListener() {
                @Override
                public void onUpdateSuccess() {
                    // Maneja el éxito
                }

                @Override
                public void onFailure(String err) {
                    // Maneja el error
                }
            });
        });

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(MainActivity.this, LstMovies.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
