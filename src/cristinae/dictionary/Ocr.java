package cristinae.dictionary;

import java.io.File;

/*
 * OCR接口
 * 实现修改语言
 * 和接受File返回String的文字识别功能
 */
public interface Ocr {
	
	/*
	 * 判断ocr类型
	 */
	public final int OCR_TYPE = 100;
	
	/*
	 * 该方法接受一个文件，并返回该图片文件内识别的字符
	 * 字符每行有'\n'分割
	 */
	public String getOcrResult(File imgFile);
	
	/*
	 * 修改当前识别的语言类型
	 */
	public void setLanguageType(String language);    
	
	
}
