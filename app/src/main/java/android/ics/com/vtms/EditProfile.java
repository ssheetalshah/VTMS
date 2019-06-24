package android.ics.com.vtms;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.ics.com.vtms.utils.Connectivity;
import android.ics.com.vtms.utils.SessionManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class EditProfile extends AppCompatActivity {
    EditText uName, uPasssword, uMobile;
    String UName, UPasssword, UMobile;
    Button bt_Upd;
    Toolbar toolbar_edt;
    SessionManager manager;
    String sUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar_edt = (Toolbar) findViewById(R.id.toolbar_edt);
        toolbar_edt.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar_edt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        manager = new SessionManager(this);
        sUser = manager.getUserID();

        uName = (EditText) findViewById(R.id.uName);
        uPasssword = (EditText) findViewById(R.id.uPasssword);
        uMobile = (EditText) findViewById(R.id.uMobile);

        bt_Upd = (Button) findViewById(R.id.bt_Upd);
        bt_Upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UName = uName.getText().toString();
                UMobile = uMobile.getText().toString();
                UPasssword = uPasssword.getText().toString();

                if (!UName.equals("")) {
                    if (!UMobile.equals("")) {
                        if (!UPasssword.equals("")) {
                            if (Connectivity.isNetworkAvailable(EditProfile.this)) {
                                new PostData().execute();
                            } else {
                                Toast.makeText(EditProfile.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            uPasssword.setError("Please Enter Password");
                            uPasssword.requestFocus();
                        }
                    } else {
                        uMobile.setError("Please Enter Password");
                        uMobile.requestFocus();
                    }
                } else {
                    uName.setError("Please Enter User Id");
                    uName.requestFocus();
                }
            }
        });

        if (Connectivity.isNetworkAvailable(this)) {
            new GetData().execute();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    //------------------------------------------------------------

    public class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(EditProfile.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://www.ihisaab.in/vtms/Api/getprofile");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login_uiserid", sUser);

                Log.e("sUser", sUser);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
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

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                String s = result.toString();
                try {
                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    if (response.equals("true")) {
                        JSONObject userdataObj = jsonObject.getJSONObject("userdata");
                        String login_name = userdataObj.getString("login_name");
                        String login_mobile1 = userdataObj.getString("login_mobile1");
                        String login_password = userdataObj.getString("login_password");
                        uName.setText(login_name);
                        uPasssword.setText(login_password);
                        uMobile.setText(login_mobile1);
                    } else {
                        Toast.makeText(EditProfile.this, "Oops! Some Problem", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    }

    //------------------------------------------------------------

    public class PostData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(EditProfile.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://www.ihisaab.in/vtms/Api/profile_update");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login_uiserid", sUser);
                postDataParams.put("login_name", UName);
                postDataParams.put("login_mobile1", UMobile);
                postDataParams.put("login_password", UPasssword);

                Log.e("sUser", sUser);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
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

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                String s = result.toString();
                try {
                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    if (response.equals("true")) {
                        String msg = jsonObject.getString("msg");
                        String userdata = jsonObject.getString("userdata");

                        AlertDialogCreate(jsonObject.getString("msg"));

                    } else {
                        Toast.makeText(EditProfile.this, "Oops! Some Problem", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    }

    //------------------------------------------------------------

    public void AlertDialogCreate(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this, R.style.AlertDialogTheme);
        builder.setIcon(R.drawable.logo)
                .setTitle("Alert !!")
                .setMessage(s)
                .setPositiveButton("OK", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditProfile.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    //------------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
