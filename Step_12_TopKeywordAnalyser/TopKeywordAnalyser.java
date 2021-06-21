package fsd.Step_12_TopKeywordAnalyser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TopKeywordAnalyser implements Runnable {

    private final String filePath;

    public TopKeywordAnalyser(String filePath) {
        this.filePath = filePath;
    }

    public void run() {

        ArrayList<String> fileData = FileUtility.readAndReturnFile(filePath);
        HashMap<String,Integer> keywordMap = new HashMap<>();

        for (String row : fileData) {
            String[] keywords = row.split(" ");
            for (String keyword:keywords) {
                String str = keyword.toLowerCase();
                if (!keywordMap.containsKey(str)) {
                    keywordMap.put(str,1);
                } else {
                    Integer value = keywordMap.get(str);
                    keywordMap.put(str,value+1);
                }
            }
        }

        ArrayList<KeywordCount> keywordCountArrayList = new ArrayList<>();

        for (String keyword:keywordMap.keySet()) {
            keywordCountArrayList.add( new KeywordCount( keyword, keywordMap.get(keyword) ) );
        }

        keywordCountArrayList.sort(new Comparator<>() {
            /*
            @Override
            public int compare(KeywordCount o1, KeywordCount o2) {
                return o2.count-o1.count;
            }
             */
            public int compare(KeywordCount o1, KeywordCount o2) {
                if (o2.count == o1.count) {
                    return o1.keyword.compareTo(o2.keyword); //Dictionary order -> Lexicographically Ascending Order
                }
                return o2.count - o1.count;
            }
        });
    /*
    <!-- Printing to console -->
    */
        for (KeywordCount keywordCount : keywordCountArrayList) {
            System.out.print(keywordCount.keyword+" "+keywordCount.count+"\t");
        }
        System.out.println();

        // REFER STEP 13 NOTES //
    /*
    <!-- Printing as JSON Object / Convert Object to JSON -->
    */
        String json = new Gson().toJson( keywordCountArrayList );
        System.out.println(json);

    /*
    <!-- Convert JSON to KeywordCount Object -->
    */
        String convertJSON = "{\"keyword\":\"HELLO GSON\",\"count\":100}";
        KeywordCount keywordCount = new Gson().fromJson( convertJSON, KeywordCount.class );
        System.out.println("Converted JSON to Keyword Count Object => "+keywordCount.keyword+" : "+keywordCount.count);

    /*
    <!-- Convert JSON to ArrayList of KeywordCount objects -->
    */
        String convertJSONArr = "[ {'keyword' : 'jaya' , 'count' :10} , {'keyword' : 'he' , 'count' :6} ]";
        ArrayList<KeywordCount> convertedArrayList = new Gson().fromJson(convertJSONArr, new TypeToken<ArrayList<KeywordCount>>(){}.getType() );

        for (KeywordCount keywordCountTemp : convertedArrayList) {
            System.out.println(keywordCountTemp.keyword + ": " + keywordCountTemp.count);
        }
        System.exit(0);
    }

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager(1);
        taskManager.waitTillQueueIsFreeAndAddTask(new TopKeywordAnalyser("/Users/snehgupta/IdeaProjects/CodingMafia_JavaFSD/src/main/java/JavaFSD/Step_12_TopKeywordAnalyser/IndianNationalAnthem"));
            //Self:- Why to typecast filePath to 'Runnable', IntelliJ not allowing it without this tho your code worked...

    }
}
