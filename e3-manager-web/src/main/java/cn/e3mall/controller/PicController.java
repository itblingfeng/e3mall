package cn.e3mall.controller;

import cn.e3mall.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PicController {
    @Value("${IMAGE_SERVER}")
    private String IMAGE_SERVER;
    @RequestMapping("/pic/upload")
    public @ResponseBody
    Map PicUpload(MultipartFile uploadFile) {
        Map resultMap = new HashMap();
        try {
            FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
            String url = client.uploadFile(uploadFile.getBytes(), uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1));
            url = IMAGE_SERVER + url;

            resultMap.put("error",0);
            resultMap.put("url",url);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("error",1);
            resultMap.put("message",e);
            return resultMap;
        }
    }
}
