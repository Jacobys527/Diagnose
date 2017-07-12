package diagnosetodd.and.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.List;

import diagnosetodd.and.com.R;
import diagnosetodd.and.com.config.MySharedPreferenceManager;
import diagnosetodd.and.com.entity.PatientModel;

public class SplashActivity extends BaseActivity {

    private KenBurnsView mKenBurns;

    private boolean isOnPause = false;

    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initView();
        initAction();
    }

    @Override
    public void initView() {
        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
    }

    public void initAction(){
        mKenBurns.setImageResource(R.drawable.splash);

        //Network works, then get all data from server and save them to local storage(SQLite)
//        if(isNetworkOnline()) {
//            List<PatientModel> patients = PatientModel.find(PatientModel.class, "isSynchronized = ?","false");
//            for(int i=0;i<patients.size();i++) {
//                patients.get(i).delete();
//            }
//            //get all data from server.Server returns string array with Json format.
//
//            List<String> allData = getDatafromServer();
//            for(int i=0;i<allData.size();i++){
//                PatientModel patientModel = new PatientModel();
//                patientModel.setPatientId(allData.get(i)["Id"]);
//                patientModel.setPatientName(allData.get(i)["Name"]);
//                patientModel.setPatientEmail(allData.get(i)["Email"]);
//                patientModel.setPatientAge(allData.get(i)["Age"]);
//                patientModel.setPatientGender(allData.get(i)["Gender"]);
//                patientModel.setPatientMigraine(allData.get(i)["Migraine"]);
//                patientModel.setPatientDrug(allData.get(i)["Drug"]);
//                patientModel.setPatientResult(allData.get(i)["Result"]);
//            }
//        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mKenBurns.pause();
                go2NextScreen();
            }
        } , 5000);
    }

    private void go2NextScreen()
    {
        MySharedPreferenceManager sf = new MySharedPreferenceManager(this);
        final boolean isFirstLaunch = sf.getValueBoolean("isFirstLaunch" , true);
        if(isOnPause) return;
        mKenBurns.pause();
        if(isFirstLaunch) {
            startActivityWithAnimation( new Intent(SplashActivity.this, WizardActivity.class),R.anim.slide_in,R.anim.slide_out, 0, true);
        }
        else{
            startActivityWithAnimation( new Intent(SplashActivity.this, MainActivity.class),R.anim.fade_in,R.anim.fade_out, 0, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
    }


    private boolean isNetworkOnline(){
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }

}
