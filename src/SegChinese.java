/*
 * Language: Java
 * Author:   Veck Hsiao @ PLSM, National Chengchi University, Taipei, Taiwan
 * Time:     June/9/2014
 * Usage:    Do Chinese segmentation
 */

import java.io.IOException; 
import java.io.Reader; 
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary; 
import com.chenlb.mmseg4j.MMSeg; 
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public class SegChinese {

	protected Dictionary dic;
	private String WFCName;
	

	public SegChinese() 
	{
		
		/*
		 * 這裡可以指定自訂詞庫 (原本 ./src/SegChinese/data 下有本作者自定的許多 *.dic 檔案)
		 * 文件中要求使用者要加入自定義詞典，直接修改或替換 src/dict/words.dic
		 */
        OSValidator os = new OSValidator();


        // 根據作業系統開啟檔案的預設路徑
        switch(os.getOSType())
        {
            case 1:		// Mac OS X
            case 0:		// UNIX or Linux
                System.setProperty("mmseg.dic.path", System.getProperty("user.dir")+ "/dict");
                break;
            case -1:	//Windows
                System.setProperty("mmseg.dic.path", System.getProperty("user.dir")+ "\\dict");
                break;
            default: 	//others
                System.out.println("System does not support.");
                System.exit(1);
        }
		dic = Dictionary.getInstance();
		
	}// end of SegChinese()
	
	
	protected Seg getSeg() 
	{ 
		return new ComplexSeg(dic);
	}// end of getSeg
	
	public String segWords(String txt, String wordSpilt) throws IOException 
	{ 
		Reader input = new StringReader(txt);
		StringBuilder sb = new StringBuilder();
		Seg seg = getSeg();
		MMSeg mmSeg = new MMSeg(input, seg); Word word = null;
	
		boolean first = true; while((word=mmSeg.next())!=null) 
		{
			if(!first) 
				sb.append(wordSpilt);
	
			String w = word.getString(); sb.append(w);
			first = false;
		}
		
		return sb.toString();
	}// end of segWord
	
	
	/*
	 * @SegFile: File to be segmented, cannot be empty
	 */
	protected void run(String SegFile) throws IOException 
	{
		
		/* 
		 * String txt = "這行文字是要被中文斷詞處理的文章,可以從執行結果看斷詞是否成功 莊圓大師 蕭凱";
		 * 上面這行是本作者原本的測試(最後的蕭凱是我加入為了測試字定義辭典的)
		 * 我這裡改成可以載入要斷詞的檔案
		 */
		
		// 檢查有無該(要斷詞的)檔案
		if(SegFile.length() == 0)
		{
			System.out.println("Please assign a file to be segmented.");
			System.exit(0);
		}
				
		File file = new File(SegFile);
		if(!file.exists() || file.isDirectory())
		{
			System.out.println("File does not exist or assignment is a directory.");
			System.exit(0);
		}

		// DEBUG
		//System.out.println(file.getAbsolutePath());
		
		// 讀取要斷詞的文件 (在專案中，需為相對 src 的路徑，因此統一抓檔案絕對路徑)		 
		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		String line = null;
		String txt = "";		
		while ((line = reader.readLine()) != null) 
    		txt = txt + line + " ";		// add space behide
		
		/*
		 * 因為原本這個程式是可以直接用 command line: java -jar [this-file.jar] <string-to-be-segmented>，所以會從 main 接收 args 在丟給 run
		 * 最後在這裡指定給要處理的字串變數 txt，如果沒有指定的話，就會用在上面初始定義示範用的 txt 內容
		 */
		if(txt.length() <= 0) 
		{ 
			System.out.println("Nothing to be segmented.");
			System.exit(0);
		}

		// 擷取主檔名拼湊成輸出檔案名稱 *.wfc
		String fname = file.getName();
		String splited[] = fname.split("[.]");		// reg "[.]" 才能斷出來，單純用 '.' 無法斷出來	
		WFCName = splited[0]; 
		
		/*
		 * System.out.println(segWords(txt, " | "));
		 * 上面這行是原本的間隔，兩個空白中間插一槓，我改成用一個空白隔開，配合 WordFrequencyCounter.java 
		 * 輸出為中間檔案 .wfc (自定義: wfc = word frequency counter XD) 
		 */ 
		// DEBUG
		//System.out.println(segWords(txt, " "));

		PrintWriter output = new PrintWriter(WFCName +".wfc", "UTF-8");
		output.println(segWords(txt, " "));
		output.close();
						
	}// end of run()

	
	/* 因為 GUI 的運作是先載入預覽後，此用者按下按鈕才進行斷詞，因此需要另外保留檔案名稱 (CLI 後來應該也要改寫成兩階段)
	 * @segString: 要斷詞的字串
	 * @wfcName: 來源檔案的名稱(不含附檔名)，因為 SegChinese 本身也有一個全域變數 WFCName，避免混淆而改小寫 wfc (之後應修正)
	 */
	public void runGUI(String segString, String wfcName) throws IOException
	{
		if(segString.length() <= 0) 
		{ 
			System.out.println("Nothing to be segmented.");
			return;
		}

		PrintWriter output = new PrintWriter(wfcName +".wfc", "UTF-8");
		output.println(segWords(segString, " "));
		output.close();
							
	}// end of runGUI()
	
			
	public String getWFCName()
	{
		if(WFCName.length() <= 0)
			return "";
		else
			return WFCName;
	}
}
