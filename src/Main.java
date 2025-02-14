import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String cipher1 = cipher("Meet me by the lake at midnight. Bring shovel.", "python");
        System.out.println(cipher1);
        System.out.println("thaiivelmhglmetgnembaitsetenroeykdbh".equals(cipher1));

        String cipher2 = cipher("Mission Delta Kilo Sierra has been compromised. Kill Steve. Evacuate", "cake");
        System.out.println(cipher2);
        System.out.println("ioliiabcrsiteuxmieksrsnpiksecesdaoraemmdlvatxsntleheooelevax".equals(cipher2));

        String deciphered1 = cipher("meeanbsleyamgioxebltirhxttkihnvxmhedtgex", "monty");
        System.out.println(deciphered1);
        System.out.println("meetmebythelakeatmidnightbringshovelxxxx".equals(deciphered1));
    }

    public static String cipher(String text, String keyword) {
        StringBuilder finalMessage = new StringBuilder();

        // for text that needs to be ciphered
        //check if is normal text - then will have spaces
        if (text.contains(" ")) {
            // convert text into an array of chars
            ArrayList<Character> charList = new ArrayList<>();
            for (int i=0; i<text.length(); i++) {
                //don't include punctuation, spaces and make lower case
                if (!Character.isWhitespace(text.charAt(i)) && !Character.toString(text.charAt(i)).matches("\\p{Punct}")) {
                    charList.add(Character.toLowerCase(text.charAt(i)));
                }
            }

            // make a grid for all letters, grid is a 2d array but dynamic & resizable - so don't have to declare size
            //grid is a collection of arraylist objects (each is a row)
            ArrayList<ArrayList<Character>> grid = new ArrayList<>();

            // while still have chars left to place into arraylist
            while (!charList.isEmpty()) {
                //has to be Character (wrapper class) instead of char (primitive data type)
                ArrayList<Character> letters = new ArrayList<>();
                //organize chars into arraylists
                for (int i=0; i<keyword.length(); i++) {
                    //even if there is an outer while loop that checks if charlist is empty,
                    //still have to check again as charlist might be exhausted during an iteration of this for loop
                    if (!charList.isEmpty()) {
                        letters.add(charList.get(0));
                        charList.remove(0);
                    }
                    else {
                        // fill with x if charlist is empty but still has keyword spaces left to fill
                        letters.add('x');
                    }
                }
                grid.add(letters);
            }

            //find columns' alphabetical order and accordingly order into string builder
            StringBuilder keywordDynamic = new StringBuilder(keyword);
            while (!keywordDynamic.isEmpty()) {
                //find the alphabetically smallest char in keyword
                int smallest = 0;
                //make sure still have chars left to compare to/not the only char left
                if (keywordDynamic.length()>1) {
                    for (int i=1; i<keywordDynamic.length(); i++) {
                        //compares which is alphabetically smaller
                        if (keywordDynamic.charAt(smallest)>keywordDynamic.charAt(i)) {
                            smallest = i;
                        }
                    }
                }
                //add each char from that smallest column into the string
                for (int i=0; i<grid.size(); i++) {
                    finalMessage.append(grid.get(i).get(smallest));
                }
                //remove that column
                for (ArrayList<Character> row : grid) {
                    row.remove(smallest);
                }
                //remove the letter from the keyword
                keywordDynamic.deleteCharAt(smallest);
            }

        }

        //for text that needs to deciphered
        else {
            //split the string into an arraylist with the length of the keyword
            //how long each substring should be (wouldn't be able to calculate this the other way around -- in ciphering)
            int lengthOfSubstrings = text.length()/keyword.length();
            ArrayList<String> stringSplit = new ArrayList<>();
            int count = 0;
            while (count<text.length()) {
                //substring = inclusive start index & exclusive end index
                stringSplit.add(text.substring(count,count+lengthOfSubstrings));
                count+=lengthOfSubstrings;
            }

            //assign numerical value to each character in keyword based on its alphabetical order
            //called rank sorting (similar logic to selection sort)
            int[] order = new int[keyword.length()];
            //iterating through each char of keyword
            for (int i=0; i<keyword.length(); i++) {
                int rank = 0;
                for (int j=0; j<keyword.length(); j++) {
                    //count how many characters are smaller than the charAt(i)
                    if (keyword.charAt(j)<keyword.charAt(i)) {
                        rank++;
                    }
                }
                order[i]=rank;
            }

            //order arraylist by the numerical values
            ArrayList<String> reorderedStrings = new ArrayList<>();
            for (int i: order) {
                reorderedStrings.add(stringSplit.get(i));
            }

            //place arraylist into a grid
            ArrayList<ArrayList<Character>> grid = new ArrayList<>();
            for (int i=0; i<reorderedStrings.get(0).length(); i++) {
                ArrayList<Character> letters = new ArrayList<>();
                for (int j=0; j<reorderedStrings.size(); j++) {
                    letters.add(reorderedStrings.get(j).charAt(i));
                }
                grid.add(letters);
            }

            //convert grid into a string
            for (ArrayList<Character> letters : grid) {
                for (Character character : letters) {
                    finalMessage.append(character);
                }
            }
        }

        return finalMessage.toString();
    }
}
// check whether is completed to put x with keyword length