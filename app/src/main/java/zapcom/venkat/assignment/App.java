package zapcom.venkat.assignment;

import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import api.Api;
import api.NetworkModule;
import retrofit2.Retrofit;

public class App extends MultiDexApplication {

    private static App mInstance;
    private static Api mRetrofitApi ;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(App.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    private void initRetroFitApi() {
        getApi();
    }

    public static Api getApi() {
        if (mRetrofitApi == null) {
            mRetrofitApi = new NetworkModule(BASE_URL).provideApi();
        }
        return mRetrofitApi;
    }

    private static Retrofit retrofit;

    private static final String BASE_URL = "https://www.jsonkeeper.com/b/5BEJ";
    public String getURL(){
        return BASE_URL;
    }
}
