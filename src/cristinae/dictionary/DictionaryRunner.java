package cristinae.dictionary;

import java.awt.Color;
import java.io.File;

/*
 * 接受翻译一块区域的指令
 */
public class DictionaryRunner implements Runnable{

	private Ocr ocr;
	private Translator translator;
	private ScreenShoter shoter;
	private int delay;
	private Color wordColor;
	/*
	 * delay > 0 : 延时秒数
	 * delay = 0 : 不延时
	 * delay = -1 : 用自定义按键控制截图频率
	 */
	//字幕板透明度
	/*
	 * 0f~1.0f
	 */
	private boolean isTransparent;
	DictionaryRunner(Ocr ocr,Translator translator,ScreenShoter shoter,int delay,boolean isTransparent,Color color)
	{
		this.ocr = ocr;
		this.translator = translator;
		this.shoter = shoter;
		this.delay = delay;
		this.isTransparent = isTransparent;
		this.wordColor = color;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DictionaryWindow dw = new DictionaryWindow(this.shoter,this.isTransparent);
		dw.setVisible(true);
		//debug	
		while(true) {
			/*
			 * 截图保存的文件夹
			 */
			this.shoter.screenshot("screenshot\\", "screenshot.png");
			File imgFile = new File("screenshot\\" + "screenshot.png");
			dw.paint(this.translator.translate(this.ocr.getOcrResult(imgFile)),this.wordColor);
			if(this.delay > 0) {
				try {
					Thread.sleep(this.delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
