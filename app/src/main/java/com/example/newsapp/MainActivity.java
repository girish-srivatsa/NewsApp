package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.google.gson.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Whole w = new Whole();
        try {
            String j = new Z().execute().get();
            Log.e("got", j);
            w = new Gson().fromJson(j, Whole.class);
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
            RVAdapter recyclerViewAdapter = new RVAdapter(Arrays.asList(w.value));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
            j = new Gson().toJson(w);
            Log.e("obj", j);
        } catch (InterruptedException e) {
            Log.e("error", String.valueOf(e.getStackTrace()));
        } catch (ExecutionException e) {
            Log.e("error", String.valueOf(e.getStackTrace()));
        }
    }
}

class RVAdapter extends RecyclerView.Adapter<RVAdapter.NewsViewHolder> {
    List<News> news;

    RVAdapter(List<News> persons) {
        this.news = persons;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        NewsViewHolder pvh = new NewsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        String text = '"' + news.get(i).name + '"';
        NewsViewHolder.newsTitle.setText(text);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView newsTitle;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            newsTitle = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}

class Z extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://bing-news-search1.p.rapidapi.com/news?safeSearch=Off&textFormat=Raw")
                .get()
                .addHeader("x-bingapis-sdk", "true")
                .addHeader("x-rapidapi-key", "5be6f2b8e3msh38c3cc89dc461b5p1c7110jsnd243442900ea")
                .addHeader("x-rapidapi-host", "bing-news-search1.p.rapidapi.com")
                .build();
        try {
            Response response = client.newCall(request).execute();
            //Log.i("data",response.body().string());
            Log.i("data", "data");
            return response.body().string();
        } catch (IOException e) {
            Log.e("error", String.valueOf(e.getStackTrace()));
            return String.valueOf(e.getStackTrace());
        }
    }


    @Override
    protected void onPostExecute(String result) {

        Log.e("hi", "hi");
        Log.e("hi", result);
    }
};

class Whole {
    String _type;
    String webSearchUrl;
    News[] value;
}

class News {
    String _type;
    String name;
    String url;
    Img image;
    String description;
    Org[] provider;
    String datePublished;
}

class Img {
    String _type;
    Img2 thumbnail;
    Boolean isLicensed;
}

class Img2 {
    String _type;
    String contentUrl;
    Integer width;
    Integer height;
}

class Org {
    String _type;
    String name;
    Img image;
}