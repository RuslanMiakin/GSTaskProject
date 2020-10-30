package mr.bel.testtaskgorastudiomiakinruslan.NetworkConnectUtils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkConnect {
    private static final String API_BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String GET_USERS = "/users";
    private static final String GET_ALBUMS = "/albums";
    private static final String GET_ALBUMS_ID = "userId";
    private static final String GET_IMAGE = "/photos";
    private static final String GET_IMAGE_ID = "albumId";

    public static URL generalURLUsers(){
        Uri builtUri = Uri.parse(API_BASE_URL+GET_USERS).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL generalURLAlbums(String id){
        Uri builtUri = Uri.parse(API_BASE_URL+GET_ALBUMS).buildUpon().appendQueryParameter(GET_ALBUMS_ID,id).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL generalURLImages(String id){
        Uri builtUri = Uri.parse(API_BASE_URL+GET_IMAGE).buildUpon().appendQueryParameter(GET_IMAGE_ID,id).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }}}
