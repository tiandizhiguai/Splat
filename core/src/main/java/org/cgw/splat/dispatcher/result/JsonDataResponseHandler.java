package org.cgw.splat.dispatcher.result;

import com.alibaba.fastjson.JSONObject;

public class JsonDataResponseHandler implements DataResponseHandler {

    @Override
    public String handleData(Object context) {
        return JSONObject.toJSONString(context);
    }

}
