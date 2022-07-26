package com.dulpyhb.autoscan;

import com.aliyun.imagerecog20190930.models.RecognizeFoodRequest;
import com.aliyun.imagerecog20190930.models.RecognizeFoodResponse;
import com.aliyun.imagerecog20190930.models.RecognizeFoodResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dulpyhb.autoscan.config.AliyunConfig;
import com.dulpyhb.autoscan.model.Image;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.*;
import java.io.*;

@RestController
@RequestMapping("upload")
public class uploadViewController {
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            return new HashMap<>();
        }
        UploadPictureUtil pictureUtil = new UploadPictureUtil();

        String fileName = multipartFile.getOriginalFilename();
        File file = new File(fileName);

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] bytes = multipartFile.getBytes();
            for(int i = 0; i < bytes.length; i++){
                out.write(bytes[i]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String url = pictureUtil.uploadOriginForOSS(fileName,file);
        RecognitionTools tools = new RecognitionTools();
        com.aliyun.imagerecog20190930.Client client = tools.createClient(AliyunConfig.ACCESSKEYID, AliyunConfig.ACCESSKEYSECRET);
        RecognizeFoodRequest recognizeFoodRequest = new RecognizeFoodRequest();
        recognizeFoodRequest.setImageURL(url);
        RuntimeOptions runtime = new RuntimeOptions();

        Image image = new Image();
        image.setImageName(fileName);
        image.setUrl(url);
        System.out.println("imUrl"+url);

        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeFoodResponse resp = client.recognizeFoodWithOptions(recognizeFoodRequest, runtime);
            System.out.println("imUrl"+url);

            Map <String, Object> data = TeaModel.buildMap(resp.body);
            System.out.println("imUrl"+url);

            data.put("imgUrl",url);
            ArrayList <ImageDTO> imageList = new ArrayList<>();
            for (RecognizeFoodResponseBody.RecognizeFoodResponseBodyDataTopFives topFives:
            resp.getBody().getData().getTopFives()) {
                imageList.add(GenerateImage.createImageDTO(topFives.category,new Color(102,102,102,100),new Font("微软雅黑", Font.PLAIN, 24), 78, 160));
                System.out.println("topFives.category"+topFives.category);
            }
            GenerateImage.writeImage(fileName, fileName,imageList);
//            System.out.println("imUrl"+url);
            return data;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return new HashMap<>();

    }
}
