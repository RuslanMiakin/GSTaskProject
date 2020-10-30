package mr.bel.testtaskgorastudiomiakinruslan;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static mr.bel.testtaskgorastudiomiakinruslan.NetworkConnectUtils.NetworkConnect.generalURLUsers;
import static mr.bel.testtaskgorastudiomiakinruslan.NetworkConnectUtils.NetworkConnect.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    private ListView UsersFullName;
    private List<String> UsersListView;
    private List<String> IdUsers;
    private static URL getUser = generalURLUsers();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsersListView = new LinkedList<>();
        IdUsers = new ArrayList<>();
        UsersFullName = findViewById(R.id.listview_nameid);
        new GORATaskUsers().execute(getUser);
        UsersFullName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userId = IdUsers.get(position);
                Intent intent = new Intent(getApplicationContext(),FullDataUsers.class);
                intent.putExtra(FullDataUsers.EXTRA_INTENT,userId);
                startActivity(intent);
            }
        });
    }
   private class GORATaskUsers extends AsyncTask<URL,Void,String> {
        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        @SuppressLint("DefaultLocale")
        protected void onPostExecute(String response){
            try {
                JSONArray ja = new JSONArray(response);
                for (int i = 0; i < ja.length(); i++)
                {
                    String id = ja.getJSONObject(i).getString("id");
                    String name = ja.getJSONObject(i).getString("name");
                    UsersListView.add(name);
                    IdUsers.add(id);
                }
                adapter = new ArrayAdapter<>
                        (getApplicationContext(),android.R.layout.simple_list_item_1,UsersListView);
                UsersFullName.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }}}
