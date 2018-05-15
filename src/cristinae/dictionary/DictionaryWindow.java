package cristinae.dictionary;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;
import javax.swing.event.MouseInputListener;

public class DictionaryWindow  extends JWindow{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScreenShoter shoter;
	/**
	 * delay > 0 -> ��ʱdelay��
	 * delay = 0 -> ��������ʱ
	 * delay = -1 -> ��������
	 */
	private boolean isTransparent;
	DictionaryWindow(ScreenShoter shoter,boolean isTransparent)
	{
		//��Ļ���Ƿ�͸��
		this.isTransparent = isTransparent;
		//��ȡ��Ļ�ߴ�
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.shoter = shoter;
		//���ô���λ��
		//ȷ�Ϻ�����չ����������չ
		//��ѡ��������չ
		if(d.width > 2 * (this.shoter.position[1] - this.shoter.position[3])) {
			//���������Ϸ�����Ļ��1/2���·�
			if(d.height < 2 * this.shoter.position[1]) {
				this.setLocation(shoter.position[0] , shoter.position[1] * 2 - shoter.position[3]);
			}
			//���������Ϸ�����Ļ��1/2���Ϸ�
			else {
				this.setLocation(shoter.position[0] , shoter.position[3]);
			}
		}
		//��ѡ�������չ
		else {
			//������������������
			if(d.width > 2 * this.shoter.position[0]) {
				this.setLocation(shoter.position[2] , shoter.position[1]);
			}
			//����������������ұ�
			else {
				this.setLocation(shoter.position[0] * 2 -  shoter.position[2], shoter.position[1]);
			}
		}
		this.setSize(shoter.position[2] - shoter.position[0],shoter.position[3] - shoter.position[1]);
		//ʹ�����Զ�ڴ������Ϸ�
		this.setAlwaysOnTop(true);
		/*
		 * ��������϶�Ч��
		 */
		//����¼�������
	    MouseEventListener mouseListener = new MouseEventListener(this);
		this.addMouseListener(mouseListener);
	    this.addMouseMotionListener(mouseListener);

	}
	public void paint(String word , Color color) {
		/** 
		 * �����ִ�ӡ��ͼ��
		 */
		//����ͼ��
		//��ȡ��Ļ
		//��ʱͼ�����ڻ�����Ļ���������Ļ��˸
		Image tempImage2=createImage(this.getWidth(),this.getHeight());
		Graphics2D g = (Graphics2D) tempImage2.getGraphics();
		int posi[] = {this.getX(),this.getY(),this.getX() + this.getWidth(),this.getY() + this.getHeight()};
		if(this.isTransparent) {
			this.setVisible(false);
			this.shoter.screenshot("screenshot\\", "screenshot.png", posi);
			this.setVisible(true);
			Image image = null;
			try {
				image = ImageIO.read(new File("screenshot\\" + "screenshot.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(image, 0, 0, null);
		}
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		/*
		 * ��������
		 * ���嶨��
		 */
		/*
		 * ���������������Ż�����
		 */
		g.setColor(color);
		int size = (int) Math.sqrt(this.getHeight() * this.getWidth() / word.length());
		/*
		 * �����·���չ
		if(size < 17) {
			this.shoter.position[2] = this.shoter.position[2] * 17/size;
			this.shoter.position[3] = this.shoter.position[3] * 17/size;
			this.setSize(shoter.position[2] - shoter.position[0],shoter.position[3] - shoter.position[1]);
			size = (int) Math.sqrt(this.getHeight() * this.getWidth() / word.length()) - 1;
		}
		*/
		
		Font font=new Font("����",Font.PLAIN,size);  
        g.setFont(font);  
        // �������ֳ��ȣ�������е�x������
        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(word);
        // �����
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // ����
        int line = textWidth / this.getWidth();
        // ÿ�е�����
        int line_num = word.length() / (line + 1);
        for(int index = 0;index < line;index++) {
        	g.drawString(word.substring(0,line_num),size + 1,(size + 1) * (index + 1));
        	word = word.substring(line_num,word.length());
        }
        // ��ʾ���������ͼƬ�ϵ�λ��(x,y) .��һ���������õ����ݡ� 
        g.drawString(word,size + 1,(size+1) *( line + 1));  
		this.getGraphics().drawImage(tempImage2,0,0,this);
	}
}
class MouseEventListener implements MouseInputListener {
    
    Point origin;
    //�����ק��Ҫ�ƶ���Ŀ�����
    DictionaryWindow frame;
     
    public MouseEventListener(DictionaryWindow frame) {
      this.frame = frame;
      origin = new Point();
    }
     
    @Override
    public void mouseClicked(MouseEvent e) {}
 
    /**
    * ��¼��갴��ʱ�ĵ�
    */
    @Override
    public void mousePressed(MouseEvent e) {
      origin.x = e.getX(); 
      origin.y = e.getY();
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {}
 
    /**
    * ����ƽ�������ʱ���������ͼ��
    * �ڱ�Ե��Ϊ�ƶ�ͼ�꣨��Ե5px���ã�
    * ��������Ϊ��չͼƬ
    */
    @Override
    public void mouseEntered(MouseEvent e) {
    	// �ڿ���߱�Ե8px��
    	if (e.getX() < 8) {
    		// �ڿ��ϱ߱�Ե8px��
        	if (e.getY() < 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
    	}
    	// �ڿ��ұ߱�Ե8px��
    	else if (e.getX() > frame.getWidth() - 8) {
        	// �ڿ��±߱�Ե8px��
        	if (e.getY() > frame.getHeight() - 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
    	}
    	// �ڿ��ϱ߱�Ե8px��
    	//else if (e.getY() < 8) this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
    	// �ڿ��±߱�Ե8px��
    	//else if (e.getY() > frame.getHeight() - 8)this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
    	else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }
     
    /**
    * ����Ƴ�������ʱ���������ͼ��ΪĬ��ָ��
    */
    @Override
    public void mouseExited(MouseEvent e) {
      this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
 
    /**
    * ����ڱ�������קʱ�����ô��ڵ�����λ��
    * �����µ�����λ�� = �ƶ�ǰ����λ��+�����ָ�뵱ǰ����-��갴��ʱָ���λ�ã�
    */
    @Override
    public void mouseDragged(MouseEvent e) {
    	int locationX = 0;
    	int locationY = 0;
    	// �ڿ���߱�Ե8px��    	
    	if (e.getX() < 8) {
    		// �ڿ��ϱ߱�Ե8px��
        	if (e.getY() < 8) {
        		locationX = e.getX() - origin.x;
            	locationY = e.getY() - origin.y;
        		this.frame.setSize(this.frame.getWidth() - e.getX() + origin.x,this.frame.getHeight() -  e.getY() + origin.y);
        	} 		
        	//else {
        	//	locationX = e.getX() - origin.x;
            //	locationY = 0;
        	//	this.frame.setSize(this.frame.getWidth() - e.getX() + origin.x,0);
        	//}
    	}
    	// �ڿ��ұ߱�Ե8px��
    	else if (e.getX() > frame.getWidth() - 8) {
        	// �ڿ��±߱�Ե8px��
        	if (e.getY() > frame.getHeight() - 8) {
        		this.frame.setSize(this.frame.getWidth() + e.getX() - origin.x,this.frame.getHeight() +  e.getY() - origin.y);
        		return;
        	}
        	//else;
    	}
    	// �ڿ��ϱ߱�Ե8px��
    	//else if (e.getY() < 8);
    	// �ڿ��±߱�Ե8px��
    	//else if (e.getY() > frame.getHeight() - 8);
    	else {
    		locationX = e.getX() - origin.x;
        	locationY = e.getY() - origin.y;
    	}
    	Point p = this.frame.getLocation();
        this.frame.setLocation(
        p.x + locationX, 
        p.y + locationY); 
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
    	// �ڿ���߱�Ե8px��
    	if (e.getX() < 8) {
    		// �ڿ��ϱ߱�Ե8px��
        	if (e.getY() < 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
    	}
    	// �ڿ��ұ߱�Ե8px��
    	else if (e.getX() > frame.getWidth() - 8) {
        	// �ڿ��±߱�Ե8px��
        	if (e.getY() > frame.getHeight() - 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
    	}
    	// �ڿ��ϱ߱�Ե8px��
    	//else if (e.getY() < 8) this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
    	// �ڿ��±߱�Ե8px��
    	//else if (e.getY() > frame.getHeight() - 8)this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
    	else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }
 
}

