Last Update: 2015/03/18 05:03

#### User Note
使用方式：
<br>1. 	點擊 PLSMSEGTF.exe 後，點選『開啟檔案』，選擇要處理的 txt 檔 (目前只支援 txt 檔，編碼限制 UTF-8)
<br>2. 	載入文本後，點擊『斷詞』，即可看到顯示的斷詞後文本，並在同一目錄下獲得 \*.wfc 檔
<br>3. 	接著在點擊『詞頻統計』，即可看到顯示的詞頻統計結果，並在同一目錄下獲得 \*_tf.txt 檔
<br>4. 	點選『清空』，可將載入框與結果顯示框清空
<br>5.  目前版本可以加入自定義辭典，在程式同目錄下建立 dict 的資料夾，資料夾中放字典檔 .dic，但一定要以 words 開頭，可以 words\*.dic，自定義字典一定要是 UTF-8(檔首無 BOM)，且一詞一行，詞前後不可有空白或 Tab

Rate(%):  
<br>1. 	計算法：我是用該詞會的詞頻除以全部的詞彙(所有出現在文中且斷開的詞彙，不是詞彙的種類)
<br>2. 	不採用 double 的原因是小數點後太多位，使用 float 即可

#### Dev Note
<br>1. 	主要專案在 Eclipse Workspace 中的 SegChinese
<br>2. 	WFC 的 runGUI 直接讀取 segArea 字串的話，目的是要減少 IO，但是 \*.wfc 仍需要的兩個原因是：
	<br>(1) CLI 還是需要這個中間檔來做處理
	<br>(2) 如果只是要斷詞結果，沒有要統計詞頻的話，這個中間檔就是必要的 (也許以後可以考慮詞頻統計完也不要刪除中間檔)


#### TODO
<br>1. 	老師建議加入 idf, consine, stop words/標點符號 的移除（不計入計算）可加成一個功能選項。
<br>2. 	能夠一次讀取多個文字檔，批次處理斷詞與詞頻，得到 inverted index
<br>3. 	CLI 也分成兩階段，先斷詞，在詞頻統計
<br>4. 	當 4. 改完後，因為 SegChinese 的物件應該會消滅，所以 getWFCName 會無效
		在 WordFrequencyCount 呼叫應該會存取不到 WFCName，所以用 GUI 的方式傳遞會比較好，將 WFCName 保留在 GUI 前端就好
<br>5. 	開啟詞頻檔案位置或檔案
<br>6. 	「找不到檔案」忘了改
<br>7. 	GB and ANSI 不應該要求使用者存 utf-8 編碼？

#### Bug
Global:
<br>1. 	\*.wfc 好像包裝後的路徑就跑掉沒刪掉
<br>2. 	如果是文件的最後一個詞，會被單獨計算，例如: ..... 台北 .... 台北 (EOF) ，則 台北 會有兩個條目

Mac:

Linux:


Windows:
<br>1. 	因為windows 選擇檔案可以是輸入的，因此可能直接輸入 file_DNE.txt 檔名後案開啟找不到檔案就掛了
<br>2. 	如果直接按右鍵建立新的文字檔(txt)，預設會是 ANSI 編碼，讀取成 UTF-8 會亂碼，需要另存新檔為 UTF-8 才會正常
