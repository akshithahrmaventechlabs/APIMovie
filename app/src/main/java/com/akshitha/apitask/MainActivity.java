package com.akshitha.apitask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.akshitha.apitask.Adapters.HomeRecyclerAdapter;
import com.akshitha.apitask.Listeners.OnMovieClickListener;
import com.akshitha.apitask.Listeners.OnSearchApiListener;
import com.akshitha.apitask.Models.SearchApiResponse;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {
    SearchView search_view;
    RecyclerView recycler_view_home;
    HomeRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_view = findViewById(R.id.search_view);
        recycler_view_home = findViewById(R.id.recycler_view_home);
        manager = new RequestManager(this);
        dialog = new ProgressDialog(this);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Please wait...");
                dialog.show();
                manager.searchMovies(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Home Button
        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Home button clicked", Toast.LENGTH_SHORT).show();

            }
        });

        // Popular Button
        findViewById(R.id.btn_popular).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Popular button clicked", Toast.LENGTH_SHORT).show();

            }
        });

        // Search Button
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Search button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final OnSearchApiListener listener = new OnSearchApiListener() {
        @Override
        public void onResponse(SearchApiResponse response) {
            dialog.dismiss();
            if (response == null) {
                Toast.makeText(MainActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(SearchApiResponse response) {
        recycler_view_home.setHasFixedSize(true);
        recycler_view_home.setLayoutManager(new GridLayoutManager(MainActivity.this, 5));
        adapter = new HomeRecyclerAdapter(this, response.getResults(), this);
        recycler_view_home.setAdapter(adapter);
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", id));
    }
}