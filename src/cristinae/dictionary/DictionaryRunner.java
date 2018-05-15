package cristinae.dictionary;

import java.awt.Color;
import java.io.File;

/*
 * ���ܷ���һ�������ָ��
 */
public class DictionaryRunner implements Runnable{

	private Ocr ocr;
	private Translator translator;
	private ScreenShoter shoter;
	private int delay;
	private Color wordColor;
	/*
	 * delay > 0 : ��ʱ����
	 * delay = 0 : ����ʱ
	 * delay = -1 : ���Զ��尴�����ƽ�ͼƵ��
	 */
	//��Ļ��͸����
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
			 * ��ͼ������ļ���
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
