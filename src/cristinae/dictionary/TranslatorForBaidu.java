package cristinae.dictionary;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class TranslatorForBaidu implements Translator{
	
	public final static int TRANSLATOR_TYPE = 202;
	/*
	 * auto	�Զ����
		zh	����
		en	Ӣ��
		yue	����
		wyw	������
		jp	����
		kor	����
		fra	����
		spa	��������
		th	̩��
		ara	��������
		ru	����
		pt	��������
		de	����
		it	�������
		el	ϣ����
		nl	������
		pl	������
		bul	����������
		est	��ɳ������
		dan	������
		fin	������
		cs	�ݿ���
		rom	����������
		slo	˹����������
		swe	�����
		hu	��������
		cht	��������
		vie	Խ����
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
         * ����û���κ����ݷ���
         * ʹ��try
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
