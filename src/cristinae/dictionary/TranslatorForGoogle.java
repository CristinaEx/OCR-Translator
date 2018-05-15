package cristinae.dictionary;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

public class TranslatorForGoogle implements Translator{
	public final static int TRANSLATOR_TYPE = 201;
	
    /**
     * Key -> Value
     * 语言     -> 单词表示
     */
    public static final Map<String,String> LANGUAGE = new HashMap<String,String>();
    
    private String from = "ja";
    
    private String to = "zh-CN";
     
    static {
        LANGUAGE.put("阿尔巴尼亚语", "sq");
        LANGUAGE.put("阿拉伯语", "ar");
        LANGUAGE.put("阿塞拜疆语", "az");
        LANGUAGE.put("爱尔兰语", "ga");
        LANGUAGE.put("爱沙尼亚语", "et");
        LANGUAGE.put("巴斯克语", "eu");
        LANGUAGE.put("白俄罗斯语", "be");
        LANGUAGE.put("保加利亚语", "bg");
        LANGUAGE.put("冰岛语", "is");
        LANGUAGE.put("波兰语", "pl");
        LANGUAGE.put("波斯语", "fa");
        LANGUAGE.put("布尔语", "af");
        LANGUAGE.put("南非荷兰语", "af");
        LANGUAGE.put("丹麦语", "da");
        LANGUAGE.put("德语", "de");
        LANGUAGE.put("俄语", "ru");
        LANGUAGE.put("法语", "fr");
        LANGUAGE.put("菲律宾语", "tl");
        LANGUAGE.put("芬兰语", "fi");
        LANGUAGE.put("格鲁吉亚语", "ka");
        LANGUAGE.put("古吉拉特语", "gu");
        LANGUAGE.put("海地克里奥尔语", "ht");
        LANGUAGE.put("韩语", "ko");
        LANGUAGE.put("荷兰语", "nl");
        LANGUAGE.put("加利西亚语", "gl");
        LANGUAGE.put("加泰罗尼亚语", "ca");
        LANGUAGE.put("捷克语", "cs");
        LANGUAGE.put("卡纳达语", "kn");
        LANGUAGE.put("克罗地亚语", "hr");
        LANGUAGE.put("拉丁语", "la");
        LANGUAGE.put("拉脱维亚语", "lv");
        LANGUAGE.put("老挝语", "lo");
        LANGUAGE.put("立陶宛语", "lt");
        LANGUAGE.put("罗马尼亚语", "ro");
        LANGUAGE.put("马耳他语", "mt");
        LANGUAGE.put("马来语", "ms");
        LANGUAGE.put("马其顿语", "mk");
        LANGUAGE.put("孟加拉语", "bn");
        LANGUAGE.put("挪威语", "no");
        LANGUAGE.put("葡萄牙语", "pt");
        LANGUAGE.put("日语", "ja");
        LANGUAGE.put("瑞典语", "sv");
        LANGUAGE.put("塞尔维亚语", "sr");
        LANGUAGE.put("世界语", "eo");
        LANGUAGE.put("斯洛伐克语", "sk");
        LANGUAGE.put("斯洛文尼亚语", "sl");
        LANGUAGE.put("斯瓦希里语", "sw");
        LANGUAGE.put("泰卢固语", "te");
        LANGUAGE.put("泰米尔语", "ta");
        LANGUAGE.put("泰语", "th");
        LANGUAGE.put("土耳其语", "tr");
        LANGUAGE.put("威尔士语", "cy");
        LANGUAGE.put("乌尔都语", "ur");
        LANGUAGE.put("乌克兰语", "uk");
        LANGUAGE.put("希伯来语", "iw");
        LANGUAGE.put("希腊语", "el");
        LANGUAGE.put("西班牙语", "es");
        LANGUAGE.put("匈牙利语", "hu");
        LANGUAGE.put("亚美尼亚语", "hy");
        LANGUAGE.put("意大利语", "it");
        LANGUAGE.put("意第绪语", "yi");
        LANGUAGE.put("印地语", "hi");
        LANGUAGE.put("印尼语", "id");
        LANGUAGE.put("英语", "en");
        LANGUAGE.put("越南语", "vi");
        LANGUAGE.put("中文繁体", "zh-TW");
        LANGUAGE.put("中文简体", "zh-CN");
    }
    /**
     * GET 谷歌翻译
     * @param word 待翻译的字符串
     * @param from 源语言
     * @param to 目标语言
     * @return 翻译结果JSON字符串
     */
    public String translateWord(String word, String from, String to) {
        if(from == null || to == null){
            return null;
        }
        HeadBuilder form = new HeadBuilder();
        form.add("client", "gtx");  
        form.add("sl", from);
        form.add("tl", to);
        form.add("dt", "t");
        form.add("q", WebRequest.encode(word, "UTF-8"));
        String result = WebRequest.get("http://translate.google.cn/translate_a/single?"
                + form.toString(), "UTF-8");
        try {
        	/*
        	 * 此时array拥有全部信息
        	 */
        	JSONArray array = new JSONArray(result);
        	//此时array拥有翻译信息
            array = array.getJSONArray(0);
            //清空
            result = "";
            for(int index = 0;index < array.length();index++)
            {
            	JSONArray arrayNow = array.getJSONArray(index);
            	result += arrayNow.getString(0);
            }
        }catch (Exception ex){
        	return null;
        }
        return result;
    }
    
    public String translate(String word)
    {
    	return  this.translateWord(word, this.from, this.to);
    }
    
    public void setFrom(String from) {
		this.from = from;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
    
}
