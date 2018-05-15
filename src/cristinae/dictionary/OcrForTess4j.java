package cristinae.dictionary;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * ����tess4j��OCR
 * ������Ҫ��tessdata�ڸ�Ŀ¼����Ϊѵ���õ�����
 */
public class OcrForTess4j implements Ocr{
	public final static int OCR_TYPE = 101;
	/*
	 * ������Ҫʶ���ͼƬ�ļ���ʶ������
	 * ����ʶ����
	 */
	private ITesseract instance;
	/*
	 * chi_sim,jpn,eng
	 */
	private String language = "jpn";
	/*
	 * ��ʼ��
	 */
	OcrForTess4j()
	{
		//debug
		//ע��:����error����
		//�۲���Ϊ��
		//�����error������Ӱ���������
		System.err.close();
		//�ض���
		System.setErr(System.out);
		this.instance = new Tesseract();
		// ָ��ʶ������
		this.instance.setLanguage(this.language);
	}
	
	public void setLanguageType(String language)
	{
		this.language = language;
		this.instance.setLanguage(this.language);
	}
	
	/*
	 * language��ѡchi_sim,jap,eng
	 * ��ʶ�𲻳�������쳣����null
	 */
	public String getOcrResult(File imgFile)
	{
		//chi_tra ������
        String ocrResult = null;
		try {
			ocrResult = this.instance.doOCR(imgFile);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ׼ȷ�Բ����ر��
		 * ��öԾ���ʶ������������Ž��д���
		 */
		ocrResult = ocrResult.replaceAll("[\\|\'_$:������]\"��", " ");
		return ocrResult;
	}
}
