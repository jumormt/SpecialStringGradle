/**
 * 
 */
package com.analysis.startup;

/**
 * @author huge
 *
 * 2014年8月11日
 */
//PrintGrade.java
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.analysis.manifest.FileAction;

public class MainFrame extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;   
	JLabel theLabel;    
	JLabel jb = null;       
	JLabel jb1 = null;       
	JTextArea htmlTextArea;   
	Properties props;    
	ArrayList<JPanel> jpanelList = null; 
	static JFrame frame;
	JTextField stringtextField;  //file直接打开文件。
	JTextField  packagetextField;   		
	JTextField apktextField;  //file直接打开文件。
	private JFileChooser fileChooser = new JFileChooser(".");  
	 static Timer timer;  
	
	public MainFrame()
	{
	//	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); 
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton queryTheLabel = new JButton("检测");
		JButton dqLabel = new JButton("导出");
		queryTheLabel.setMnemonic(KeyEvent.VK_C); 
		queryTheLabel.addActionListener(this);
		
		dqLabel.setMnemonic(KeyEvent.VK_B);
		dqLabel.addActionListener(this);
		
		JPanel leftPanel = new JPanel();
		
		leftPanel.add(queryTheLabel);
		leftPanel.add(dqLabel);
		add(leftPanel,BorderLayout.CENTER);
	}    // React to the user pushing the Change button.
	public void actionPerformed(ActionEvent e)
	{
		if ("检测".equals(e.getActionCommand()))
		{            
			QueryJTable jTableDefineTest = new QueryJTable();//确定完毕，进入此结构中
			jTableDefineTest.pack();
			if( fileChooser.getSelectedFile() != null ){ //路径等信息不为空进行检测
				System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
//				List<String>fileList = new ArrayList<String>(); 
//				FileAction.CalcalateAllFile(fileChooser.getSelectedFile().getAbsolutePath(), fileList);
//				int fileNumber = fileList.size();//获取总的任务量 
				ProgressBar();
			}
		}
		if("导出".equals(e.getActionCommand())){ //TODO 导出文件，下拉列表
		//	System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			ResultSQL();	
		}
	}   
	
	
	private static void createAndShowGUI()
	{        
		frame = new JFrame ("敏感字符串检测"); 
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setPreferredSize(new Dimension(500, 200));
		frame.setLocation(500, 600); 
		JComponent newContentPane = new MainFrame();
		newContentPane.setOpaque(true);  
		frame.setContentPane(newContentPane); 
		frame.pack();
		frame.setVisible(true);
		
	}  
	
	private static void ResultSQL(){
		JFrame resultJF = new JFrame();
		resultJF.setTitle("导出文件选择");
		resultJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		resultJF.setBounds(400,400,250,100);  
        JPanel contentPane=new JPanel();  
        contentPane.setBorder(new EmptyBorder(5,5,5,5));  
        resultJF.setContentPane(contentPane);  
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
        JLabel label=new JLabel("批次选择:");  
        contentPane.add(label);  
        JComboBox comboBox=new JComboBox();  
        comboBox.addItem("默认批次");
        comboBox.addItem("1");  
        comboBox.addItem("2");  
        comboBox.addItem("3");  
        contentPane.add(comboBox); 
        
        comboBox.addItemListener(new ItemListener() {
        	 
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == 1) {
                	System.out.println(ie.getItem().toString());
                  //  label.setText(ie.getItem().toString());
                }
            }
         
        });
       
        resultJF.setVisible(true);  
	}
	
	private static void ProgressBar(){
		
	//	Timer timer = null;  
	    final JProgressBar jpbFileLoading;
		jpbFileLoading = new JProgressBar();  
        jpbFileLoading.setStringPainted(true);  //设置进度条呈现进度字符串,默认为false  
        jpbFileLoading.setBorderPainted(false); //不绘制边框,默认为true  
        jpbFileLoading.setPreferredSize(new Dimension(100, 40)); //设置首选大小  
        timer = new Timer(500, new ActionListener(){  //更新时间
            public void actionPerformed(ActionEvent e) {  
                int loadingValue = jpbFileLoading.getValue();  
                if (loadingValue < 100){  
                    jpbFileLoading.setValue(++loadingValue);  
                }else {  
                    timer.stop();  
                }  
            }  
        });  
        timer.start();
        frame.add(jpbFileLoading, BorderLayout.NORTH);  
        frame.setLocationRelativeTo(null); //居中显示  
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE); //采用指定的窗体装饰风格  
        frame.setVisible(true); 
        
	}
	
	public static void main(String[] args)
	{       
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
	
	class QueryJTable extends JFrame implements ActionListener
	{        
		private static final long serialVersionUID = 1L;
		
		JDialog jdlg ;
		
		private JButton filebutton = new JButton("打开文件");
		private JButton queryTheLabel = new JButton("确定"); 	 
		private JButton rTheLabel = new JButton("返回");
		private JPanel p = new JPanel(new SpringLayout());
		
		public QueryJTable()
		{
//			super();
			jdlg = new JDialog(frame,  true);
			jdlg.setTitle("检测相关信息");
			
			jdlg.setBounds(400, 400, 400, 300); 
			jdlg.getContentPane().add(filebutton, BorderLayout.NORTH);
			filebutton.addActionListener(this);
			
			String[] labels = { "string: ", "package: ", "appname: " };   
			int numPairs = labels.length;  	 
			
	        stringtextField = new JTextField(30);
	        stringtextField.setSize(30, 20);
	        stringtextField.setHorizontalAlignment(SwingConstants.LEFT);
	        JLabel stringLabel = new JLabel("string",JLabel.LEFT);
	        p.add(stringLabel);
	  //      stringLabel.setLabelFor(stringtextField);  
	    
            p.add(stringtextField); 
            
            packagetextField = new JTextField(30);  
            packagetextField.setHorizontalAlignment(SwingConstants.LEFT);
            JLabel packageLabel = new JLabel("package",JLabel.LEFT);
	        p.add(packageLabel);
	        packageLabel.setLabelFor(packagetextField);  
            p.add(packagetextField); 
            
            apktextField = new JTextField(30);  
            apktextField.setHorizontalAlignment(SwingConstants.LEFT);
            JLabel apkLabel = new JLabel("apkname",JLabel.LEFT);
	        p.add(apkLabel);
	        apkLabel.setLabelFor(apktextField);  
	        p.add(apktextField);

	 //       SpringUtilities.makeCompactGrid(p, numPairs, 2,  6, 6,   6, 6);   

	        p.setLayout(new GridLayout(0, 1));
	        p.add(queryTheLabel); 
	        p.add(rTheLabel);
			queryTheLabel.addActionListener(this); 
			rTheLabel.addActionListener(this); 
//			ym.add(nameage); 
			jdlg.add(p); 
			jdlg.setVisible(true);
			System.out.println(stringtextField.getText()+","+apktextField.getText());
			}    
		
		public void actionPerformed(ActionEvent e) {
			
			Object source = e.getSource();
			// 触发JButton(此例仅设置有一个按钮，多按钮请自行更改)
			//if (source instanceof JButton) {
			if("打开文件".equals(e.getActionCommand())){
				openFile();
			}
			
			if("确定".equals(e.getActionCommand())) //开始检测,加进度条
			{
				if( fileChooser.getSelectedFile() == null ){
				//	JDialog jdchild = new JDialog(jdlg,  true);
					JOptionPane.showMessageDialog(null, "文件件路径为空，不予检测！", "警告", JOptionPane.ERROR_MESSAGE);
				}
				System.out.println(stringtextField.getText()+","+packagetextField.getText());
				jdlg.dispose();
			}
			if("返回".equals(e.getActionCommand()))
			{				
				//this.setVisible(false);
				stringtextField.setText("");
				packagetextField.setText("");
				apktextField.setText("");
				fileChooser.setSelectedFile(null);	
				System.out.println(stringtextField.getText()+","+packagetextField.getText());
				jdlg.dispose();
			}
		}
			
		public void openFile() {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("打开文件夹");
			int ret = fileChooser.showOpenDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				//文件夹路径
				String filepath = fileChooser.getSelectedFile().getAbsolutePath();
			//	System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}			
	}
}