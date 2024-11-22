import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Utilities for handling HTML
 * 
 * @author Mr Greenstein and Jonathan Chen
 * @since November 1, 2024
 */
public class HTMLUtilities{
    private enum TokenState{
		NONE, COMMENT, PREFORMAT
	}
    private TokenState state;
    public HTMLUtilities(){
        state = TokenState.NONE;
    }
    /**
     * Break the HTML string into tokens. The array returned is
     * exactly the size of the number of tokens in the HTML string.
     *
     * @param str the HTML string
     * @return the String array of tokens
     */
   public String[] tokenizeHTMLString(String str){
	    ArrayList<String> tokens = new ArrayList<>();
	    int resultIndex = 0;
	    boolean inMultiLineComment = false;
	    while(resultIndex < str.length()){
	        // Handle entering comment block
	        if(state == TokenState.NONE && isPrefix(str, "<!--", resultIndex)){
	            state = TokenState.COMMENT;
	            resultIndex += 4;
	        }
	        // Handle exiting comment block
	        else if(state == TokenState.COMMENT && isPrefix(str, "-->", resultIndex)){
	            state = TokenState.NONE;
	            resultIndex += 3;
	        }
	        // Ignore all content in COMMENT state
	        else if(state == TokenState.COMMENT){
	            resultIndex++;
	        }
	        // Handle entering preformatted block
	        else if(state == TokenState.NONE && isPrefix(str, "<pre>", resultIndex)){
	            state = TokenState.PREFORMAT;
	            tokens.add("<pre>");
	            resultIndex += 5;
	        }
	        // Handle exiting preformatted block
	        else if(state == TokenState.PREFORMAT && isPrefix(str, "</pre>", resultIndex)){
	            state = TokenState.NONE;
	            tokens.add("</pre>");
	            resultIndex += 6;
	        }
	        // Add entire line as single token in PREFORMAT state
	        else if(state == TokenState.PREFORMAT){
	            tokens.add(str.substring(resultIndex).trim());
	            resultIndex = str.length();
	        }
	        // Handle tokenization in NONE state(regular processing)
	        else if(state == TokenState.NONE){
	            if(str.charAt(resultIndex) == '<'){
	                int tagEnd = str.indexOf('>', resultIndex);
	                if(tagEnd != -1){
	                    tokens.add(str.substring(resultIndex, tagEnd+1));
	                    resultIndex = tagEnd;
	                }
	            }
	            else if(Character.isDigit(str.charAt(resultIndex))
							|| str.charAt(resultIndex) == '-'
							|| str.charAt(resultIndex) == '.'){
	                int endIndex = findNumberEndIndex(str, resultIndex);
	                tokens.add(str.substring(resultIndex, endIndex));
	                resultIndex = endIndex - 1;
	            }
	            else if(isPunctuation(str.charAt(resultIndex))){
	                tokens.add(str.substring(resultIndex, resultIndex + 1));
	            }
	            else if(isWordCharacter(str.charAt(resultIndex))){
	                int endIndex = findWordEndIndex(str, resultIndex);
	                tokens.add(str.substring(resultIndex, endIndex));
	                resultIndex = endIndex - 1;
	            }
	        }
	        resultIndex++;
	    }
	    return tokens.toArray(new String[0]);
	}
    /**
     * Print the tokens in the array to the screen.
     *
     * @param tokens an array of String tokens
     */
    public void printTokens(String[] tokens){
        if(tokens == null) return;
        for(int a = 0; a < tokens.length; a++){
            if(a % 5 == 0) System.out.print("\n  ");
            System.out.print("[token " + a + "]: " + tokens[a] + " ");
        }
        System.out.println();
    }
    /**
     * Checks if a string starts with the given prefix at the specified index.
     *
     * @param str the original string
     * @param prefix the prefix to check
     * @param index the starting index in the original string
     * @return true if str starts with prefix at index, false otherwise
     */
    public boolean isPrefix(String str, String prefix, int index){
		if(str.length() >= index + prefix.length()
		&& str.substring(index, index + prefix.length()).equals(prefix)){
			return true;
		}
		else{
			return false;
		}
    }
    public boolean isPunctuation(char punctuation){
        String punctuationChars = ".,;:()?!=&~+-";
        if(punctuationChars.indexOf(punctuation) >= 0){
			return true;
		}
		else{
			return false;
		}
    }
    public boolean isWordCharacter(char c){
		if((c >= 'a' && c <= 'z') ||(c >= 'A' && c <= 'Z')){
			return true;
		}
		else{
			return false;
		}
    }
    public int findNumberEndIndex(String str, int start){
        int index = start;
        while(index < str.length() &&(Character.isDigit(str.charAt(index))
						|| "e.-".indexOf(str.charAt(index)) >= 0)){
            index++;
        }
        return index;
    }
	public int findWordEndIndex(String str, int start){
        int index = start;
        while(index < str.length()&&(isWordCharacter(str.charAt(index))
				|| str.charAt(index) == '-')){
            index++;
        }
        return index;
    }
    public int findResultLength(String[] result){
	    int length = result.length;
	    while(length > 0 && result[length - 1] == null){
	        length--;
	    }
	    return length;
	}
}
