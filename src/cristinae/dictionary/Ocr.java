package cristinae.dictionary;

import java.io.File;

/*
 * OCR�ӿ�
 * ʵ���޸�����
 * �ͽ���File����String������ʶ����
 */
public interface Ocr {
	
	/*
	 * �ж�ocr����
	 */
	public final int OCR_TYPE = 100;
	
	/*
	 * �÷�������һ���ļ��������ظ�ͼƬ�ļ���ʶ����ַ�
	 * �ַ�ÿ����'\n'�ָ�
	 */
	public String getOcrResult(File imgFile);
	
	/*
	 * �޸ĵ�ǰʶ�����������
	 */
	public void setLanguageType(String language);    
	
	
}
