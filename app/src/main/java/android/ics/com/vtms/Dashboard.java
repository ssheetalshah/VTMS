package android.ics.com.vtms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.ics.com.vtms.Adapters.Vehichles_Adapter;
import android.ics.com.vtms.JAVA_files.Vehicles;
import android.ics.com.vtms.utils.AppPreference;
import android.ics.com.vtms.utils.Connectivity;
import android.ics.com.vtms.utils.SessionManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar_dash;
    AutoCompleteTextView vehsearch;
    RecyclerView vehrec;
    ArrayAdapter<String> City_adapter;
    private Vehichles_Adapter mAdapter;
    //   private Session_management sessionManagement;
    SessionManager manager;
    private String searchItems[] = {"None Found"};
    HashMap<Integer, String> City_map = new HashMap<>();
    ArrayList<Vehicles> Consult;
    ArrayList<String> List_by_city_consultant = new ArrayList<>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        vehsearch = (AutoCompleteTextView) findViewById(R.id.vehsearch);
        toolbar_dash = (Toolbar) findViewById(R.id.toolbar_dash);
        vehrec = findViewById(R.id.vehrec);
        toolbar_dash.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar_dash.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar_dash);

        Consult = new ArrayList<>();

        vehsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    vehrec.setAdapter(null);
                    mAdapter.notifyDataSetChanged();
                    Consult.clear();
                } catch (NullPointerException e) {
                    Log.e("adapter", "is null");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               try{
                   if (s != null) {
                       if (Connectivity.isNetworkAvailable(Dashboard.this)) {
                           new Get_the_city_vehichle(s.toString()).execute();
                           // Toast.makeText(Dashboard.this, "you have typed " + s, Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(Dashboard.this, "No Internet", Toast.LENGTH_SHORT).show();
                       }

                   } else {
                       Toast.makeText(Dashboard.this, "Please type city first", Toast.LENGTH_SHORT).show();
                   }

               }catch (Exception e){
                   System.out.print(e);
               }


                // consult_search.requestFocus();
                //  Toast.makeText(Search_Consultant.this, "" + s.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //   consult_search.requestFocus();

            }
        });

//        vehsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                vehrec.setAdapter(null);
//                mAdapter.notifyDataSetChanged();
//                Consult.clear();
//                return false;
//            }
//        });
        manager = new SessionManager(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent intent = new Intent(Dashboard.this, EditProfile.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent1 = new Intent(Dashboard.this, ChangePassword.class);
                startActivity(intent1);
                return true;
            case R.id.item3:
                manager.logoutUser();
                Intent intent2 = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //---------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class Get_the_city_vehichle extends AsyncTask<String, Void, String> {
        Object city_id;

        public Get_the_city_vehichle(Object o) {
            this.city_id = o;
        }

        protected void onPreExecute() {
            //  dialog = new ProgressDialog(AccountFormActivity.this);
            //  dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL("http://ihisaab.in/vtms/Api/search_data");
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("search", city_id);
                postDataParams.put("licence_no", AppPreference.getLicense(Dashboard.this));

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }


        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                //  dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {
                    Consult.clear();
                    jsonObject = new JSONObject(result);
                    String responce = jsonObject.getString("responce");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int k = 0; k < jsonArray.length(); k++) {
                        JSONObject jObj = jsonArray.getJSONObject(k);
                        String id = jObj.getString("id");
                        String VehicleDescription = jObj.getString("VehicleDescription");
                        Consult.add(new Vehicles(id, VehicleDescription));

                        mAdapter = new Vehichles_Adapter(Dashboard.this, Consult);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        vehrec.setLayoutManager(mLayoutManager);
                        vehrec.setItemAnimator(new DefaultItemAnimator());
                        vehrec.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }
//                    recyclerView1.setAdapter(null);


                    if (responce.equalsIgnoreCase("True")) {


                        Toast.makeText(Dashboard.this, "Success ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Dashboard.this, "Failed to retrieve", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
