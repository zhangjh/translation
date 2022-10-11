package me.zhangjh.translate.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Charsets;
import me.zhangjh.translate.constant.BaiduLanguage;
import me.zhangjh.translate.dto.BaiduTransResponse;
import me.zhangjh.translate.dto.TranslateRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public class BaiDuTranslate extends TranslateEngine<BaiduTransResponse> {

    private static final String URL_PRE = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private final String salt = "weired_sheep";

    private String genSign(TranslateRequest request) throws NoSuchAlgorithmException {
        String signContent = request.getAppId() + request.getText() + salt + request.getAppSecret();
        System.out.println(signContent);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(signContent.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, md5.digest()).toString(16);
    }

    public Map<String, String> getLanguages() {
        return BaiduLanguage.getLanguages();
    }

    @Override
    public BaiduTransResponse translateText(TranslateRequest translateRequest) throws Exception {
        // q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4
        String url =
                URL_PRE + "?q=" + URLEncoder.encode(translateRequest.getText(), Charsets.UTF_8.name())
                        + "&from=" + translateRequest.getFrom() + "&to=" + translateRequest.getTo() +
                        "&appid" +
                        "=" + translateRequest.getAppId() +
                        "&salt" +
                "=" + salt +
                "&sign=" + genSign(translateRequest);
        System.out.println(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(URI.create(url));
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resContent = EntityUtils.toString(response.getEntity());
        System.out.println(resContent);
        return JSONObject.parseObject(resContent, BaiduTransResponse.class);
    }
}
