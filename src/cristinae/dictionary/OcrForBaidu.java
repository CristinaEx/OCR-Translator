package cristinae.dictionary;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/**
 * 基于百度的Ocr
 * 必须联网使用
 * 图片规格：min:15px , max:4096px
 */
public class OcrForBaidu implements Ocr{
	public final static int OCR_TYPE = 102;
	private AipOcr client;
	/*
	 * 识 别 语 言 类 型， 默 认 为 CHN_ENG。 
	 * 可 选 值 包 括：CHN_ENG： 中 英 文 混 合；
	 * ENG：英文；POR： 葡 萄 牙 语；
	 * FRE：法 语；
	 * GER： 德 语；
	 * ITA： 意 大 利 语；
	 * SPA： 西 班 牙 语；
	 * RUS：俄 语；
	 * JAP： 日 语 
	 */
	private String languageType = "JAP";
	//是否检测朝向
	private String detectDirection = "false";
	//是否检测语言
	private String detectLanguage = "false";
	
	OcrForBaidu(String appId ,String apiKey, String secretKey)
	{
		// 初始化一个OcrClient 
		this.client = new AipOcr(appId,apiKey,secretKey);
		// 可选：设置网络连接参数 
		//建立连接的超时时间（单位：毫秒） 
		client.setConnectionTimeoutInMillis(2000);
		//通过打开的连接传输数据的超时时间（单 位：毫秒）
		client.setSocketTimeoutInMillis(60000);
	}
	OcrForBaidu()
	{
		//下方null处需填入开发者信息
		this.client = new AipOcr(null,null,null);
		// 可选：设置网络连接参数 
		//建立连接的超时时间（单位：毫秒） 
		client.setConnectionTimeoutInMillis(2000);
		//通过打开的连接传输数据的超时时间（单 位：毫秒）
		client.setSocketTimeoutInMillis(60000);
	}
	
	/*
	 * 设置请求参数
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
	
	//通用文字识别
	public JSONObject basicGeneralRecognition(File imgFile){ // 自定义参数定义 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.basicGeneral(file, options); 
		return response;
		}
	
	//通用文字识别含位置信息版
	public JSONObject generalRecognition(File imgFile){ // 自定义参数定义 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.general(file, options); 
		return response;
		}
		
	//通用文字识别（含生僻字版）
	//无免费额度
	public JSONObject enhancedGeneral(File imgFile){ // 自定义参数定义 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.enhancedGeneral(file, options); 
		return response;
		}
	
	//通用文字识别（高精度版,含位置信息）
	//无免费额度
	public JSONObject accurateGeneral(File imgFile){ // 自定义参数定义 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.accurateGeneral(file, options);
		return response;
		}
		
	//通用文字识别（高精度版）
	//无免费额度
	public JSONObject basicAccurateGeneral(File imgFile){ // 自定义参数定义 
		HashMap<String,String>options=new HashMap<String,String>(); 
		options.put("detect_direction",this.detectDirection); 
		options.put("language_type",this.languageType);
		options.put("detect_language",this.detectLanguage); 
		byte[] file = OcrForBaidu.imageFileToByteArray(imgFile);
		JSONObject response = client.basicAccurateGeneral(file, options);
		return response;
		}
	
	/*
	 * 常用错误
	 * error_code error_msg 备注 
	 * SDK100 imagesizeerror 图片大小超限 
	 * SDK101 imagelengtherror 图片边长不符合要求 
	 * SDK102 readimagefileerror 读取图片文件错误 
	 * SDK108 connection or read data timeout 连接超时或读取数据超时 
	 * SDK109 unsupportedimageformat 不支持的图片格式
	 * 错误码 错误信息 描述 
	 * 216103 paramtoolong 参数太长 
	 * 216110 appidnotexist APPID不存在 
	 * 216111 invaliduserid 非法用户ID 
	 * 216200 emptyimage 空的图片 
	 * 216201 imageformaterror 图片格式错误 
	 * 216202 imagesizeerror 图片大小错误 
	 * 282100 imagetranscodeerror 图片压缩转码错误
	 */
	
	// 将图片文件转化为字节数组字符串
    public static byte[] imageFileToByteArray(File imgFile) {  
        byte[] data = null;    
        // 读取图片字节数组    
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
