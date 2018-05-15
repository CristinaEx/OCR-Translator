package cristinae.dictionary;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

public class TranslatorForGoogle implements Translator{
	public final static int TRANSLATOR_TYPE = 201;
	
    /**
     * Key -> Value
     * ����     -> ���ʱ�ʾ
     */
    public static final Map<String,String> LANGUAGE = new HashMap<String,String>();
    
    private String from = "ja";
    
    private String to = "zh-CN";
     
    static {
        LANGUAGE.put("������������", "sq");
        LANGUAGE.put("��������", "ar");
        LANGUAGE.put("�����ݽ���", "az");
        LANGUAGE.put("��������", "ga");
        LANGUAGE.put("��ɳ������", "et");
        LANGUAGE.put("��˹����", "eu");
        LANGUAGE.put("�׶���˹��", "be");
        LANGUAGE.put("����������", "bg");
        LANGUAGE.put("������", "is");
        LANGUAGE.put("������", "pl");
        LANGUAGE.put("��˹��", "fa");
        LANGUAGE.put("������", "af");
        LANGUAGE.put("�ϷǺ�����", "af");
        LANGUAGE.put("������", "da");
        LANGUAGE.put("����", "de");
        LANGUAGE.put("����", "ru");
        LANGUAGE.put("����", "fr");
        LANGUAGE.put("���ɱ���", "tl");
        LANGUAGE.put("������", "fi");
        LANGUAGE.put("��³������", "ka");
        LANGUAGE.put("�ż�������", "gu");
        LANGUAGE.put("���ؿ���¶���", "ht");
        LANGUAGE.put("����", "ko");
        LANGUAGE.put("������", "nl");
        LANGUAGE.put("����������", "gl");
        LANGUAGE.put("��̩��������", "ca");
        LANGUAGE.put("�ݿ���", "cs");
        LANGUAGE.put("���ɴ���", "kn");
        LANGUAGE.put("���޵�����", "hr");
        LANGUAGE.put("������", "la");
        LANGUAGE.put("����ά����", "lv");
        LANGUAGE.put("������", "lo");
        LANGUAGE.put("��������", "lt");
        LANGUAGE.put("����������", "ro");
        LANGUAGE.put("�������", "mt");
        LANGUAGE.put("������", "ms");
        LANGUAGE.put("�������", "mk");
        LANGUAGE.put("�ϼ�����", "bn");
        LANGUAGE.put("Ų����", "no");
        LANGUAGE.put("��������", "pt");
        LANGUAGE.put("����", "ja");
        LANGUAGE.put("�����", "sv");
        LANGUAGE.put("����ά����", "sr");
        LANGUAGE.put("������", "eo");
        LANGUAGE.put("˹�工����", "sk");
        LANGUAGE.put("˹����������", "sl");
        LANGUAGE.put("˹��ϣ����", "sw");
        LANGUAGE.put("̩¬����", "te");
        LANGUAGE.put("̩�׶���", "ta");
        LANGUAGE.put("̩��", "th");
        LANGUAGE.put("��������", "tr");
        LANGUAGE.put("����ʿ��", "cy");
        LANGUAGE.put("�ڶ�����", "ur");
        LANGUAGE.put("�ڿ�����", "uk");
        LANGUAGE.put("ϣ������", "iw");
        LANGUAGE.put("ϣ����", "el");
        LANGUAGE.put("��������", "es");
        LANGUAGE.put("��������", "hu");
        LANGUAGE.put("����������", "hy");
        LANGUAGE.put("�������", "it");
        LANGUAGE.put("�������", "yi");
        LANGUAGE.put("ӡ����", "hi");
        LANGUAGE.put("ӡ����", "id");
        LANGUAGE.put("Ӣ��", "en");
        LANGUAGE.put("Խ����", "vi");
        LANGUAGE.put("���ķ���", "zh-TW");
        LANGUAGE.put("���ļ���", "zh-CN");
    }
    /**
     * GET �ȸ跭��
     * @param word ��������ַ���
     * @param from Դ����
     * @param to Ŀ������
     * @return ������JSON�ַ���
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
        	 * ��ʱarrayӵ��ȫ����Ϣ
        	 */
        	JSONArray array = new JSONArray(result);
        	//��ʱarrayӵ�з�����Ϣ
            array = array.getJSONArray(0);
            //���
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
