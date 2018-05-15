package cristinae.dictionary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 程序的主界面
 * @author ene
 *
 */
public class MainView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//整体布局方式
	private FlowLayout gridLayout;
	private JButton buttonFirst;
	private JButton buttonSecond;
	private JButton buttonThird;
	private JButton buttonFourth;
	private Dictionary dic;
	private ExecutorService exec = Executors.newCachedThreadPool();
	private JComboBox<String> comBox;
	/**
	 * 初始化
	 */
	MainView(){
		//设置关闭窗口
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置字典初始化
		dic = new Dictionary();
		//设置标题
		this.setTitle("屏幕取词翻译软件");
		//设置组件的位置和大小
		this.setBounds(400, 400, 600, 100);
		//使组件永远在窗口最上方
		this.setAlwaysOnTop(true);
		//this.setUndecorated(true);
		//this.setOpacity(0.5f);
		//布局方式初始化
		//一行二列，水平间距5，竖直无间距
		this.gridLayout = new FlowLayout();
		this.setLayout(gridLayout);
		JPanel jpFirst = new JPanel();
		/*
		 * 下拉选择栏
		 */
		String [] name = Dictionary.getFileName().toArray(new String[0]);
        this.comBox = new JComboBox<String>(name);
        this.comBox.setVisible(true);
        jpFirst.add(this.comBox,BorderLayout.WEST);
        /*
         * 设置取词范围
         */
        this.buttonFirst = new JButton("设置取词范围");
        this.buttonFirst.setVisible(true);
        this.buttonFirst.addActionListener(new FirstActionListener());
        jpFirst.add(this.buttonFirst);
        this.add(jpFirst);
        JPanel jpSecond = new JPanel();
        /*
         * 预览
         */
        this.buttonSecond = new JButton("预览");
        this.buttonSecond.setVisible(true);
        jpSecond.add(this.buttonSecond);
        /*
         * 清空
         */
        this.buttonThird = new JButton("清空");
        this.buttonThird.setVisible(true);
        this.buttonThird.addActionListener(new ThirdActionListener());
        jpSecond.add(this.buttonThird);
        /*
         * 更多
         */
        this.buttonFourth = new JButton("更多");
        this.buttonFourth.setVisible(true);
        jpSecond.add(this.buttonFourth);
        this.add(jpSecond);
	}
	protected class FirstActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println((String)comBox.getSelectedItem());
			/*
			 * 多线程
			 */
			exec.execute(new Runnable() {
				public void run() {
					dic.addRunner((String) comBox.getSelectedItem());
				}
			});
		}
		
	}
	protected class ThirdActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exec.shutdownNow();
			Dictionary.exec.shutdownNow();
		}
	}
}

