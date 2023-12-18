package com.akshitha.apitask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akshitha.apitask.Listeners.OnDetailsApiListener;
import com.akshitha.apitask.Models.DetailApiResponse;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
 TextView textView_movie_plot, textView_movie_name,textView_movie_released,textView_movie_runtime,textView_movie_rating,textView_movie_votes;
 ImageView imageView_movie_poster;
 RecyclerView recycler_movie_cast;

 RequestManager manager;
 ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView_movie_plot=findViewById(R.id.textView_movie_plot);
        textView_movie_name=findViewById(R.id.textView_movie_name);
        textView_movie_released=findViewById(R.id.textView_movie_released);
        textView_movie_runtime=findViewById(R.id.textView_movie_runtime);
        textView_movie_rating=findViewById(R.id.textView_movie_rating);
        textView_movie_votes=findViewById(R.id.textView_movie_votes);
        imageView_movie_poster=findViewById(R.id.imageView_movie_poster);
        recycler_movie_cast=findViewById(R.id.recycler_movie_cast);

        manager=new RequestManager(this);
        String movie_id=getIntent().getStringExtra("data");

        dialog =new ProgressDialog(this);
        dialog.setTitle("Please wait....");
        dialog.show();
        manager.searchMovieDetails(listener,movie_id);

    }
    private OnDetailsApiListener listener=new OnDetailsApiListener() {
        @Override
        public void onResponse(DetailApiResponse response){
            dialog.dismiss();
            if(response.equals(null)){
            Toast.makeText(DetailsActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
            return;
        }
        showResutls(response);
        }

        private void showResutls(DetailApiResponse response) {
            textView_movie_name.setText(response.getOriginal_title());
            textView_movie_released.setText("Yea Released:"+response.getRelease_date());
            textView_movie_runtime.setText("Length"+response.getOverview());
            textView_movie_rating.setText("Rating"+response.getPopularity());
            textView_movie_votes.setText(response.getVote_count()+"Votes");
            textView_movie_plot.setText(response.getPoster_path());

            try {
                Picasso.get().load(response.getPoster_path()).into(imageView_movie_poster);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailsActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
        }
    };
}