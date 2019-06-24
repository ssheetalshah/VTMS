package android.ics.com.vtms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.ics.com.vtms.utils.AppPreference;
import android.ics.com.vtms.utils.Connectivity;
import android.ics.com.vtms.utils.SessionManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {
    Button bt_login;
    TextView forgetPass;
    Toolbar toolbar_login;
    EditText userId, password ,lice_no;
    String UserId, Password ,Licence="";
    SessionManager manager;
    String login_id="";
    private TelephonyManager telephonyManager;
    String IMIE_NO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar_login = (Toolbar) findViewById(R.id.toolbar_login);
        toolbar_login.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar_login.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lice_no = findViewById(R.id.lice_no);
        bt_login = (Button) findViewById(R.id.bt_login);
        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.password);
        forgetPass = (TextView) findViewById(R.id.forgetPass);

        manager = new SessionManager(this);
        if (manager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserId = userId.getText().toString();
                Password = password.getText().toString();
                Licence = lice_no.getText().toString();
                if (!UserId.equals("")) {
                    if (!Password.equals("")) {
                        if(!Licence.equals(""))
                        {
                            if (Connectivity.isNetworkAvailable(LoginActivity.this)) {
                                new PostLogin().execute();
                            } else {
                                Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            lice_no.setError("Please enter licence number provided to you");
                        }

                    } else {
                        password.setError("Please Enter Password");
                        password.requestFocus();
                    }
                } else {
                    userId.setError("Please Enter User Id");
                    userId.requestFocus();
                }
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_PHONE_STATE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        getIMEI();
                        Toast.makeText(LoginActivity.this, "responce is "+response.getPermissionName(), Toast.LENGTH_SHORT).show();
                        // permission is granted, open the camera
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(LoginActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                            // navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest
                                                                           permission, PermissionToken token) {
                        Toast.makeText(LoginActivity.this, "Gonna request", Toast.LENGTH_SHORT).show();
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    @SuppressLint("HardwareIds")
    private void getIMEI() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            Log.d("Device id"  , ""+telephonyManager.getDeviceId());
            IMIE_NO = telephonyManager.getDeviceId();
            Toast.makeText(this, "device name is "+telephonyManager.getDeviceId(), Toast.LENGTH_SHORT).show();
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
//        return telephonyManager.getDeviceId();
    }

    //------------------------------------------------------

    public class PostLogin extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://www.ihisaab.in/vtms/Api/user_login");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login_uiserid", UserId);
                postDataParams.put("login_password", Password);
                postDataParams.put("im_no", IMIE_NO);
                postDataParams.put("lice_no", Licence);
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
                        JSONObject userdata = jsonObject.getJSONObject("userdata");
                         login_id = userdata.getString("login_id");
                        String login_uiserid = userdata.getString("login_uiserid");
                        String login_password = userdata.getString("login_password");

                        manager.malegaonLogin(login_id,login_uiserid);

                        AppPreference.setLogid(LoginActivity.this,login_id);
                        AppPreference.setLicense(LoginActivity.this,Licence);
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();
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

    //---------------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
