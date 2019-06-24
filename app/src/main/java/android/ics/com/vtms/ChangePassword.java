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

public class ChangePassword extends AppCompatActivity {
    Toolbar toolbar_change;
    EditText oldPass, newPass, conPass;
    String OldPass, NewPass, ConPass;
    Button bt_chPass;
    SessionManager manager;
    String sUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbar_change = (Toolbar) findViewById(R.id.toolbar_change);
        toolbar_change.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar_change.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldPass = (EditText) findViewById(R.id.oldPass);
        newPass = (EditText) findViewById(R.id.newPass);
        conPass = (EditText) findViewById(R.id.conPass);
        bt_chPass = (Button) findViewById(R.id.bt_chPass);

        manager = new SessionManager(this);
        sUser = manager.getUserID();

        bt_chPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldPass = oldPass.getText().toString();
                NewPass = newPass.getText().toString();
                ConPass = conPass.getText().toString();

                if (!OldPass.equals("")) {
                    if (!NewPass.equals("")) {
                        if (!ConPass.equals("")) {
                            if (Connectivity.isNetworkAvailable(ChangePassword.this)) {
                                new PostChangPassword().execute();
                            } else {
                                Toast.makeText(ChangePassword.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            conPass.setError("Please Enter Confirm Password");
                            conPass.requestFocus();
                        }
                    } else {
                        newPass.setError("Please Enter New Password");
                        newPass.requestFocus();
                    }
                } else {
                    oldPass.setError("Please Enter Old Password");
                    oldPass.requestFocus();
                }
            }
        });
    }

    //--------------------------------------------------------

    public class PostChangPassword extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(ChangePassword.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://www.ihisaab.in/vtms/Api/change_password");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login_oldpassword", OldPass);
                postDataParams.put("login_newpassword", NewPass);
                postDataParams.put("login_confpassword", ConPass);
                postDataParams.put("login_uiserid", sUser);
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

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    if (response.equals("true")) {
                        String msg = jsonObject.getString("msg");
                        String userdata = jsonObject.getString("userdata");

                        AlertDialogCreate(jsonObject.getString("msg"));
                    } else {
                        //      Toast.makeText(LoginActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
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

    //-------------------------------------------------------

    public void AlertDialogCreate(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this, R.style.AlertDialogTheme);
        builder.setIcon(R.drawable.logo)
                .setTitle("Alert !!")
                .setMessage(s)
                .setPositiveButton("OK", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ChangePassword.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    //--------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
