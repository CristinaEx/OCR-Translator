package cristinae.dictionary;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class TranslatorForBaidu implements Translator{
	
	public final static int TRANSLATOR_TYPE = 202;
	/*
	 * auto	自动检测
		zh	中文
		en	英语
		yue	粤语
		wyw	文言文
		jp	日语
		kor	韩语
		fra	法语
		spa	西班牙语
		th	泰语
		ara	阿拉伯语
		ru	俄语
		pt	葡萄牙语
		de	德语
		it	意大利语
		el	希腊语
		nl	荷兰语
		pl	波兰语
		bul	保加利亚语
		est	爱沙尼亚语
		dan	丹麦语
		fin	芬兰语
		cs	捷克语
		rom	罗马尼亚语
		slo	斯洛文尼亚语
		swe	瑞典语
		hu	匈牙利语
		cht	繁体中文
		vie	越南语
	 */
	public String from = "auto";
	
	public String to = "zh";
	
	private String appId;
	
	private String salt;
	
	private String key;
	
	TranslatorForBaidu(String appId, String key){
		this.appId = appId;
		this.key = key;
		this.salt = new Integer(Math.abs(new Random(System.currentTimeMillis()).nextInt())).toString();
	}
	
	public String translateWord(String word, String from, String to) {
		
        if(from == null || to == null){
            return null;
        }
        HeadBuilder form = new HeadBuilder();
        form.add("from", from);
        form.add("to", to);
        form.add("q",WebRequest.encode(word, "UTF-8"));
        form.add("appid", this.appId);
        form.add("salt", this.salt);
        form.add("sign", MD5.md5(this.appId + word + this.salt + this.key));
        String result  = new String();
        String str = WebRequest.get("http://api.fanyi.baidu.com/api/trans/vip/translate?"
                + form.toString(), "UTF-8");
        JSONObject object = new JSONObject(str);
        /*
         * 避免没有任何数据返回
         * 使用try
         */
        try{
        	JSONArray array = object.getJSONArray("trans_result");
        	object = array.getJSONObject(0);
            result = object.getString("dst");
            return result;
        } catch (Exception e) {
            return "";
        }  
    }
	
	public String translate(String word)
	{
		return this.translateWord(word, this.from, this.to);
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
}
