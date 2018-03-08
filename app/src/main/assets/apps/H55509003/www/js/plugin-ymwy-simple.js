// simple plugin 
// chen.si liaction 2018-3-8

document.addEventListener('plusready',function () {
	
	// 声明plus调用时使用的名称
	var ymwySimple = {
		// 声明要调用的方法,此方法名称用于js调用,与Native里的名称没有关系
		/**
		 * 调用Native sayHello方法
		 * @param {Object} argus 传递的参数,json格式
		 * @param {Object} successCallback 成功回调
		 * @param {Object} errorCallback 失败回调
		 */
		sayHello : function (argus,successCallback,errorCallback) {
			
			// 防御性校验
			var success = typeof successCallback !== "function" ? null : function (result) {
				successCallback(result);
			};
			var fail = typeof errorCallback !== "function" ? null : function (code) {
				errorCallback(code);
			};
			
			// 获取callbackId
			var callbackId = window.plus.bridge.callbackId(success,fail);
			
			// 异步调用 native 方法
			window.plus.bridge.exec("YMWYSimple","sayHello",[callbackId,argus]);
		},
		reset : function (argus,successCallback,errorCallback) {
			
			// 防御性校验
			var success = typeof successCallback !== "function" ? null : function (result) {
				successCallback(result);
			};
			var fail = typeof errorCallback !== "function" ? null : function (code) {
				errorCallback(code);
			};
			
			// 获取callbackId
			var callbackId = window.plus.bridge.callbackId(success,fail);
			
			// 异步调用 native 方法
			window.plus.bridge.exec("YMWYSimple","reset", [callbackId,argus]);
		}
	};
	
	
	// 暴露出去
	window.plus.ymwySimple = ymwySimple;
	
},true);