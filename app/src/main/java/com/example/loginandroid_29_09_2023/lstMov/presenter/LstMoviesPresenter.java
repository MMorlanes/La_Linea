package com.example.loginandroid_29_09_2023.lstMov.presenter;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.model.LstMoviesModel;

import java.util.ArrayList;

public class LstMoviesPresenter implements ContractListMovies.Presenter,
                                        ContractListMovies.Model.OnLstMoviesListener{

    private ContractListMovies.View vista;
    private LstMoviesModel lstMoviesModel;

    public LstMoviesPresenter(ContractListMovies.View vista){
        this.vista = vista;
        lstMoviesModel = new LstMoviesModel(this);
    }
    @Override
    public void lstMovies(String filtro) {
        lstMoviesModel.moviesAPI("", this);
    }

    @Override
    public void onFinished(ArrayList<Pelicula> lstPelicula) {
        vista.successMovies(lstPelicula);
    }

    @Override
    public void onFailure(String err) {

    }

    public void updateMovieTitle(int movieId, String newTitle) {
        lstMoviesModel.updateMovieTitle(movieId, newTitle, new ContractListMovies.Model.OnUpdateMovieListener() {
            @Override
            public void onUpdateSuccess() {
                vista.showUpdateSuccess();
            }

            @Override
            public void onFailure(String err) {
                vista.showUpdateFailure(err);
            }
        });
    }

}
