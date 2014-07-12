/*
 * Language: Java
 * Author:   Veck Hsiao @ PLSM, National Chengchi University, Taipei, Taiwan
 * Time:     June/9/2014
 * Usage:    Graphic user interface of mmseg4j-seg-tf
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;



public class GUIMain extends JFrame {

	private JPanel contentPane;
	
	/*
	 * 這個 retry 是一個 flag，因為很多時候需要使用者操作後檢驗操作結果
	 * 如果錯誤了應該要重來而非關閉程式，所以可以用這個 flag 來做為 while 的檢查 
	 */
	private boolean retry = false;
	
	/*
	 * 因為 GUI 與 CLI 的運作不同 
	 */
	private String WFCName = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain frame = new GUIMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/veck/Desktop/Eclipse Workspace/SegChinese/img/plsmck.ico"));
		setTitle("MMSeg4J 實作斷詞與詞頻統計");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel label = new JLabel("請選擇檔案");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		contentPane.add(label, gbc_label);
		
		JLabel label_1 = new JLabel("斷詞結果");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 0;
		contentPane.add(label_1, gbc_label_1);
		
		JLabel label_2 = new JLabel("詞頻統計");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.gridwidth = 2;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 0;
		contentPane.add(label_2, gbc_label_2);
		
		JScrollPane leftScrollPane = new JScrollPane();
		GridBagConstraints gbc_leftScrollPane = new GridBagConstraints();
		gbc_leftScrollPane.weightx = 5.0;
		gbc_leftScrollPane.gridheight = 3;
		gbc_leftScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_leftScrollPane.fill = GridBagConstraints.BOTH;
		gbc_leftScrollPane.gridx = 0;
		gbc_leftScrollPane.gridy = 1;
		contentPane.add(leftScrollPane, gbc_leftScrollPane);
		
		final JTextArea readArea = new JTextArea();
		leftScrollPane.setViewportView(readArea);
		readArea.setEditable(false);
		
		JScrollPane midScrollPane = new JScrollPane();
		GridBagConstraints gbc_midScrollPane = new GridBagConstraints();
		gbc_midScrollPane.weightx = 5.0;
		gbc_midScrollPane.gridheight = 3;
		gbc_midScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_midScrollPane.fill = GridBagConstraints.BOTH;
		gbc_midScrollPane.gridx = 1;
		gbc_midScrollPane.gridy = 1;
		contentPane.add(midScrollPane, gbc_midScrollPane);
		
		final JTextArea segArea = new JTextArea();
		midScrollPane.setViewportView(segArea);
		segArea.setEditable(false);
		
		JLabel lbltt = new JLabel("字詞\t\t\t次數\t\t\t%\n");
		GridBagConstraints gbc_lbltt = new GridBagConstraints();
		gbc_lbltt.gridwidth = 2;
		gbc_lbltt.insets = new Insets(0, 0, 5, 0);
		gbc_lbltt.gridx = 2;
		gbc_lbltt.gridy = 1;
		contentPane.add(lbltt, gbc_lbltt);
		
		JScrollPane rightScrollPane = new JScrollPane();
		GridBagConstraints gbc_rightScrollPane = new GridBagConstraints();
		gbc_rightScrollPane.gridwidth = 2;
		gbc_rightScrollPane.weightx = 3.0;
		gbc_rightScrollPane.gridheight = 2;
		gbc_rightScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_rightScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rightScrollPane.gridx = 2;
		gbc_rightScrollPane.gridy = 2;
		contentPane.add(rightScrollPane, gbc_rightScrollPane);
		
		final JTextArea tfArea = new JTextArea();
		rightScrollPane.setViewportView(tfArea);
		tfArea.setEditable(false);
		
		JButton openButton = new JButton("開啟檔案");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * 按下『開啟檔案』後，就要跳出瀏覽視窗，讓使用者選擇檔案
				 */
				Frame frame = new Frame();
				FileDialog fd = new FileDialog(frame, "開啟檔案", FileDialog.LOAD);
				String dirPath = "";
				OSValidator os = new OSValidator();
				
				// 根據作業系統開啟檔案的預設路徑 
				switch(os.getOSType())
				{
					case 1:		// Mac OS X						
					case 0:		// UNIX or Linux
						dirPath = "~/Desktop";
						break;
					case -1:	//Windows
						dirPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop";
						break;
					default: 	//others	
						System.out.println("System does not support.");
						System.exit(1);
				}
				
				do{	
					fd.setDirectory(dirPath);
					fd.setVisible(true);				
					
					// 檢查附檔名，需為 txt
					String ext[] = fd.getFile().split("[.]");	
					WFCName = ext[0];
					
					try	// 避免沒有附檔名，ext[1] 不存在
					{
						
						if(!ext[1].equals("txt"))
						{
							JOptionPane.showMessageDialog(frame, "檔案格式錯誤，應為 .txt 檔", "格式錯誤", JOptionPane.ERROR_MESSAGE);
							retry = false;
						}
						else
							retry = true;
					}
					catch(ArrayIndexOutOfBoundsException ex)
					{
						JOptionPane.showMessageDialog(frame, "檔案格式錯誤，應為 .txt 檔", "格式錯誤", JOptionPane.ERROR_MESSAGE);
						retry = false;
					}

				}while(!retry);
				
				// reset "retry"
				retry = false;
											
				// 選擇完檔案才會繼續執行至此
				String filePath = fd.getDirectory() + fd.getFile();				
				File file = new File(filePath);	
				
				// 讀取檔案顯示在 readArea
				FileReader fr;
				try 
				{
					/*
					 *	fr = new FileReader(file.getAbsoluteFile());
					 *  BufferedReader reader = new BufferedReader(fr);
					 *  readArea.read(reader, "readArea");
					 *  reader.close();
					 *  fr.close();
					 *  
					 *  Note: 這段作法在 windows 下可以執行，但是讀取 UTF-8 編碼會有問題 (Mac is fine) 
			         */
			        
			          BufferedReader input = new BufferedReader(
			        		  new InputStreamReader(
			        				  new FileInputStream(file.getAbsoluteFile()),"UTF-8"));
      				  readArea.read(input, "readArea");
      				  input.close();
			         
				} 
				catch (Exception ex) 
				{					
					ex.printStackTrace();
					System.exit(1);
				}				

			}// end of actionPerformed()
		});
		GridBagConstraints gbc_openButton = new GridBagConstraints();
		gbc_openButton.insets = new Insets(0, 0, 0, 5);
		gbc_openButton.gridx = 0;
		gbc_openButton.gridy = 4;
		contentPane.add(openButton, gbc_openButton);
		
		JButton segButton = new JButton("斷詞");
		segButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * 按下『斷詞』後：
				 * 1. 讀取 readArea 的內容成字串
				 * 2. 建立 SegChinese 物件處理斷詞
				 * 3. 將處理完的中間檔案 *.wfc 印在 segArea
				 */
				
				String str = readArea.getText();
				
				FileReader fr;
				try 
				{
					new SegChinese().runGUI(str, WFCName);
					
					/*
					fr = new FileReader(WFCName+".wfc");
					BufferedReader reader = new BufferedReader(fr);
					segArea.read(reader, "segArea");
			        reader.close();
			        fr.close();
			        */
					
			        BufferedReader input = new BufferedReader(
			        		new InputStreamReader(
			        				new FileInputStream(WFCName + ".wfc"),"UTF-8"));
      				segArea.read(input, "segArea");
      				input.close();			        
				} 
				catch (Exception ex) 
				{
					//ex.printStackTrace();
				}
								
			}// end of actionPerfomed()
		});
		GridBagConstraints gbc_segButton = new GridBagConstraints();
		gbc_segButton.insets = new Insets(0, 0, 0, 5);
		gbc_segButton.gridx = 1;
		gbc_segButton.gridy = 4;
		contentPane.add(segButton, gbc_segButton);
		
		JButton tfButton = new JButton("詞頻統計");
		tfButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				/*
				 * 按下『詞頻統計』後：
				 * 1. 讀取 segArea 的內容成字串 (不讀檔案是為了少一次 IO)
				 * 2. 建立 WordFrequencyCounter 物件處理斷詞 (會輸出 *_tf.txt)
				 * 3. 讀取 *_tf.txt 輸出在 tfArea
				 * 4. 刪除中間檔 *.wfc
				 * TODO: 開啟 *_tf.txt 的檔案目錄 (or 檔案)
				 */
				
				// 避免沒有斷詞直接按統計詞頻而產生空檔案，先檢查有無 .wfc 存在
				Frame frame = new Frame();
				File file = new File(WFCName + ".wfc");
				if(!file.exists())
				{
					JOptionPane.showMessageDialog(frame, "請先進行斷詞！", "無斷詞庫", JOptionPane.WARNING_MESSAGE);
					retry = false;
				}
				else
				{	
					String str = segArea.getText();
					
					FileReader fr;
					try 
					{
						new WordFrequencyCounter().runGUI(str, WFCName);
						
						/*
						fr = new FileReader(WFCName+"_tf.txt");
						BufferedReader reader = new BufferedReader(fr);					
						tfArea.read(reader, "tfArea");
				        new File(WFCName + ".wfc").delete();
				        reader.close();
				        fr.close();
				        */
				        
				        BufferedReader input = new BufferedReader(
				        		new InputStreamReader(
				        				new FileInputStream(WFCName + "_tf.txt"),"UTF-8"));
	      				tfArea.read(input, "tfArea");
	      				input.close();			   
				        				        
					} 
					catch (Exception ex) 
					{
						//ex.printStackTrace();
					}
										
				}
			}// end of actionPerfomed()
		});
		GridBagConstraints gbc_tfButton = new GridBagConstraints();
		gbc_tfButton.insets = new Insets(0, 0, 0, 5);
		gbc_tfButton.gridx = 2;
		gbc_tfButton.gridy = 4;
		contentPane.add(tfButton, gbc_tfButton);
		
		JButton resetButton = new JButton("清空");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readArea.setText("");
				segArea.setText("");
				tfArea.setText("");
			}
		});
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.gridx = 3;
		gbc_resetButton.gridy = 4;
		contentPane.add(resetButton, gbc_resetButton);
	}

}
