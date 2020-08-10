package com.qzw.robot.util;

import cn.hutool.json.JSONObject;

import java.io.IOException;


public class VideoSearchUtil {
	public static String bilibiliVideo(String str) throws IOException {
		String searchStr = HttpUtils.chnAndSpaceReplace(str);
		//根据搜索结果，获取第一个结果的bvid
		JSONObject bilibiliSearch = HttpUtils.readJsonFromUrl("https://api.bilibili.com/x/web-interface/search/all/v2?keyword=" +
				searchStr + "&page=1&pagesize=0");
		String desc, preview, url, author;
		desc = bilibiliSearch.getByPath("data.result[8].data[0].title", String.class).replaceAll("<em class=\"keyword\">|</em>", "").replaceAll("&amp;", " ");
		preview = "https:" + bilibiliSearch.getByPath("data.result[8].data[0].pic", String.class);
		author = bilibiliSearch.getByPath("data.result[8].data[0].author", String.class);
		url = bilibiliSearch.getByPath("data.result[8].data[0].arcurl", String.class);
		
		//返回分享json
		return "{\"app\":\"com.tencent.structmsg\",\"config\":{\"autosize\":true,\"ctime\":1594271225,\"forward\":true,\"type\":\"normal\"},\"desc\":\"视频\",\"extra\":{\"app_type\":1,\"appid\":100495085},\"meta\":{\"video\":{\"app_type\":1,\"appid\":100495085,\"desc\":\"" + author + "\",\"jumpUrl\":\"" + url + "\",\"preview\":\"" + preview + "\",\"tag\":\"哔哩哔哩\",\"title\":\"" + desc + "\"}},\"prompt\":\"[分享]" + desc + "\",\"ver\":\"0.0.0.1\",\"view\":\"video\"}";
	}
}
