package diagnosetodd.and.com.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public abstract void initView();

    public void startActivityWithAnimation(final Intent intent, final int enterAnim, final int exitAnim, final int delayTime, final boolean isFinish){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
                if (isFinish==true){finish();}
                overridePendingTransition(enterAnim, exitAnim);
            }
        }, delayTime);
    }
}
