

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SolutionA {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String[] list = {"Una paloma blanca Im just a bird in the sky",
                "Una paloma blanca over the mountain I fly",
                "Blue ridge mountains Shenandoah river",
                "Im like a bird Ill only fly away",
                "West Virginia Mountain mamma"};

		/* calculate tf-idf of a list of documents */
        Map<String, Integer> tf = new HashMap<String, Integer>();

        for (String l:list){
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


		/* calculate IDF as follows:
 	IDF(t) = log_e(Total number of documents / Number of documents with term t in it).
		 */

        Map<String, Integer> df = new HashMap<String, Integer>();

        tf.forEach((key, value) -> {

            for(String l : list){
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

        int N = list.length;

        Map<String, Float> idf = new HashMap<String, Float>();

        df.forEach((key, value) -> {
            idf.put(key, (float) Math.log((float)N/df.get(key)));
        });

        System.out.println(idf);


    }

}