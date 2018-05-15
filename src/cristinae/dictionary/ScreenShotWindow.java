package cristinae.dictionary;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
/*
* ��ͼ����
* Ŀ¼������:
* images/close.gif
* images/save.gif
*/
class ScreenShotWindow extends JWindow
{ 
	private int orgx, orgy, endx, endy;
	private BufferedImage image=null;
	private BufferedImage tempImage=null;
	private BufferedImage saveImage=null;
	private ToolsWindow tools=null;
	public int result[] = null;

	public ScreenShotWindow() throws AWTException{
			//��ȡ��Ļ�ߴ�
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			this.setBounds(0, 0, d.width, d.height);
			//��ȡ��Ļ
			Robot robot = new Robot();
			image = robot.createScreenCapture(new Rectangle(0, 0, d.width,d.height));
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//����ɿ�ʱ��¼���������꣬�����ز�������
					orgx = e.getX();
					orgy = e.getY();
					if(tools!=null){
						tools.setVisible(false);
					}
				}
				@Override
				public void mouseReleased(MouseEvent e) {
				//����ɿ�ʱ����ʾ��������
					if(tools==null){
						tools=new ToolsWindow(ScreenShotWindow.this,e.getX(),e.getY());
					}else{
						tools.setLocation(e.getX(),e.getY());
					}
					tools.setVisible(true);
					tools.toFront();
				}
			});
			this.addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					//����϶�ʱ����¼���겢�ػ洰��
					endx = e.getX();
					endy = e.getY();
					//��ʱͼ�����ڻ�����Ļ���������Ļ��˸
					Image tempImage2=createImage(ScreenShotWindow.this.getWidth(),ScreenShotWindow.this.getHeight());
					Graphics g = tempImage2.getGraphics();
					g.drawImage(tempImage, 0, 0, null);
					int x = Math.min(orgx, endx);
					int y = Math.min(orgy, endy);
					int width = Math.abs(endx - orgx)+1;
					int height = Math.abs(endy - orgy)+1;
					// ����1��ֹwidth��height0
					g.setColor(Color.BLUE);
					g.drawRect(x-1, y-1, width+1, height+1);
					//��1��1���˷�ֹͼƬ���ο򸲸ǵ�
					saveImage = image.getSubimage(x, y, width, height);
					g.drawImage(saveImage, x, y, null);
					ScreenShotWindow.this.getGraphics().drawImage(tempImage2,0,0,ScreenShotWindow.this);
				}
			});
	}

	@Override
	public void paint(Graphics g) {
		RescaleOp ro = new RescaleOp(0.8f, 0, null);
		tempImage = ro.filter(image, null);
		g.drawImage(tempImage, 0, 0, this);
	}
	//ͼ��ȷ��
	public void saveImage(){
		//��������
		int arr[] = {Math.min(orgx, endx), Math.min(orgy,endy), Math.max(orgx, endx), Math.max(orgy,endy)};
		this.result = arr;
		/*
		 * result[0] < result[2]
		 * result[1] < result[3]
		 */
		this.setVisible(false);
		this.tools.setVisible(false);
	}
	
	public void close(){
		//��������
		int arr[] = {0,0,0,0};
		this.result = arr;
		this.tools.setVisible(false);
	}
}
/*
* ��������
*/
@SuppressWarnings("serial")
class ToolsWindow extends JWindow{

	private ScreenShotWindow parent;

	public ToolsWindow(ScreenShotWindow parent,int x,int y) {
		this.parent=parent;
		this.init();
		this.setLocation(x, y);
		this.pack();
		this.setVisible(true);
	}

	private void init(){

		this.setLayout(new BorderLayout());
		JToolBar toolBar=new JToolBar("Java ��ͼ");

		//���水ť
		JButton saveButton=new JButton(new ImageIcon("images/save.gif"));
		saveButton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.saveImage();
			}
		});
		toolBar.add(saveButton);

		//�رհ�ť
		JButton closeButton=new JButton(new ImageIcon("images/close.gif"));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.close();                                                                                                                                                                                                                                                                                                    
			}
		});
		toolBar.add(closeButton);

		this.add(toolBar,BorderLayout.NORTH);
	}
}

