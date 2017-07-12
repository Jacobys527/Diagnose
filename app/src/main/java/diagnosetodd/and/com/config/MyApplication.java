package diagnosetodd.and.com.config;

import android.app.Application;

import com.orm.SugarContext;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    public static MyApplication getInstance(){return mInstance;}

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
