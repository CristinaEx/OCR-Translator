package cristinae.dictionary;

import java.awt.Color;

public class DictionRunnerBuilder {
	/**
	 * 采用的OCR
	 */
	private Ocr ocr = null;
	/**
	 * 采用的翻译
	 */
	private Translator dic;
	/**
	 * 延时
	 */
	private int delay = 0;
	/**
	 * 字幕透明度设置
	 */
	private boolean isTransparent = true;
	/**
	 * 字幕颜色
	 */
	private Color color = null;
	/*
	 * 构造函数初始化
	 */
	DictionRunnerBuilder(Ocr ocr,Translator dic,int delay,boolean isTransparent,Color wordColor){
		this.ocr = ocr;
		this.dic = dic;
		this.delay = delay;
		this.isTransparent = isTransparent;
		this.color = wordColor;
	}
	/*
	 * 后面设入方法
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
