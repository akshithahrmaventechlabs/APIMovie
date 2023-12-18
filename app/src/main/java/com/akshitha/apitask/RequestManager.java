package com.akshitha.apitask;

import android.content.Context;
import android.widget.Toast;

import com.akshitha.apitask.Listeners.OnDetailsApiListener;
import com.akshitha.apitask.Listeners.OnSearchApiListener;
import com.akshitha.apitask.Models.DetailApiResponse;
import com.akshitha.apitask.Models.SearchApiResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    OkHttpClient httpClient = new OkHttpClient.Builder()
            //here we can add Interceptor for dynamical adding headers
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("test", "test").build();
                    return chain.proceed(request);
                }
            })
            //here we adding Interceptor for full level logging
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void searchMovies(OnSearchApiListener listener, String movie_name) {
        getMovies getMovies = retrofit.create(RequestManager.getMovies.class);
        Call<SearchApiResponse> call = getMovies.callMovies(movie_name, " ");

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "couldn't fetch Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });
    }

    public void searchMovieDetails(OnDetailsApiListener listener, String movie_id) {
        getMovieDetails getMovieDetails = retrofit.create(RequestManager.getMovieDetails.class);
        Call<DetailApiResponse> call = getMovieDetails.callMovieDetails(movie_id);

        call.enqueue(new Callback<DetailApiResponse>() {
            @Override
            public void onResponse(Call<DetailApiResponse> call, Response<DetailApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "couldn't fetch Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DetailApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });
    }


    public interface getMovies {
        @Headers({
                "Accept: application/json",
                "api_key: 626ac90eafd973bdc15134b6595350a3",

        })

        @GET("3/movie/popular")
        Call<SearchApiResponse> callMovies(
                @Query("query") String movie_name,
                @Query("api_key") String api_key
        );
    }


    public interface getMovieDetails {
        @Headers({
                "end_point:/movie/popular",
                "Accept: application/json",
                "api_key: 6b8db85ce1e45beacf91815f5643cd76",

        })
        @GET("film/{movie_id}")
        Call<DetailApiResponse> callMovieDetails(
                @Path("movie_id") String movie_id
        );
    }
}
