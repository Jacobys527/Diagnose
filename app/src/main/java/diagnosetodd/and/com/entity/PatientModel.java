package diagnosetodd.and.com.entity;

import com.orm.SugarRecord;

public class PatientModel extends SugarRecord {

    String patientId = "";
    String patientName = "";
    String patientEmail = "";
    int patientAge = 0;
    int patientGender = 0;
    boolean patientMigraine = false;
    boolean patientDrug = false;
    int patientResult = 0;
    boolean isNotSynchronized = false;


    public PatientModel(){}
    public PatientModel(String patientName,String patientEmail,int patientAge,
                        int patientGender,boolean patientMigraine,boolean patientDrug, int patientResult){
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientMigraine = patientMigraine;
        this.patientDrug = patientDrug;
        this.patientResult = patientResult;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }
    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public int getPatientAge() {
        return patientAge;
    }
    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public int getPatientGender() {
        return patientGender;
    }
    public void setPatientGender(int patientGender) {
        this.patientGender = patientGender;
    }

    public boolean getPatientMigraine() {
        return patientMigraine;
    }
    public void setPatientMigraine(boolean patientMigraine) {
        this.patientMigraine = patientMigraine;
    }

    public boolean getPatientDrug() {
        return patientDrug;
    }
    public void setPatientDrug(boolean patientDrug) {
        this.patientMigraine = patientMigraine;

    }

    public int getPatientResult() {
        return patientResult;
    }
    public void setPatientResult(int patientResult) {
        this.patientResult = patientResult;

    }

    public boolean getIsNotSynchronized() {
        return isNotSynchronized;
    }
    public void setIsNotSynchronized(boolean isNotSynchronized) {
        this.isNotSynchronized = isNotSynchronized;

    }
}
