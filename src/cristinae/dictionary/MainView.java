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
 * �����������
 * @author ene
 *
 */
public class MainView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//���岼�ַ�ʽ
	private FlowLayout gridLayout;
	private JButton buttonFirst;
	private JButton buttonSecond;
	private JButton buttonThird;
	private JButton buttonFourth;
	private Dictionary dic;
	private ExecutorService exec = Executors.newCachedThreadPool();
	private JComboBox<String> comBox;
	/**
	 * ��ʼ��
	 */
	MainView(){
		//���ùرմ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�����ֵ��ʼ��
		dic = new Dictionary();
		//���ñ���
		this.setTitle("��Ļȡ�ʷ������");
		//���������λ�úʹ�С
		this.setBounds(400, 400, 600, 100);
		//ʹ�����Զ�ڴ������Ϸ�
		this.setAlwaysOnTop(true);
		//this.setUndecorated(true);
		//this.setOpacity(0.5f);
		//���ַ�ʽ��ʼ��
		//һ�ж��У�ˮƽ���5����ֱ�޼��
		this.gridLayout = new FlowLayout();
		this.setLayout(gridLayout);
		JPanel jpFirst = new JPanel();
		/*
		 * ����ѡ����
		 */
		String [] name = Dictionary.getFileName().toArray(new String[0]);
        this.comBox = new JComboBox<String>(name);
        this.comBox.setVisible(true);
        jpFirst.add(this.comBox,BorderLayout.WEST);
        /*
         * ����ȡ�ʷ�Χ
         */
        this.buttonFirst = new JButton("����ȡ�ʷ�Χ");
        this.buttonFirst.setVisible(true);
        this.buttonFirst.addActionListener(new FirstActionListener());
        jpFirst.add(this.buttonFirst);
        this.add(jpFirst);
        JPanel jpSecond = new JPanel();
        /*
         * Ԥ��
         */
        this.buttonSecond = new JButton("Ԥ��");
        this.buttonSecond.setVisible(true);
        jpSecond.add(this.buttonSecond);
        /*
         * ���
         */
        this.buttonThird = new JButton("���");
        this.buttonThird.setVisible(true);
        this.buttonThird.addActionListener(new ThirdActionListener());
        jpSecond.add(this.buttonThird);
        /*
         * ����
         */
        this.buttonFourth = new JButton("����");
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
			 * ���߳�
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

