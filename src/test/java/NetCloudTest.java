import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qzw.robot.menu.Music;
import com.qzw.robot.model.MusicInfo;
import com.qzw.robot.util.HttpUtils;
import kotlinx.serialization.json.JsonObject;
import org.junit.Test;

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
        System.out.println(res);
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

    @Test
    public void musicInfo(){
        MusicInfo musicInfo = new MusicInfo();
        HashMap<String,String> n = new HashMap<>();
        n.put("s","别留 gai");
        n.put("offset","0");
        n.put("limit","1");
        n.put("type","1");
        String res = HttpUtils.post(URL,HttpUtils.formatData(n));
        System.out.println(res);

        JSONObject jb = JSONObject.parseObject(res);
        JSONObject result = jb.getJSONObject("result");
        JSONArray songs = result.getJSONArray("songs");
        Object o = songs.get(0);
        String str1 = JSONObject.toJSONString(o);
        JSONObject jb2 = JSONObject.parseObject(str1);
        //获取到音乐ID和音乐名称
        String id = jb2.getString("id");
        String musicUrl = "http://music.163.com/song/media/outer/url?id="+id;
        musicInfo.setMusicUrl(id);
        String jumpUrl = "https://y.music.163.com/m/song?id="+id+"&userid=83974353";
        musicInfo.setJumpUrl(jumpUrl);
        String musicName = jb2.getString("name");
        musicInfo.setMusicName(musicName);
        System.out.println(musicName);

        //获取演唱者
        JSONArray artists = jb2.getJSONArray("artists");
        JSONObject artist = artists.getJSONObject(0);
        String desc = artist.getString("name");
        musicInfo.setDesc(desc);

        //获取专辑封面
        JSONObject albums = jb2.getJSONObject("album");
        String albumImg = albums.getString("blurPicUrl");
        musicInfo.setPreview(albumImg);

    }
}
