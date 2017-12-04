

import java.util.HashMap;
import java.util.Map;

public class SolutionB {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String[] documents = {"How do we process information",
                "Information Retrieval is an interesting topic",
                "An interesting discussion",
                "Discussion on a psychological topic",
                "Perhaps it is some sort of psychological trauma"};

        SolutionB obj = new SolutionB();

        Map<String, Integer> tf = obj.computeTermFrequency(documents);
        Map<String, Integer> df = obj.computeDocumentFrequency(documents, tf);
        Map<String, Float> idf = obj.computeInverseDocumentFrequency(documents, df);

        System.out.println(idf);


    }

    /* Helper function to calculate TF */
    private Map<String, Integer> computeTermFrequency(String[] documents){

        Map<String, Integer> tf = new HashMap<String, Integer>();

        for (String l:documents){
            String[] temp = l.split(" ");

            for(int i=0; i<temp.length;i++){
                temp[i]=temp[i].toLowerCase();
            }

            for(String t:temp){
                if(tf.containsKey(t)){
                    int count = tf.get(t);
                    count++;
                    tf.put(t, count);
                }
                else {
                    tf.put(t, 1);
                }
            }
        }

        return tf;
    }

    /* Helper function to calculate DF */
    private Map<String, Integer> computeDocumentFrequency(String[] documents, Map<String, Integer> tf){
        Map<String, Integer> df = new HashMap<String, Integer>();

        tf.forEach((key, value) -> {

            for(String l : documents){
                if(l.contains(key)){
                    if(df.containsKey(key)){
                        int count = df.get(key);
                        count++;
                        df.put(key, count);
                    }else{
                        df.put(key, 1);
                    }
                }
            }
        });

        return df;
    }

    /* IDF(t) = log_e(Total number of documents / Number of documents with term t in it) */
    private Map<String, Float> computeInverseDocumentFrequency(String[] documents, Map<String, Integer> df){


        int N = documents.length;

        Map<String, Float> idf = new HashMap<String, Float>();

        df.forEach((key, value) -> {
            idf.put(key, (float) Math.log((float)N/df.get(key)));
        });


        return idf;
    }
}