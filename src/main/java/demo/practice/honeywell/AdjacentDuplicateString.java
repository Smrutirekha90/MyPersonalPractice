package demo.practice.honeywell;

import java.util.Collections;
import java.util.PriorityQueue;

/*
A list of character is given, you have to print all characters keeping in mind that same character should not print adjacently.
input-
A,A,I,I,I,I,S,S
output-
I,A,I,A,I,S,I,S
A,I,A,I,S,I,S,I (no two characters are adjacent)
eta try kara jadi free acha
 */
public class AdjacentDuplicateString {

    public static void main(String[] args){
        String str = "AAIIIISSSBB";
        System.out.println(rearrangeAdjacentDuplicates(str));
    }

    public static String rearrangeAdjacentDuplicates(String str){
        final class CharFreq implements Comparable<CharFreq>{
            char c;
            int freq;
            public CharFreq(char ch, int count){
                c = ch;
                freq = count;
            }
            @Override
            public int compareTo(CharFreq o) {
                int comp = Double.compare(freq, o.freq);
                if(comp == 0){
                    comp = Character.compare(o.c, c);
                }

                return comp;
            }
        }

        int n = str.length();
        StringBuffer rearranged = new StringBuffer();
        PriorityQueue<CharFreq> maxHeap = new PriorityQueue<CharFreq>(256, Collections.reverseOrder());
        int freqHistoGram[] = new int[256];
        //build the character frequency histogram
        for(char c : str.toCharArray()){
            freqHistoGram[c]++;

            //if a character repeats more than n/2 then we can't rearrange
            if(freqHistoGram[c] > (n+1)/2){
                return str;
            }
        }
        //build the max heap of histogram
        for(char i  = 0; i < 256; i++){
            if(freqHistoGram[i] > 0)
                maxHeap.add(new CharFreq(i, freqHistoGram[i]));
        }

        //rearrange - pop top 2 most frequent items and arrange them in adjacent positions
        //decrease the histogram frequency of the selected chars
        while(!maxHeap.isEmpty()){
            //extract top one and decrease the hstogram by one
            CharFreq first = maxHeap.poll();
            rearranged.append(first.c);
            first.freq--;

            CharFreq second = null;
            //extract second top and decrease the histogram by one
            if(!maxHeap.isEmpty()){
                second = maxHeap.poll();
                rearranged.append(second.c);
                second.freq--;
            }

            //add back the updated histograms
            if(first.freq > 0){
                maxHeap.add(first);
            }
            if(second != null && second.freq > 0){
                maxHeap.add(second);
            }
        }

        return rearranged.toString();
    }
}
