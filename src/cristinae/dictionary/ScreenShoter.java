package cristinae.dictionary;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

/*
 * ÿ��ScreenShotֻ����һ������Ľ�ͼ����
 * x1,y1
 *     
 *           x2,y2
 */
public class ScreenShoter {
	/*
	 * ȷ����Ҫ��ͼ��λ��
	 */
	public int position[] = null;
	public void check() {
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<int[]> result = exec.submit(new Callable<int[]>() {
			@Override
			public int[] call() throws Exception {  
				int result[] = null;
				try {
					ScreenShotWindow ssw = new ScreenShotWindow();
					ssw.setVisible(true);
					//��֤���߳������ݷ���
					while(result == null)
					{
						result = ssw.result;
						Thread.sleep(1000);
					}
					ssw.close();
				} catch (AWTException e) {
					e.printStackTrace();
				}
		        return result;
		    }
		});
		try {
			this.position =  result.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * ��ȡһ�Ž�ͼ�������䱣���ڸ�Ŀ¼��
	 */
	public void screenshot(String folder, String fileName)
	{
		if(position == null)return;
		if(position[0] + position[1] + position[2] + position[3]== 0)return;
        BufferedImage image;
		try {
			image = new Robot().createScreenCapture(new Rectangle(position[0],position[1],position[2],position[3]));
			// ��ͼ�����·�� 
	        File screenFile = new File(folder + fileName);    
	        if(!folder.isEmpty())
	        {
	        	// ���·��������,�򴴽�  
		        if (!screenFile.getParentFile().exists()) {  
		            screenFile.getParentFile().mkdirs();  
		        } 
	        }
	        //�ж��ļ��Ƿ���ڣ������ھʹ����ļ�
	        if(!screenFile.exists()&& !screenFile .isDirectory()) {
	            screenFile.mkdir();
	        }
	        File f = new File(folder + fileName);        
	        try {
				ImageIO.write(image, "png", f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
	
	/*
	 * ��ȡһ�Ž�ͼ�������䱣���ڸ�Ŀ¼��
	 * �ý�ͼΪ��ǰ��Ļ�����posi[0]posi[1] <-> posi[2]posi[3]
	 */
	public void screenshot(String folder, String fileName,int posi[])
	{
		if(posi == null)return;
		if(posi[0] + posi[1] + posi[2] + posi[3]== 0)return;
        BufferedImage image;
		try {
			image = new Robot().createScreenCapture(new Rectangle(posi[0],posi[1],posi[2],posi[3]));
			// ��ͼ�����·�� 
	        File screenFile = new File(folder + fileName);    
	        if(!folder.isEmpty())
	        {
	        	// ���·��������,�򴴽�  
		        if (!screenFile.getParentFile().exists()) {  
		            screenFile.getParentFile().mkdirs();  
		        } 
	        }
	        //�ж��ļ��Ƿ���ڣ������ھʹ����ļ�
	        if(!screenFile.exists()&& !screenFile .isDirectory()) {
	            screenFile.mkdir();
	        }
	        File f = new File(folder + fileName);        
	        try {
				ImageIO.write(image, "png", f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
}
		