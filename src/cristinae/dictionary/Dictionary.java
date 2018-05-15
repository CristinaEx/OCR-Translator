package cristinae.dictionary;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Dictionary {
	public static String filePath = "userdata//";
	public static ExecutorService exec = Executors.newCachedThreadPool();
	/*
	 * delay > 0 : 延时秒数
	 * delay = 0 : 不延时
	 * delay = -1 : 用自定义按键控制截图频率
	 */
	public void addRunner(Ocr ocr, Translator translator, int delay, boolean isTransparent,Color wordColor) {
		ScreenShoter shoter = new ScreenShoter();
		shoter.check();
		Runnable runner = new DictionaryRunner(ocr
				,translator
				, shoter
				, delay
				, isTransparent
				, wordColor);	
		Dictionary.exec.execute(runner);
	}
	/*
	 * 导入文件中指定的OCR和TRANSLATOR
	 * 设置内容：
	 * String name ; int ocrIndex ; int translatorIndex ; int delay ; boolean isTransparent ; 
	 * 并返回设置名
	 */
	public void addRunner(String fileName) {
		Ocr ocr = null;
		Translator translator = null;
		int delay = 0;
		boolean tr = true;
		Color color = null;
		try {
			JSONObject objectMain = new JSONObject(FileUtils.readFileToString(new File(Dictionary.filePath + fileName + ".json"), "UTF-8"));
			JSONObject object = objectMain.getJSONObject("ocr");
			if(object.getInt("type") == OcrForTess4j.OCR_TYPE) {
				ocr = new OcrForTess4j();
			}
			else if(object.getInt("type") == OcrForBaidu.OCR_TYPE) {
				ocr = new OcrForBaidu(object.getString("appId"),object.getString("apiKey"),object.getString("secretKey"));
			}
			ocr.setLanguageType(object.getString("language"));
			object = objectMain.getJSONObject("translator");
			if(object.getInt("type") == TranslatorForGoogle.TRANSLATOR_TYPE)
				translator = new TranslatorForGoogle();
			else if(object.getInt("type") == TranslatorForBaidu.TRANSLATOR_TYPE) {
				translator = new TranslatorForBaidu(object.getString("appId"),object.getString("key"));
			}
			translator.setFrom(object.getString("from"));
			translator.setTo(object.getString("to"));
			delay = objectMain.getInt("delay");
			object = objectMain.getJSONObject("color");
			color = new Color(object.getInt("r"),object.getInt("g"),object.getInt("b"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			ocr = new OcrForTess4j();
			translator = new TranslatorForGoogle();
			color = Color.BLACK;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(new DictionRunnerBuilder(
				ocr,
				translator,
				delay,
				tr,
				color).getRunnable()).start();
	}
	public static List<String> getFileName() {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(filePath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            list.add(file.getName().substring(0, file.getName().length() - 5));
        }
        return list;
    }
}
