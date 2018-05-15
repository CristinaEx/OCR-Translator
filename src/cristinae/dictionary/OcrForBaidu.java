package cristinae.dictionary;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/**
 * ���ڰٶȵ�Ocr
 * ��������ʹ��
 * ͼƬ���min:15px , max:4096px
 */
public class OcrForBaidu implements Ocr{
	public final static int OCR_TYPE = 102;
	private AipOcr client;
	/*
	 * ʶ �� �� �� �� �ͣ� Ĭ �� Ϊ CHN_ENG�� 
	 * �� ѡ ֵ �� ����CHN_ENG�� �� Ӣ �� �� �ϣ�
	 * ENG��Ӣ�ģ�POR�� �� �� �� �
	 * FRE���� �
	 * GER�� �� �
	 * ITA�� �� �� �� �
	 * SPA�� �� �� �� �
	 * RUS���� �
	 * JAP�� �� �� 
	 */
	private String languageType = "JAP";
	//�Ƿ��⳯��
	private String detectDirection = "false";
	//�Ƿ�������
	private String detectLanguage = "false";
	
	OcrForBaidu(String appId ,String apiKey, String secretKey)
	{
		// ��ʼ��һ��OcrClient 
		this.client = new AipOcr(appId,apiKey,secretKey);
		// ��ѡ�������������Ӳ��� 
		//�������ӵĳ�ʱʱ�䣨��λ�����룩 
		client.setConnectionTimeoutInMillis(2000);
		//ͨ���򿪵����Ӵ������ݵĳ�ʱʱ�䣨�� λ�����룩
		client.setSocketTimeoutInMillis(60000);
	}
	OcrForBaidu()
	{
		//�·�null�������뿪������Ϣ
		this.client = new AipOcr(null,null,null);
		// ��ѡ�������������Ӳ��� 
		//�������ӵĳ�ʱʱ�䣨��λ�����룩 
		client.setConnectionTimeoutInMillis(2000);
		//ͨ���򿪵����Ӵ������ݵĳ�ʱʱ�䣨�� λ�����룩
		client.setSocketTimeoutInMillis(60000);
	}
	
	/*
	 * �����������
	 */
	public void setLanguageType(String languageType)
	{
		this.languageType = languageType;
	}
	
	public void setDetectDirection(String detectDirection)
	{
		this.detectDirection = detectDirection;
	}
	
	public void setDetectLanguage(String detectLanguage)
	{
		this.detectLanguage = detectLanguage;
	}
	
	public String getOcrResult(File imgFile)
	{
		JSONObject jsonObject = this.basicGeneralRecognition(imgFile);
		JSONArray jsonArray = jsonObject.getJSONArray("words_result");
		String result = new String();
		for(int index = 0;index < jsonArray.length();index++) {
			JSONObject ob = jsonArray.getJSONObject(index);
			result += ob.getString("words");
			result += '\n';
		}
		//System.out.println(result);
		return result;
	}
	
	//ͨ������ʶ��
	public JSONObject basicGeneralRecognition(File imgFile){ // �Զ���������� 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.basicGeneral(file, options); 
		return response;
		}
	
	//ͨ������ʶ��λ����Ϣ��
	public JSONObject generalRecognition(File imgFile){ // �Զ���������� 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.general(file, options); 
		return response;
		}
		
	//ͨ������ʶ�𣨺���Ƨ�ְ棩
	//����Ѷ��
	public JSONObject enhancedGeneral(File imgFile){ // �Զ���������� 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.enhancedGeneral(file, options); 
		return response;
		}
	
	//ͨ������ʶ�𣨸߾��Ȱ�,��λ����Ϣ��
	//����Ѷ��
	public JSONObject accurateGeneral(File imgFile){ // �Զ���������� 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.accurateGeneral(file, options);
		return response;
		}
		
	//ͨ������ʶ�𣨸߾��Ȱ棩
	//����Ѷ��
	public JSONObject basicAccurateGeneral(File imgFile){ // �Զ���������� 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.basicAccurateGeneral(file, options);
		return response;
		}
	
	/*
	 * ���ô���
	 * error_code error_msg ��ע 
	 * SDK100 imagesizeerror ͼƬ��С���� 
	 * SDK101 imagelengtherror ͼƬ�߳�������Ҫ�� 
	 * SDK102 readimagefileerror ��ȡͼƬ�ļ����� 
	 * SDK108 connection or read data timeout ���ӳ�ʱ���ȡ���ݳ�ʱ 
	 * SDK109 unsupportedimageformat ��֧�ֵ�ͼƬ��ʽ
	 * ������ ������Ϣ ���� 
	 * 216103 paramtoolong ����̫�� 
	 * 216110 appidnotexist APPID������ 
	 * 216111 invaliduserid �Ƿ��û�ID 
	 * 216200 emptyimage �յ�ͼƬ 
	 * 216201 imageformaterror ͼƬ��ʽ���� 
	 * 216202 imagesizeerror ͼƬ��С���� 
	 * 282100 imagetranscodeerror ͼƬѹ��ת�����
	 */
	
	// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ���
    public static byte[] imageFileToByteArray(File imgFile) {  
        byte[] data = null;    
        // ��ȡͼƬ�ֽ�����    
        try {     
            FileInputStream in = new FileInputStream(imgFile);    
            data = new byte[in.available()];    
            in.read(data);    
            in.close();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        return data;
    }   
    
}
