package top.liaction.plugins

import io.dcloud.common.DHInterface.AbsMgr
import io.dcloud.common.DHInterface.IWebview
import io.dcloud.common.DHInterface.StandardFeature
import io.dcloud.common.util.JSUtil
import org.json.JSONArray
import org.json.JSONObject
import top.liaction.YMWYBase

/**
 * Created by liaction/chen.si on 2018/3/8 0008.
 * #测试plugin开发
 * >0.继承[StandardFeature]
 * >1.编写Js需要调用的方法,比如:[sayHello],js调用时,action参数
 * >2.
 *
 * 注意:
 * 最后别忘了在[assets/data/dcloud_properties.xml]中[features]节点下进行注册
 * 比如添加一句:```<feature name="YMWYSimple" value="top.liaction.plugins.YMWYSimplePlugin"/>```
 * 其中 name : js bridge中要调用的服务名,即server参数 , value : 对应的class全路径,调用时会被初始化
 */
class YMWYSimplePlugin : StandardFeature(), YMWYBase {
  //请求次数
  private var mRequestCounter = 0

  override fun init(p0: AbsMgr?, p1: String?) {
    super.init(p0, p1)
    showLog("YMWYSimplePlugin init , feature name : $p1")
  }

  /**
   * @param webview 发起请求的webview
   * @param jsonArray JS请求传入的参数
   */
  fun sayHello(webview: IWebview, jsonArr: JSONArray= JSONArray()) {
    showLog("start invoke method : [sayHello], jsonArray : $jsonArr ")
    mRequestCounter++
    val callbackId = jsonArr.getString(0)
    val argus = jsonArr.getJSONObject(1)
    val requestSuccess = argus.getBoolean("request")

    if (requestSuccess) {
      argus.put("result", "收到请求")
      argus.put("count", mRequestCounter)

      /**
       * 0-0.传[JSUtil.OK] + false
       * 客户端:successCallback
       */
      argus.put("keepCallback", false)
      JSUtil.execCallback(webview, callbackId, argus, JSUtil.OK, false)

      /**
       * 0-1.传[JSUtil.OK] + true
       * 客户端:successCallback
       */
//    argus.put("keepCallback",true)
//    JSUtil.execCallback(webview,callbackId,argus,JSUtil.OK,true)

      return
    }

    /**
     * 1-0.传[JSUtil.ERROR] + false
     * 客户端:errorCallback
     */
    JSUtil.execCallback(webview,callbackId,mRequestCounter.toDouble(),JSUtil.ERROR,false)

    /**
     * 1-1.传[JSUtil.ERROR] + true
     * 客户端:errorCallback
     */
//    JSUtil.execCallback(webview,callbackId,mRequestCounter.toDouble(),JSUtil.ERROR,true)

    /**
     * todo
     * [JSUtil.execCallback]的最后一个参数到底有什么作用?待定...
     * 2018-3-8 15:44:22
     */

  }

  fun reset(webview: IWebview, jsonArr: JSONArray) {
    showLog("start invoke method : [reset], jsonArray : $jsonArr")
    mRequestCounter = 0
    JSUtil.execCallback(webview, jsonArr.getString(0), "重置成功", JSUtil.OK, false)
  }

}