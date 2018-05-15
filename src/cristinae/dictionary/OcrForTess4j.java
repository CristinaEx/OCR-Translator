package cristinae.dictionary;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 基于tess4j的OCR
 * 其中需要有tessdata在根目录下作为训练好的数据
 */
public class OcrForTess4j implements Ocr{
	public final static int OCR_TYPE = 101;
	/*
	 * 接受需要识别的图片文件和识别语言
	 * 返回识别结果
	 */
	private ITesseract instance;
	/*
	 * chi_sim,jpn,eng
	 */
	private String language = "jpn";
	/*
	 * 初始化
	 */
	OcrForTess4j()
	{
		//debug
		//注意:不被error干扰
		//眼不见为净
		//会产生error，但不影响程序运行
		System.err.close();
		//重定向
		System.setErr(System.out);
		this.instance = new Tesseract();
		// 指定识别语种
		this.instance.setLanguage(this.language);
	}
	
	public void setLanguageType(String language)
	{
		this.language = language;
		this.instance.setLanguage(this.language);
	}
	
	/*
	 * language可选chi_sim,jap,eng
	 * 若识别不出获产生异常返回null
	 */
	public String getOcrResult(File imgFile)
	{
		//chi_tra 有问题
        String ocrResult = null;
		try {
			ocrResult = this.instance.doOCR(imgFile);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 准确性不是特别高
		 * 最好对经常识别错误的特殊符号进行处理
		 */
		ocrResult = ocrResult.replaceAll("[\\|\'_$:‘’：]\"、", " ");
		return ocrResult;
	}
}
