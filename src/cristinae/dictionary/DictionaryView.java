package cristinae.dictionary;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.event.MouseInputListener;

public class DictionaryView extends JDialog{
	/**
	 * 未完成，无法更新
	 */
	private static final long serialVersionUID = 1L;
	private ScreenShoter shoter;
	DictionaryView(ScreenShoter shoter,float transparent)
	{
		//字幕板透明度
		this.setUndecorated(true);
		this.setBackground(Color.GRAY);
		this.setOpacity(transparent);
		//获取屏幕尺寸
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.shoter = shoter;
		//设置窗口位置
		//确认横向扩展或者纵向扩展
		//若选择纵向扩展
		if(d.width > 2 * (this.shoter.position[1] - this.shoter.position[3])) {
			//若区域最上方在屏幕的1/2的下方
			if(d.height < 2 * this.shoter.position[1]) {
				this.setLocation(shoter.position[0] , shoter.position[1] * 2 - shoter.position[3]);
			}
			//若区域最上方在屏幕的1/2的上方
			else {
				this.setLocation(shoter.position[0] , shoter.position[3]);
			}
		}
		//若选择横向扩展
		else {
			//如果最左边在区域的左边
			if(d.width > 2 * this.shoter.position[0]) {
				this.setLocation(shoter.position[2] , shoter.position[1]);
			}
			//如果最左边在区域的右边
			else {
				this.setLocation(shoter.position[0] * 2 -  shoter.position[2], shoter.position[1]);
			}
		}
		this.setSize(shoter.position[2] - shoter.position[0],shoter.position[3] - shoter.position[1]);
		//使组件永远在窗口最上方
		this.setAlwaysOnTop(true);
		/*
		 * 设置鼠标拖动效果
		 */
		//鼠标事件处理类
	    MouseEventListener2 mouseListener = new MouseEventListener2(this);
		this.addMouseListener(mouseListener);
	    this.addMouseMotionListener(mouseListener);

	}
	 public void paint(Graphics g) {
	        super.paint(g);
	        //this.paint("word",Color.BLACK);
	        /*
	         * 未完成
	         */
	    }
	public void paint(String word , Color color) {
		/** 
		 * 把文字打印到图层
		 */
		Image tempImage2=createImage(this.getWidth(),this.getHeight());
		Graphics2D g = (Graphics2D) tempImage2.getGraphics();
		g.setBackground(new Color(100,100,100,0));
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		/*
		 * 绘制文字
		 * 字体定制
		 */
		/*
		 * 计算所需行数，优化表现
		 */
		g.setColor(color);
		int size = (int) Math.sqrt(this.getHeight() * this.getWidth() / word.length());
		Font font=new Font("黑体",Font.BOLD,size);  
        g.setFont(font);  
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(word);
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 行数
        int line = textWidth / this.getWidth();
        // 每行的字数
        int line_num = word.length() / (line + 1);
        for(int index = 0;index < line;index++) {
        	g.drawString(word.substring(0,line_num),size + 1,(size + 1) * (index + 1));
        	word = word.substring(line_num,word.length());
        }
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。 
        g.drawString(word,size + 1,(size+1) *( line + 1));
		this.getGraphics().drawImage(tempImage2,0,0,this.getWidth(),this.getHeight(),null);
	}
}
class MouseEventListener2 implements MouseInputListener {
    
    Point origin;
    //鼠标拖拽想要移动的目标组件
    DictionaryView frame;
     
    public MouseEventListener2(DictionaryView frame) {
      this.frame = frame;
      origin = new Point();
    }
     
    @Override
    public void mouseClicked(MouseEvent e) {}
 
    /**
    * 记录鼠标按下时的点
    */
    @Override
    public void mousePressed(MouseEvent e) {
      origin.x = e.getX(); 
      origin.y = e.getY();
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {}
 
    /**
    * 鼠标移进标题栏时，设置鼠标图标
    * 在边缘则为移动图标（边缘5px作用）
    * 在中心则为扩展图片
    */
    @Override
    public void mouseEntered(MouseEvent e) {
    	// 在框左边边缘8px内
    	if (e.getX() < 8) {
    		// 在框上边边缘8px内
        	if (e.getY() < 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
    	}
    	// 在框右边边缘8px内
    	else if (e.getX() > frame.getWidth() - 8) {
        	// 在框下边边缘8px内
        	if (e.getY() > frame.getHeight() - 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
    	}
    	// 在框上边边缘8px内
    	//else if (e.getY() < 8) this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
    	// 在框下边边缘8px内
    	//else if (e.getY() > frame.getHeight() - 8)this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
    	else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }
     
    /**
    * 鼠标移出标题栏时，设置鼠标图标为默认指针
    */
    @Override
    public void mouseExited(MouseEvent e) {
      this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
 
    /**
    * 鼠标在标题栏拖拽时，设置窗口的坐标位置
    * 窗口新的坐标位置 = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
    */
    @Override
    public void mouseDragged(MouseEvent e) {
    	int locationX = 0;
    	int locationY = 0;
    	// 在框左边边缘8px内    	
    	if (e.getX() < 8) {
    		// 在框上边边缘8px内
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
    	// 在框右边边缘8px内
    	else if (e.getX() > frame.getWidth() - 8) {
        	// 在框下边边缘8px内
        	if (e.getY() > frame.getHeight() - 8) {
        		this.frame.setSize(this.frame.getWidth() + e.getX() - origin.x,this.frame.getHeight() +  e.getY() - origin.y);
        		return;
        	}
        	//else;
    	}
    	// 在框上边边缘8px内
    	//else if (e.getY() < 8);
    	// 在框下边边缘8px内
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
    	// 在框左边边缘8px内
    	if (e.getX() < 8) {
    		// 在框上边边缘8px内
        	if (e.getY() < 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
    	}
    	// 在框右边边缘8px内
    	else if (e.getX() > frame.getWidth() - 8) {
        	// 在框下边边缘8px内
        	if (e.getY() > frame.getHeight() - 8) {
        		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        	}
        	//else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
    	}
    	// 在框上边边缘8px内
    	//else if (e.getY() < 8) this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
    	// 在框下边边缘8px内
    	//else if (e.getY() > frame.getHeight() - 8)this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
    	else this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }
}
