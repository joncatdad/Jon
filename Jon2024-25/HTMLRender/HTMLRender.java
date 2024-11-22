import java.util.Scanner;
/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
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
 *  @since November 18, 2024
 *	@version
 */
public class HTMLRender{
    private String[] tokens;  // Array to hold all HTML tokens
    private final int TOKENS_SIZE = 100000; // Maximum size of token array
    // SimpleHtmlRenderer fields
    private SimpleHtmlRenderer render;
    private HtmlPrinter browser;
    // HTMLUtilities instance
    private HTMLUtilities htmlUtilities;
    public HTMLRender(){
        // Initialize token array
        tokens = new String[TOKENS_SIZE];
        // Initialize Simple Browser
        render = new SimpleHtmlRenderer();
        browser = render.getHtmlPrinter();
        // Initialize HTMLUtilities
        htmlUtilities = new HTMLUtilities();
    }
    public static void main(String[] args){
        HTMLRender hf = new HTMLRender();
        hf.run(args);
    }
    public void run(String [] args){
        Scanner input = null;
		String fileName = "";
		// if the command line contains the file name, then store it
		if(args.length > 0)
			fileName = args[0];
		// otherwise print out usage message
		else{
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
		// Read each line of the HTML file, tokenize, then print tokens
		while(input.hasNext()){
			String line = input.nextLine();
			System.out.println("\n" + line);
			String [] tokens = htmlUtilities.tokenizeHTMLString(line);
			htmlUtilities.printTokens(tokens);
		}
		input.close();
        // Read and tokenize the HTML file
        readAndTokenizeHTML(fileName);
        // Render the tokens
        renderTokens();
    }
    /**
     * Reads and tokenizes an HTML file using FileUtils and HTMLUtilities.
     *
     * @param fileName the name of the HTML file to read
     */
    public void readAndTokenizeHTML(String fileName){
	    try(Scanner fileScanner = FileUtils.openToRead(fileName)){
	        StringBuilder htmlContent = new StringBuilder();
	        // Read the entire file content
	        while(fileScanner.hasNextLine()){
	            htmlContent.append(fileScanner.nextLine()).append("\n");
	        }
	        // Tokenize the HTML content
	        tokens = htmlUtilities.tokenizeHTMLString(htmlContent.toString());
	        System.out.println("Tokenization complete.");
	    }
	    catch(Exception e){
	        System.err.println("Error reading or tokenizing the file: " + e.getMessage());
	    }
	}
    /**
     * Renders the tokens using the SimpleHtmlRenderer and HtmlPrinter.
     */
    public void renderTokens(){
	    if(tokens == null){
	        System.err.println("No tokens available to render.");
	        return;
	    }
	    // Loop through all tokens
	    for(int i = 0; i < tokens.length; i++){
	        String token = tokens[i];
	        if(token == null){
	            continue; // Skip null tokens
	        }
			if (token.equalsIgnoreCase("<p>")) {
			    browser.printBreak(); // Line feed before paragraph
			}
			if (token.equalsIgnoreCase("</p>")) {
			    browser.printBreak(); // Blank line after paragraph
			}
			if (token.equalsIgnoreCase("<br>")) {
			    browser.printBreak(); // Newline
			}
			if (token.equalsIgnoreCase("<hr>")) {
			    browser.printHorizontalRule(); // Horizontal rule
			}
			if (token.equalsIgnoreCase("<b>")) {
			    browser.printBold(""); // Bold start
			}
			if (token.equalsIgnoreCase("</b>")) {
			    browser.printBold(""); // Bold end
			}
			if (token.equalsIgnoreCase("<i>")) {
			    browser.printItalic(""); // Italic start
			}
			if (token.equalsIgnoreCase("</i>")) {
			    browser.printItalic(""); // Italic end
			}
			if (token.equals("<pre>")) {
			    String preText = getNextTextToken(i);
			    browser.printPreformattedText(preText);
			    browser.println();
			}
			if (token.startsWith("<h")
			&& token.endsWith(">")) {
			    int headingLevel = Integer.parseInt(token.substring(2, token.length() - 1));
			    String text = getNextTextToken(i);
			    browser.printBreak();
			    renderHeading(headingLevel, text);
			}
			if (!token.startsWith("<")) { // Plain text (not a tag)
			    browser.print(token + " "); // Print plain text with a space
			}
	    }
	}
    /**
     * Renders a heading based on its level.
     *
     * @param level the heading level(1 to 6)
     * @param text the text to render in the heading
     */
    public void renderHeading(int level, String text){
        switch(level){
            case 1:
                browser.printHeading1(text);
                break;
            case 2:
                browser.printHeading2(text);
                break;
            case 3:
                browser.printHeading3(text);
                break;
            case 4:
                browser.printHeading4(text);
                break;
            case 5:
                browser.printHeading5(text);
                break;
            case 6:
                browser.printHeading6(text);
                break;
            default:
                System.err.println("Invalid heading level: " + level);
                break;
        }
    }
    /**
     * Gets the next plain text token for tags that require inner text.
     *
     * @return the next text token
     */
    public String getNextTextToken(int currentIndex){
        for(int i = currentIndex + 1; i < tokens.length; i++){
            String token = tokens[i];
            if(token != null && !token.startsWith("<")){
                return token;
            }
        }
        return ""; // If no text token found, return empty string
    }
}
