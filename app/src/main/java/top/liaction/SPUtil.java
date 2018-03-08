package top.liaction;

import android.content.SharedPreferences;
import android.text.TextUtils;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.SP;
import java.util.HashSet;
import java.util.Set;

public class SPUtil {
    private static SPUtil spUtil;
    private SharedPreferences preferences;
    public static SPUtil getInstance(IWebview pIWebview) {
        if (null == spUtil)
            spUtil = new SPUtil(pIWebview);
        return spUtil;
    }
    public static SPUtil getInstance() {
        if (null == spUtil)
            spUtil = new SPUtil();
        return spUtil;
    }

    public SPUtil(IWebview pIWebview){
        preferences = SP.getOrCreateBundle(pIWebview.obtainFrameView().obtainApp().obtainAppId()+SP.N_STORAGES);
    }
    public SPUtil(String appId){
        if (null == appId || TextUtils.isEmpty(appId.trim())) {
            appId = "";
        }
        preferences = SP.getOrCreateBundle(appId+SP.N_STORAGES);
    }
    public SPUtil(){
        this("");
    }

    public boolean putString(String key, String data) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        return editor.commit();
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public boolean remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        return editor.commit();
    }



}
