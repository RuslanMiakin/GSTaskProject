package mr.bel.testtaskgorastudiomiakinruslan;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static mr.bel.testtaskgorastudiomiakinruslan.NetworkConnectUtils.NetworkConnect.generalURLAlbums;
import static mr.bel.testtaskgorastudiomiakinruslan.NetworkConnectUtils.NetworkConnect.generalURLImages;
import static mr.bel.testtaskgorastudiomiakinruslan.NetworkConnectUtils.NetworkConnect.getResponseFromURL;

public class FullDataUsers extends AppCompatActivity {

    private ProgressBar progressBar;
    public static final String EXTRA_INTENT = "gorastudio";
    public String idUserClick;
    private URL AlbumId;
    private List<String> GetAlbumsIdForClickedUser;
    private List<String> titlesArray; private ArrayList<Bitmap> ImagesArray;
    private AdapterReciclerView adapter;
    private Boolean runing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_data_users);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        runing = true;
        Intent intent = getIntent();
        progressBar = findViewById(R.id.marker_progress);
        idUserClick = intent.getStringExtra(EXTRA_INTENT);
        AlbumId = generalURLAlbums(idUserClick);
        titlesArray = new ArrayList();
        ImagesArray = new ArrayList();
        GetAlbumsIdForClickedUser = new LinkedList<>();
        ReciclerData();
        new GORATaskAlbomImage().execute(AlbumId);
    }

    public void ReciclerData(){
        RecyclerView Recycler = findViewById(R.id.reciclerview_data_full);
        adapter = new AdapterReciclerView(getApplicationContext(),titlesArray, ImagesArray);
        Recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        Recycler.setLayoutManager(layoutManager);}

private class GORATaskAlbomImage extends AsyncTask<URL,Void,String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
                JSONArray ja = new JSONArray(response);
                for (int i = 0; i < ja.length(); i++) {

                    String AlbumId = ja.getJSONObject(i).getString("id");
                    GetAlbumsIdForClickedUser.add(AlbumId);
                }

                for (int i = 0; i < GetAlbumsIdForClickedUser.size(); i++) {
                    if(!runing) return response;
                    URL UrlAlbumId = generalURLImages(GetAlbumsIdForClickedUser.get(i));
                    response = getResponseFromURL(UrlAlbumId);
                    System.out.println("Наш аррей" + response);
                    JSONArray jas = new JSONArray(response);

                    for (int j = 0; j < jas.length(); j++) {
                        String titleImg = jas.getJSONObject(j).getString("title");
                        String urlImg = jas.getJSONObject(j).getString("url");
                        URL obj = new URL(urlImg);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("User-Agent", "My Example");
                        InputStream in = con.getInputStream();
                        Bitmap dw = BitmapFactory.decodeStream(in);

                        ImagesArray.add(dw);
                        titlesArray.add(titleImg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setData(titlesArray, ImagesArray);
                            }
                        });
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.INVISIBLE);
    }
}

    @Override
    protected void onDestroy() {
        runing = false;
        super.onDestroy();
    }
}



