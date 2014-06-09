import java.io.IOException;


public class CLIMain {

	public static void main(String[] args) throws Exception 
	{ 
		/*
		if(args.length != 1)
		{
			System.out.println("使用方式：java SegChinese [file-to-be-segmented]");
			System.exit(0);
		}
		*/
		
		/*
		 * 暫時寫死，之後會吃使用者在 GUI 吃的檔案路徑
		 */
		
		// 斷詞
		SegChinese sc = new SegChinese();
		sc.run("src/sample.txt");
		
		// DEBUG
		//System.out.println(sc.getWFCName());
		
		// 計算詞頻
		WordFrequencyCounter wfc = new WordFrequencyCounter();
		wfc.run(sc.getWFCName());
		
		
	}// end of main()	

}