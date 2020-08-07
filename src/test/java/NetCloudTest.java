import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qzw.robot.util.HttpUtils;
import kotlinx.serialization.json.JsonObject;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author ：quziwei
 * @date ：07/08/2020 11:11
 * @description：
 */
public class NetCloudTest {
    private static final String URL = "http://music.163.com/api/search/pc";

    private static final String URL2 = "https://api.imjad.cn/cloudmusic/";
    @Test
    public void test(){
        HashMap<String,String> n = new HashMap<>();
        n.put("s","lemon");
        n.put("offset","0");
        n.put("limit","1");
        n.put("type","1");
        String res = HttpUtils.post(URL,HttpUtils.formatData(n));
        JSONObject jb = JSONObject.parseObject(res);
        JSONObject result = jb.getJSONObject("result");
        JSONArray songs = result.getJSONArray("songs");
        Object o = songs.get(0);
        String str1 = JSONObject.toJSONString(o);
        JSONObject jb2 = JSONObject.parseObject(str1);
        String id = jb2.getString("id");

        HashMap<String,String> g = new HashMap();
        g.put("type","song");
        g.put("id",id);
        String res2 = HttpUtils.get(URL2,g);
        JSONObject resObj = JSONObject.parseObject(res2);
        JSONArray data = resObj.getJSONArray("data");
        JSONObject dataObj = (JSONObject) data.get(0);


        System.out.println(dataObj.getString("url"));


    }
}