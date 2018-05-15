package cristinae.dictionary;

/**
 * 未完成
 * JWindow刷新已完成
 * JFrame的Paint无法工作
 * DictionaryRunnerBuilder未完成
 * 把Dictionary与上者结合
 * DictionaryRunnerBuilder实现从文件读取配置，并保存（一个配置）
 * 且一个方法提供改配置的Runner
 * 放弃该项目的原因：Tesseract识别率较低，百度OCR要钱
 * OcrForTess4j的问题：图片预处理可以优化+阅读源码，针对行识别；
 * 重启任务的话请优化Dictionary(管理配置的存取读写使用)
 *  * @author ene
 *
 */
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainView mainView = new MainView();
		mainView.setVisible(true);
		//Dictionary dic = new Dictionary();
		//dic.addRunner("another_test");
    }
}
