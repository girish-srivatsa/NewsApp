package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
        Whole w=new Whole();
        try {
            String j = new Z().execute().get();
            Log.e("got",j);
            w=new Gson().fromJson(j,Whole.class);
            j=new Gson().toJson(w);
            Log.e("obj",j);
        }
        catch(InterruptedException e){
            Log.e("error",String.valueOf(e.getStackTrace()));
        }
        catch(ExecutionException e){
            Log.e("error",String.valueOf(e.getStackTrace()));
        }
    }
}
class Z extends AsyncTask<Void, Void, String>{
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
        try{
            Response response = client.newCall(request).execute();
            //Log.i("data",response.body().string());
            Log.i("data","data");
            return response.body().string();
        }
        catch(IOException e){
            Log.e("error", String.valueOf(e.getStackTrace()));
            return String.valueOf(e.getStackTrace());
        }
    }


    @Override
    protected void onPostExecute(String result) {

        Log.e("hi","hi");
        Log.e("hi",result);
    }
};
class Whole{
    String _type;
    String webSearchUrl;
    News[] value;
}
class News{
    String _type;
    String name;
    String url;
    Img image;
    String description;
    Org[] provider;
    String datePublished;
}
class Img{
    String _type;
    Img2 thumbnail;
    Boolean isLicensed;
}
class Img2{
    String _type;
    String contentUrl;
    Integer width;
    Integer height;
}
class Org{
    String _type;
    String name;
    Img image;
}