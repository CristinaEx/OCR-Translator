package cristinae.dictionary;

import java.awt.Color;

public class DictionRunnerBuilder {
	/**
	 * ���õ�OCR
	 */
	private Ocr ocr = null;
	/**
	 * ���õķ���
	 */
	private Translator dic;
	/**
	 * ��ʱ
	 */
	private int delay = 0;
	/**
	 * ��Ļ͸��������
	 */
	private boolean isTransparent = true;
	/**
	 * ��Ļ��ɫ
	 */
	private Color color = null;
	/*
	 * ���캯����ʼ��
	 */
	DictionRunnerBuilder(Ocr ocr,Translator dic,int delay,boolean isTransparent,Color wordColor){
		this.ocr = ocr;
		this.dic = dic;
		this.delay = delay;
		this.isTransparent = isTransparent;
		this.color = wordColor;
	}
	/*
	 * �������뷽��
	 */
	DictionRunnerBuilder(){}
	public Runnable getRunnable() {
		ScreenShoter shoter = new ScreenShoter();
		shoter.check();
		return new DictionaryRunner(this.ocr,
				this.dic,
				shoter,
				this.delay,
				this.isTransparent,
				this.color) ;
	}
	/*
	 * debug
	 */
	public void test() {
		System.out.println(Ocr.OCR_TYPE);
		System.out.println(Translator.TRANSLATOR_TYPE);
		System.out.println(delay);
		System.out.println(isTransparent);
		System.out.println(color.getRGB());
	}
}
