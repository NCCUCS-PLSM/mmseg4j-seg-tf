import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class WordFrequencyCounter {

    private class Word implements Comparable<Word>{
        String word;
        int count;
        public Word(String word, int count) {
            this.word = word;
            this.count = count;
        }
        public int compareTo(Word otherWord) {
            if(this.count==otherWord.count){
                return this.word.compareTo(otherWord.word);
            }
            return otherWord.count-this.count;
        }
    }

    private Word[] getFrequentWords(String words[]){
        HashMap<String,Word> map = new HashMap<String,Word>();
        for(String s:words){
            Word w = map.get(s);
            if(w==null)
                w = new Word(s,1);
            else
                w.count++;
            map.put(s, w);
        }
        Word[] list = map.values().toArray(new Word[]{});
        Arrays.sort(list);
        return list;
    }// end of getFrequentWords

    public void run(String WFCName) throws Exception
    {
    	File file = new File(WFCName + ".wfc");
    	BufferedReader reader = new BufferedReader(new FileReader(file.getName()));  // 改成吃 SegChinese 產生的 .wfc 檔案
		String line = null;
		String str = "";
		
		while ((line = reader.readLine()) != null) 
    		str = str + line + " ";		// add space behide
		String words[] = str.split(" ");

		//DEBUG
		//System.out.println(words.length);
		//System.out.println(str);		

		// 統計詞頻並輸出成 *_tf.txt 檔案
        Word[] frequency = new WordFrequencyCounter().getFrequentWords(words);
        PrintWriter output = new PrintWriter(WFCName + "_tf.txt", "UTF-8");
        for(Word w:frequency)
        {
        	if(w.word.length() != 0)
        	{     
        		// DEBUG
        		//System.out.println(w.word+"\t"+w.count);
        		output.println(w.word + "\t" + w.count + "\t" + ((float)w.count/(float)words.length)*100);
        	}
        }
        
        file.delete();
        output.close();        		        
        
    }// end of run()
    
    public void runGUI(String segString, String wfcName) throws Exception
    {
    	
		String words[] = segString.split(" ");
		
		// 統計詞頻並輸出成 *_tf.txt 檔案
        Word[] frequency = new WordFrequencyCounter().getFrequentWords(words);
        PrintWriter output = new PrintWriter(wfcName + "_tf.txt", "UTF-8");
        for(Word w:frequency)
        {
        	if(w.word.length() != 0)
        	{     
        		// DEBUG
        		//System.out.println(w.word+"\t"+w.count);
        		output.println(w.word + "\t" + w.count + "\t" + ((float)w.count/(float)words.length)*100);
        	}
        }        
        output.close();       
        
    }// end of runGUI    
}