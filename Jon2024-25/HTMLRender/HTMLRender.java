/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 * 
 * 	My version of HTMLUtilities is incorrect - should be correct with 
 * 	correct HTMLUtilities class, some statements mistokenized
 * 	such as <i>italic, statement before comment, last number of the numbers
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline(break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Jonathan Chen 
 *	@since November 18, 2024
 *  @version
 */
import java.util.Scanner;
public class HTMLRender{
    private HTMLUtilities hUtils;
    private int charNum;
    private boolean htmlBoolean;
    private boolean bodyBoolean;
    private boolean paraBoolean;
    private boolean quoteBoolean;
    private boolean boldBoolean;
    private boolean italBoolean;
    private boolean preBoolean;
    private boolean h1Boolean;
    private boolean h2Boolean;
    private boolean h3Boolean;
    private boolean h4Boolean;
    private boolean h5Boolean;
    private boolean h6Boolean;
    private final int GEN_LENGTH = 80;
    private final int H1_LENGTH = 40;
    private final int H2_LENGTH = 50;
    private final int H3_LENGTH = 60;
    private final int H4_LENGTH = 80;
    private final int H5_LENGTH = 100;
    private final int H6_LENGTH = 120;
    private String[] tokens; // Use array for tokens
    private int tokenIndex; // Track the current index in the array
    private final int TOKENS_SIZE = 100000; // Size of token array
    private SimpleHtmlRenderer render;
    private HtmlPrinter browser;
    public HTMLRender(){
        hUtils = new HTMLUtilities();
        charNum = 0;
        tokens = new String[TOKENS_SIZE];  // Array for tokens
        tokenIndex = 0; // Initialize token index
        htmlBoolean = false;
        bodyBoolean = false;
        paraBoolean = false;
        quoteBoolean = false;
        boldBoolean = false;
        italBoolean = false;
        preBoolean = false;
        h1Boolean = false;
        h2Boolean = false;
        h3Boolean = false;
        h4Boolean = false;
        h5Boolean = false;
        h6Boolean = false;
        render = new SimpleHtmlRenderer();
        browser = render.getHtmlPrinter();
    }
    public static void main(String[] args){
        HTMLRender hRender = new HTMLRender();
        FileUtils fUtils = new FileUtils();
        Scanner reader = fUtils.openToRead(args[0]);
        hRender.run(reader);
    }
    public void run(Scanner input){
        String line = "";
        while(input.hasNextLine()){
            line = input.nextLine();
            String[] token = hUtils.tokenizeHTMLString(line);  // Tokenize the line
            addToTokens(token);  // Add the tokens to the array
            if(!htmlBoolean || !bodyBoolean){
                scanFirstTokens(token);  // Check for <html> and <body> tags
            }
        }
        input.close();
        if(htmlBoolean && bodyBoolean){
            tokenize();  // Process the tokens if <html> and <body> are found
        }
    }
    public void addToTokens(String[] tokensToAdd){
        for(String token : tokensToAdd){
            if(tokenIndex < TOKENS_SIZE){
                tokens[tokenIndex++] = token;  // Add token to the array and increment index
            }
        }
    }
    public void scanFirstTokens(String[] check){
        for(String s : check){
            if(s.equalsIgnoreCase("<html>")){
                htmlBoolean = true;
            }
            if(s.equalsIgnoreCase("<body>")){
                bodyBoolean = true;
			}
        }
    }
    public void tokenize(){
        for(int i = 2; i < tokenIndex; i++){ // Use tokenIndex for array bounds
            String token = tokens[i];
            if(token != null){
                checkWhichToken(token); // Check the type of token
                if(htmlBoolean && bodyBoolean){
                    if(token.charAt(0) != '<'){
                        processTextToken(token); // Handle text tokens based on the current state
                    }
                }
            }
        }
    }
    public void processTextToken(String token){
        if(paraBoolean && !boldBoolean && !italBoolean){
            printToken(token, GEN_LENGTH);
        }
        else if(paraBoolean && boldBoolean && !italBoolean){
            printBoldToken(token, GEN_LENGTH);
        }
        else if(paraBoolean && !boldBoolean && italBoolean){
            printItalicToken(token, GEN_LENGTH);
        }
        else if(boldBoolean){
            printBoldToken(token, GEN_LENGTH);
        }
        else if(italBoolean){
            printItalicToken(token, GEN_LENGTH);
        }
        else if(quoteBoolean){
            printToken(token, GEN_LENGTH);
        }
        else if(h1Boolean){
            printHeadingToken(token, H1_LENGTH);
        }
        else if(h2Boolean){
            printHeadingToken(token, H2_LENGTH);
        }
        else if(h3Boolean){
            printHeadingToken(token, H3_LENGTH);
        }
        else if(h4Boolean){
            printHeadingToken(token, H4_LENGTH);
        }
        else if(h5Boolean){
            printHeadingToken(token, H5_LENGTH);
        }
        else if(h6Boolean){
            printHeadingToken(token, H6_LENGTH);
        }
        else if(preBoolean){
            browser.printPreformattedText(token);
            browser.println();
        }
        else{
            printToken(token, GEN_LENGTH);
        }
    }
    public void printToken(String token, int maxLength){
        if(hUtils.isPunctuation(token.charAt(0), 0, "")){
            browser.print(token);
        }
        else{
            browser.print(" " + token);
        }
        charNum += token.length();
        if(charNum >= maxLength){
            browser.println();
            charNum = 0;
        }
    }
    public void printBoldToken(String token, int maxLength){
        if(hUtils.isPunctuation(token.charAt(0), 0, "")){
            browser.printBold(token);
        }
        else{
            browser.printBold(" " + token);
        }
        charNum += token.length();
        if(charNum >= maxLength){
            browser.println();
            charNum = 0;
        }
    }
    public void printItalicToken(String token, int maxLength){
        if(hUtils.isPunctuation(token.charAt(0), 0, "")){
            browser.printItalic(token);
        }
        else{
            browser.printItalic(" " + token);
        }
        charNum += token.length();
        if(charNum >= maxLength){
            browser.println();
            charNum = 0;
        }
    }
    public void printHeadingToken(String token, int maxLength){
        browser.printHeading1(token);  // Adjust based on heading level
        charNum += token.length();
        if(charNum >= maxLength){
            browser.println();
            charNum = 0;
        }
    }
    public void checkWhichToken(String tempToken){
        // Handle different tokens based on HTML tags
        if(tempToken.equalsIgnoreCase("<p>")){
            paraBoolean = true;
            browser.println();
        }
        else if(tempToken.equalsIgnoreCase("</p>")){
            paraBoolean = false;
            browser.println();
        }
        else if(tempToken.equalsIgnoreCase("<q>")){
            quoteBoolean = true;
            browser.print(" \"");
        }
        else if(tempToken.equalsIgnoreCase("</q>")){
            quoteBoolean = false;
            browser.print("\"");
        }
        else if(tempToken.equalsIgnoreCase("<b>")){
            boldBoolean = true;
        }
        else if(tempToken.equalsIgnoreCase("</b>")){
            boldBoolean = false;
        }
        else if(tempToken.equalsIgnoreCase("<i>")){
            italBoolean = true;
        }
        else if(tempToken.equalsIgnoreCase("</i>")){
            italBoolean = false;
        }
        else if(tempToken.equalsIgnoreCase("<hr>")){
            browser.printHorizontalRule();
        }
        else if(tempToken.equalsIgnoreCase("<br>")){
            browser.printBreak();
        }
        else if(tempToken.equalsIgnoreCase("<h1>")){
            h1Boolean = true;
        }
        else if(tempToken.equalsIgnoreCase("</h1>")){
            h1Boolean = false;
        }
        else if(tempToken.equalsIgnoreCase("<pre>")){
            preBoolean = true;
        }
        else if(tempToken.equalsIgnoreCase("</pre>")){
            preBoolean = false;
        }
    }
}
