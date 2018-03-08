package top.liaction

import android.content.Context
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.dcloud.application.DCloudApplication
import io.dcloud.common.DHInterface.IWebview
import okhttp3.OkHttpClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.json.JSONObject
import java.util.concurrent.TimeUnit


/**
 * Created by liaction/chen.si
 * base
 */

class YMWYBaseApp : DCloudApplication() {
  override fun onCreate() {
    super.onCreate()
    val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    AndroidNetworking.initialize(applicationContext, okHttpClient)

  }
}

interface YMWYBase : AnkoLogger {

  fun showLog(message: Any) {
    error {
      message
    }
  }

  /**
   * 暂时先支持返回数据为JsonObject
   */
  fun post(context: Context, url: String,
      parameterMap: Any,
      success: ((result: JSONObject) -> Unit)?, error: (() -> Unit)?) {
    val qmuiTipDialog = QMUITipDialog.Builder(context).setTipWord("请求数据中").setIconType(
        QMUITipDialog.Builder.ICON_TYPE_LOADING)
        .create()
    qmuiTipDialog.show()
    AndroidNetworking.post(url)
        .addBodyParameter(parameterMap)
        .build().getAsJSONObject(object : JSONObjectRequestListener {
      override fun onResponse(response: JSONObject) {
        qmuiTipDialog.dismiss()
        showLog(response.toString())
        success?.invoke(response)
      }

      override fun onError(anError: ANError) {
        qmuiTipDialog.dismiss()
        anError.printStackTrace()
        error?.invoke()
      }
    })
  }

  fun post(context: IWebview, url: String,
      parameterMap: Any,
      success: ((result: JSONObject) -> Unit)?, error: (() -> Unit)?) {
    post(context.activity, url, parameterMap, success, error)
  }

  fun showTip(context: Context, msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
  }

  fun showTip(context: IWebview, msg: String?) {
    showTip(context.activity, msg)
  }
}