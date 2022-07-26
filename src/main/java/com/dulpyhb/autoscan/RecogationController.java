package com.dulpyhb.autoscan;

import com.aliyun.imagerecog20190930.*;
import com.aliyun.imagerecog20190930.models.*;
import com.aliyun.tea.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.*;
import com.aliyun.teautil.models.*;
import com.dulpyhb.autoscan.config.AliyunConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("recogation")
public class RecogationController {


    @GetMapping("/food")
    public String recogationFood(String url) throws Exception {
        RecognitionTools tools = new RecognitionTools();
        com.aliyun.imagerecog20190930.Client client = tools.createClient(AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);
        RecognizeFoodRequest recognizeFoodRequest = new RecognizeFoodRequest();
        recognizeFoodRequest.setImageURL(url);
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeFoodResponse resp = client.recognizeFoodWithOptions(recognizeFoodRequest, runtime);
            return com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp));
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return "resp.body.toString()";
    }

}
