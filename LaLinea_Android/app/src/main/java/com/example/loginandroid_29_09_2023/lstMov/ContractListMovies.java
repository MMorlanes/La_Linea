package com.example.loginandroid_29_09_2023.lstMov;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.User;

import java.util.ArrayList;

public interface ContractListMovies {
    public interface Presenter {
        void lstMovies(String filtro);
    }

    public interface Model {
        void moviesAPI(String filtro, OnLstMoviesListener respuestaMovies);

        interface OnLstMoviesListener {
            void onFinished(ArrayList<Pelicula> lstPelicula);
            void onFailure(String err);
        }

        void updateMovieTitle(int movieId, String newTitle, OnUpdateMovieListener listener);

        interface OnUpdateMovieListener {
            void onUpdateSuccess();
            void onFailure(String err);
        }
    }

    public interface View {
        void successMovies(ArrayList<Pelicula> lstPelicula);
        void failureMovies(String err);

        // Agrega estos métodos para manejar el éxito y el fracaso de la actualización
        void showUpdateSuccess();
        void showUpdateFailure(String err);
    }
}

