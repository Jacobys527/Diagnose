package diagnosetodd.and.com.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import diagnosetodd.and.com.R;
import diagnosetodd.and.com.entity.PatientModel;

public class MainActivity extends BaseActivity {

    private Button diagnoseBtn;
    private EditText inputName;
    private TextInputLayout inputLayoutName;
    private EditText inputEmail;
    private TextInputLayout inputLayoutEmail;
    private EditText inputAge;
    private TextInputLayout inputLayoutAge;
    private RadioGroup radioGenderGroup;
    private CheckBox inputMigraine;
    private CheckBox inputDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    @Override
    public void initView() {
        diagnoseBtn = (Button)findViewById(R.id.submit_btn);
        inputName = (EditText)findViewById(R.id.input_name);
        inputLayoutName = (TextInputLayout)findViewById(R.id.input_layout_name);
        inputEmail = (EditText)findViewById(R.id.input_email);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.input_layout_email);
        inputAge = (EditText)findViewById(R.id.input_age);
        inputLayoutAge = (TextInputLayout)findViewById(R.id.input_layout_age);
        radioGenderGroup = (RadioGroup)findViewById(R.id.radio_gender);
        inputMigraine = (CheckBox)findViewById(R.id.input_migraine);
        inputDrug = (CheckBox)findViewById(R.id.input_drug);

        diagnoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm(){
        if (!validateName()) {return;}
        if (!validateEmail()) {return;}
        if (!validateAge()) {return;}

        // Diagnose the patient and determine the percentage of disease.
        int nSum = 0;
        int nAge = Integer.parseInt(inputAge.getText().toString().trim());

        int nGender = 0;
        RadioButton radioGenderButton;
        radioGenderButton = (RadioButton) findViewById(radioGenderGroup.getCheckedRadioButtonId());
        String str = radioGenderButton.getText().toString();
        if (str.equalsIgnoreCase("Female")) {
            nGender = 1;
        }

        boolean isCheckedMigraine = inputMigraine.isChecked();
        boolean isCheckedDrug = inputDrug.isChecked();

        String nName = inputName.getText().toString().trim();
        String nEmail = inputEmail.getText().toString().trim();

        if (nAge <= 15) {nSum += 1;}
        if (nGender == 0) {nSum += 1;}
        if (isCheckedMigraine == true) {nSum += 1;}
        if (isCheckedDrug == true) {nSum += 1;}

        int nResult = nSum * 100 / 4;

        PatientModel patientModel = new PatientModel(nName,
                                                     nEmail,
                                                     nAge,
                                                     nGender,
                                                     isCheckedMigraine,
                                                     isCheckedDrug,
                                                     nResult
                                                     );
        if(isNetworkOnline()) {

            //Uploading Data(Network did not work, so save data to internal storage) to server.

//            List<PatientModel> patients = PatientModel.find(PatientModel.class, "isSynchronized = ?","true");
//            if (patients.size()>0) {
//                for (int i = 0; i < patients.size(); i++) {
//                    JSONObject obj = new JSONObject();
//                    try {
//                        obj.put("Name", patients.get(i).getPatientName());
//                        obj.put("Email", patients.get(i).getPatientEmail());
//                        obj.put("Age", patients.get(i).getPatientAge());
//                        obj.put("Gender", patients.get(i).getPatientGender());
//                        obj.put("Migraine", patients.get(i).getPatientMigraine());
//                        obj.put("Drug", patients.get(i).getPatientDrug());
//                        obj.put("Result", patients.get(i).getPatientResult());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String ptId = null;
//                    //ptId = uploadPatientData(obj);
//                    if (ptId == null) {
//                        patients.get(i).setPatientId(ptId);
//                        patients.get(i).setIsNotSynchronized(false);
//                        patientModel.save();
//                    }
//                }
//            }

            // RestApi Returns patientID, the name of RestAPi is uploadPatientData
            // public String uploadPatientData(JSONARRAY)
            // return patientId

//            JSONObject obj = new JSONObject();
//            try {
//                obj.put("Name",nName);
//                obj.put("Email",nEmail);
//                obj.put("Age",nAge);
//                obj.put("Gender",nGender);
//                obj.put("Migraine",isCheckedMigraine);
//                obj.put("Drug",isCheckedDrug);
//                obj.put("Result",nResult);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            String ptId = null;
//            ptId = uploadPatientData(obj);
//            if(ptId!= null){
//                patientModel.save();
//            }else{
//                patientModel.setIsNotSynchronized(true);
//                patientModel.save();
//            }

            patientModel.setIsNotSynchronized(true);
//            patientModel.save();
        }else{
            patientModel.setIsNotSynchronized(true);
//            patientModel.save();
        }

        Toast.makeText(this , "  Name:" + nName + "  Age: " +  nAge + "  Email: " + nEmail + " Result:" +
                String.valueOf(nResult) , Toast.LENGTH_LONG).show();

        inputName.setText("");
        inputEmail.setText("");
        inputAge.setText("");
        inputMigraine.setChecked(false);
        inputDrug.setChecked(false);
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    //Check validation of Name: Not Empty
    private boolean validateName(){
        String name = inputName.getText().toString().trim();
        if (name.isEmpty()) {
            inputLayoutName.setError(getString(R.string.error_name_valid));
            requestFocus(inputName);
            return false;

        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }


    //Check validation of Email: The type of Email, ttt@test.com
    private static  boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean  validateEmail() {
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.error_email_valid));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }


    //Check validation of Age:
    private boolean validateAge(){
        String name = inputAge.getText().toString().trim();
        if (name.isEmpty()) {
            inputLayoutAge.setError(getString(R.string.error_age_valid));
            requestFocus(inputAge);
            return false;

        } else {
            inputLayoutAge.setErrorEnabled(false);
        }
        return true;
    }
}
