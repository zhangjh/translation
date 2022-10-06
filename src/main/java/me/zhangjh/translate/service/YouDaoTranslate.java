package me.zhangjh.translate.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Charsets;
import me.zhangjh.translate.constant.YouDaoLanguage;
import me.zhangjh.translate.dto.YoudaoTransResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
public class YouDaoTranslate extends TranslateEngine {

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";

    private final String APP_KEY;

    private final String APP_SECRET;

    {
        APP_KEY = System.getenv("youdao_appKey");
        APP_SECRET = System.getenv("youdao_appSecret");
    }

    private Map<String, String> genParam(String q, String from, String to) {
        Map<String,String> params = new HashMap<>();
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", from);
        params.put("to", to);
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
//        params.put("vocabId","您的用户词表ID");
        return params;
    }

    public YoudaoTransResponse requestForHttp(String url, Map<String,String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<>();
        for (Map.Entry<String, String> en : params.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key, value));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(paramsList, Charsets.UTF_8));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            if("audio/mp3".equals(contentType[0].getValue())){
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                if(result != null){
                    String file = "合成的音频存储路径" + System.currentTimeMillis() + ".mp3";
                    byte2File(result,file);
                }
                return null;
            } else{
                /* 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity,"UTF-8");
                EntityUtils.consume(httpEntity);
                System.out.println(json);
                return JSONObject.parseObject(json, YoudaoTransResponse.class);
            }
        }
    }

    /**
     * 生成加密字段
     */
    private static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     *
     * @param result 音频字节流
     * @param file 存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    public List<Map<String, String>> getLanguages() {
        return YouDaoLanguage.getLanguages();
    }

    @Override
    public YoudaoTransResponse translateText(String query, String from, String to) {
        try {
            return requestForHttp(YOUDAO_URL, genParam(query, from, to));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
