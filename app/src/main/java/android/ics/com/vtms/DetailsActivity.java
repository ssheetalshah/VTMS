package android.ics.com.vtms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.ics.com.vtms.JAVA_files.Vehicles;
import android.ics.com.vtms.utils.AppPreference;
import android.ics.com.vtms.utils.Connectivity;
import android.ics.com.vtms.utils.SessionManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class DetailsActivity extends AppCompatActivity {
    Toolbar toolbar_details;
    LinearLayout uploadli, agencyli, aggrement, bankli, branchli, rcli, customerli, cycleli, Vehli, ARMli, RRMli, RCMLi, ZMli, emailli, Chargesli,
            Makeli, Chassisli, Engineli, Customerli, Yearli, Areali, Regionli, Sec9li, Sec17li, Guarli, Opngli, APPLli, Tentativeli, Tentativebli, GVli, Segmentli, Lastli, Remarkli, Remarktxli, Remarktxli1, Remarktxli2, Maturityli, agencylicode, Remarktxli2x;
    TextView dId, uploadDate, agenCode, agreNo, bankName, branchName, rcNo, custName, cycle1, vehiDescrip, armName,
            rrmName, rcmName, zmName, emailId, chargDue, make, chassisNo, engineNo, custAddress, yom, areaOffice,
            region, sec9, sec17, guarName, opngAmount, appl, tenNoi, tenBkt, gv, segment, lastRepDate, loanExp, maturity,
            agenName, reTxt1, reTxt2, reNum1, reNum2, uid;
    TelephonyManager telephonyManager;
    String vId, vDesp;
    SessionManager manager;
    String sLoginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //
        uploadli = findViewById(R.id.uploadli);
        agencyli = findViewById(R.id.Agencyli);
        agencylicode = findViewById(R.id.agencylicode);
        aggrement = findViewById(R.id.aggrement);
        bankli = findViewById(R.id.bankli);
        rcli = findViewById(R.id.rcli);
        Remarktxli2x = findViewById(R.id.Remarktxli2x);
        branchli = findViewById(R.id.branchli);
        customerli = findViewById(R.id.customerli);
        cycleli = findViewById(R.id.cycleli);
        Vehli = findViewById(R.id.Vehli);
        ARMli = findViewById(R.id.ARMli);
        RRMli = findViewById(R.id.RRMli);
        RCMLi = findViewById(R.id.RCMLi);
        ZMli = findViewById(R.id.ZMli);
        emailli = findViewById(R.id.emailli);
        Chargesli = findViewById(R.id.Chargesli);
        Makeli = findViewById(R.id.Makeli);
        Chassisli = findViewById(R.id.Chassisli);
        Engineli = findViewById(R.id.Engineli);
        Customerli = findViewById(R.id.Customerli);
        Yearli = findViewById(R.id.Yearli);
        Areali = findViewById(R.id.Areali);
        Regionli = findViewById(R.id.Regionli);
        Sec9li = findViewById(R.id.Sec9li);
        Sec17li = findViewById(R.id.Sec17li);
        Guarli = findViewById(R.id.Guarli);
        Opngli = findViewById(R.id.Opngli);
        APPLli = findViewById(R.id.APPLli);
        Maturityli = findViewById(R.id.Maturityli);
        Tentativeli = findViewById(R.id.Tentativeli);
        Tentativebli = findViewById(R.id.Tentativebli);
        GVli = findViewById(R.id.GVli);
        Segmentli = findViewById(R.id.Segmentli);
        Lastli = findViewById(R.id.Lastli);
        Remarktxli = findViewById(R.id.Remarkli);
        Remarktxli1 = findViewById(R.id.Remarkli1);
        Remarktxli2 = findViewById(R.id.Remarkli2);
        Remarkli = findViewById(R.id.Remarkli);

        toolbar_details = (Toolbar) findViewById(R.id.toolbar_details);
        toolbar_details.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar_details.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        manager = new SessionManager(this);
        sLoginId = manager.getLoginid();
        // Log.e("sLoginId",sLoginId);

        if (getIntent() != null) {
            Vehicles vehicles = (Vehicles) getIntent().getSerializableExtra("Vehicles");
            vId = vehicles.getId();
            vDesp = vehicles.getVehicleDescription();
        }

        dId = (TextView) findViewById(R.id.dId);
        uploadDate = (TextView) findViewById(R.id.uploadDate);
        agenCode = (TextView) findViewById(R.id.agenCode);
        agreNo = (TextView) findViewById(R.id.agreNo);
        bankName = (TextView) findViewById(R.id.bankName);
        branchName = (TextView) findViewById(R.id.branchName);
        rcNo = (TextView) findViewById(R.id.rcNo);
        custName = (TextView) findViewById(R.id.custName);
        cycle1 = (TextView) findViewById(R.id.cycle);
        vehiDescrip = (TextView) findViewById(R.id.vehiDescrip);
        armName = (TextView) findViewById(R.id.armName);
        rrmName = (TextView) findViewById(R.id.rrmName);
        rcmName = (TextView) findViewById(R.id.rcmName);
        zmName = (TextView) findViewById(R.id.zmName);
        emailId = (TextView) findViewById(R.id.emailId);
        chargDue = (TextView) findViewById(R.id.chargDue);
        make = (TextView) findViewById(R.id.make);
        chassisNo = (TextView) findViewById(R.id.chassisNo);
        engineNo = (TextView) findViewById(R.id.engineNo);
        custAddress = (TextView) findViewById(R.id.custAddress);
        yom = (TextView) findViewById(R.id.yom);
        areaOffice = (TextView) findViewById(R.id.areaOffice);
        region = (TextView) findViewById(R.id.region);
        sec9 = (TextView) findViewById(R.id.sec9);
        sec17 = (TextView) findViewById(R.id.sec17);
        guarName = (TextView) findViewById(R.id.guarName);
        opngAmount = (TextView) findViewById(R.id.opngAmount);
        appl = (TextView) findViewById(R.id.appl);
        tenNoi = (TextView) findViewById(R.id.tenNoi);
        tenBkt = (TextView) findViewById(R.id.tenBkt);
        gv = (TextView) findViewById(R.id.gv);
        segment = (TextView) findViewById(R.id.segment);
        lastRepDate = (TextView) findViewById(R.id.lastRepDate);
        loanExp = (TextView) findViewById(R.id.loanExp);
        maturity = (TextView) findViewById(R.id.maturity);
        agenName = (TextView) findViewById(R.id.agenName);
        reTxt1 = (TextView) findViewById(R.id.reTxt1);
        reTxt2 = (TextView) findViewById(R.id.reTxt2);
        reNum1 = (TextView) findViewById(R.id.reNum1);
        reNum2 = (TextView) findViewById(R.id.reNum2);
        uid = (TextView) findViewById(R.id.uid);


        if (Connectivity.isNetworkAvailable(DetailsActivity.this)){
            new PostDetails().execute();
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    //------------------------------------------------------

    public class PostDetails extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(DetailsActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://www.ihisaab.in/vtms/Api/view_detail");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login_id", AppPreference.getLogid(DetailsActivity.this));
                postDataParams.put("id", vId);
                Log.e("postDataParams", postDataParams.toString());

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
                    String responce = jsonObject.getString("responce");
                    if (responce.equals("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0;  i<jsonArray.length(); i++){
                            JSONObject Obj = jsonArray.getJSONObject(i);
                            String UploadingDate = Obj.getString("UploadingDate");
                            if(UploadingDate.equals("none"))
                            {
                                uploadli.setVisibility(View.GONE);
                            }
                            String AgencyCode = Obj.getString("AgencyCode");
                            if(AgencyCode.equals("none"))
                            {
                                agencylicode.setVisibility(View.GONE);
                            }
                            String AggrementNo = Obj.getString("AggrementNo");
                            if(AggrementNo.equals("none"))
                            {
                                aggrement.setVisibility(View.GONE);
                            }
                            String BankName = Obj.getString("BankName");
                            if(BankName.equals("none"))
                            {
                                bankli.setVisibility(View.GONE);
                            }
                            String BranchName = Obj.getString("BranchName");
                            if(BranchName.equals("none"))
                            {
                                branchli.setVisibility(View.GONE);
                            }
                            String RcNo = Obj.getString("RcNo");
                            if(RcNo.equals("none"))
                            {
                                rcli.setVisibility(View.GONE);
                            }
                            String CustomerName = Obj.getString("CustomerName");
                            if(CustomerName.equals("none"))
                            {
                                customerli.setVisibility(View.GONE);
                            }
                            String cycle = Obj.getString("cycle");
                            if(cycle.equals("none"))
                            {
                                cycleli.setVisibility(View.GONE);
                            }
                            String VehicleDescription = Obj.getString("VehicleDescription");
                            if(VehicleDescription.equals("none"))
                            {
                                Vehli.setVisibility(View.GONE);
                            }
                            String ARM_SR_NameAndNo = Obj.getString("ARM_SR_NameAndNo");
                            if(ARM_SR_NameAndNo.equals("none"))
                            {
                                ARMli.setVisibility(View.GONE);
                            }
                            String RRM_SR_NameAndNo = Obj.getString("RRM_SR_NameAndNo");
                            if(RRM_SR_NameAndNo.equals("none"))
                            {
                                RRMli.setVisibility(View.GONE);
                            }
                            String RCM_NameAndNo = Obj.getString("RCM_NameAndNo");
                            if(RCM_NameAndNo.equals("none"))
                            {
                                RCMLi.setVisibility(View.GONE);
                            }
                            String ZM_NameAndNo = Obj.getString("ZM_NameAndNo");
                            if(ZM_NameAndNo.equals("none"))
                            {
                                ZMli.setVisibility(View.GONE);
                            }
                            String Email_ID = Obj.getString("Email_ID");
                            if(Email_ID.equals("none"))
                            {
                                emailli.setVisibility(View.GONE);
                            }
                            String ChargesDue = Obj.getString("ChargesDue");
                            if(ChargesDue.equals("none"))
                            {
                                Chargesli.setVisibility(View.GONE);
                            }
                            String Make = Obj.getString("Make");
                            if(Make.equals("none"))
                            {
                                Makeli.setVisibility(View.GONE);
                            }
                            String ChassisNo = Obj.getString("ChassisNo");
                            if(ChassisNo.equals("none"))
                            {
                                Chassisli.setVisibility(View.GONE);
                            }
                            String EngineNo = Obj.getString("EngineNo");
                            if(EngineNo.equals("none"))
                            {
                                Engineli.setVisibility(View.GONE);
                            }
                            String YearOfMfg = Obj.getString("YearOfMfg");
                            if(YearOfMfg.equals("none"))
                            {
                                Yearli.setVisibility(View.GONE);
                            }
                            String AreaOffice = Obj.getString("AreaOffice");
                            if(AreaOffice.equals("none"))
                            {
                                Areali.setVisibility(View.GONE);
                            }
                            String Region = Obj.getString("Region");
                            if(Region.equals("none"))
                            {
                                Regionli.setVisibility(View.GONE);
                            }
                            String Sec9AvailableArea = Obj.getString("Sec9AvailableArea");
                            if(Sec9AvailableArea.equals("none"))
                            {
                                Sec9li.setVisibility(View.GONE);
                            }
                            String CustomerAddress = Obj.getString("CustomerAddress");
                            if(CustomerAddress.equals("none"))
                            {
                                Customerli.setVisibility(View.GONE);
                            }
                            String GuarName = Obj.getString("GuarName");
                            if(GuarName.equals("none"))
                            {
                                Guarli.setVisibility(View.GONE);
                            }
                            String OpngOdAmount = Obj.getString("OpngOdAmount");
                            if(OpngOdAmount.equals("none"))
                            {
                                Opngli.setVisibility(View.GONE);
                            }
                            String APPL = Obj.getString("APPL");
                            if(APPL.equals("none"))
                            {
                                APPLli.setVisibility(View.GONE);
                            }
                            String TentativeNOI = Obj.getString("TentativeNOI");
                            if(TentativeNOI.equals("none"))
                            {
                                Tentativeli.setVisibility(View.GONE);
                            }
                            String TentativeBkt = Obj.getString("TentativeBkt");
                            if(TentativeBkt.equals("none"))
                            {
                                Tentativebli.setVisibility(View.GONE);
                            }
                            String GV = Obj.getString("GV");
                            if(GV.equals("none"))
                            {
                                GVli.setVisibility(View.GONE);
                            }
                            String Segment = Obj.getString("Segment");
                            if(Segment.equals("none"))
                            {
                                Segmentli.setVisibility(View.GONE);
                            }
                            String LastReceiptDate = Obj.getString("LastReceiptDate");
                            if(LastReceiptDate.equals("none"))
                            {
                                Lastli.setVisibility(View.GONE);
                            }
                            String Maturity = Obj.getString("Maturity");
                            if(Maturity.equals("none"))
                            {
                                Maturityli.setVisibility(View.GONE);
                            }
                            String AgencyName = Obj.getString("AgencyName");
                            if(AgencyName.equals("none"))
                            {
                                Log.e("re" , ""+AgencyName);
                                agencyli.setVisibility(View.GONE);
                            }
                            String RemarkText1 = Obj.getString("RemarkText1");
                            if(RemarkText1.equals("none"))
                            {
                                Remarkli.setVisibility(View.GONE);
                            }
                            String RemarkText2 = Obj.getString("RemarkText2");
                            if(RemarkText2.equals("none"))
                            {
                                Log.e("re" , ""+RemarkText2);
                                Remarktxli2x.setVisibility(View.GONE);
                            }
                            String RemarkNum1 = Obj.getString("RemarkNum1");
                            if(RemarkNum1.equals("none"))
                            {
                                Remarktxli1.setVisibility(View.GONE);
                            }
                            String RemarkNum2 = Obj.getString("RemarkNum2");
                            if(RemarkNum2.equals("none"))
                            {
                                Remarktxli2.setVisibility(View.GONE);
                            }

                            uploadDate.setText(UploadingDate);
                            agenCode.setText(AgencyCode);
                            agreNo.setText(AggrementNo);
                            bankName.setText(BankName);
                            branchName.setText(BranchName);
                            custName.setText(CustomerName);
                            cycle1.setText(cycle);
                            vehiDescrip.setText(VehicleDescription);
                            armName.setText(ARM_SR_NameAndNo);
                            rcNo.setText(RcNo);
                            rrmName.setText(RRM_SR_NameAndNo);
                            rcmName.setText(RCM_NameAndNo);
                            zmName.setText(ZM_NameAndNo);
                            emailId.setText(Email_ID);
                            chargDue.setText(ChargesDue);
                            make.setText(Make);
                            chassisNo.setText(ChassisNo);
                            engineNo.setText(EngineNo);
                           // custAddress.setText(UploadingDate);
                            yom.setText(YearOfMfg);
                            areaOffice.setText(AreaOffice);
                            region.setText(Region);
                            sec9.setText(Sec9AvailableArea);
                            custAddress.setText(CustomerAddress);
                            guarName.setText(GuarName);
                            opngAmount.setText(OpngOdAmount);
                            appl.setText(APPL);
                            tenNoi.setText(TentativeNOI);
                            tenBkt.setText(TentativeBkt);
                            gv.setText(GV);
                            segment.setText(Segment);
                            lastRepDate.setText(LastReceiptDate);
                          //  loanExp.setText(UploadingDate);
                            maturity.setText(Maturity);
                            agenName.setText(AgencyName);
                            reTxt1.setText(RemarkText1);
                            reTxt2.setText(RemarkText2);
                            reNum1.setText(RemarkNum1);
                            reNum2.setText(RemarkNum2);
                          //  uid.setText(UploadingDate);
                        }

                    } else {
                        Toast.makeText(DetailsActivity.this, "Oops! Some Problem", Toast.LENGTH_LONG).show();
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

    //--------------------------------------------------------

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
