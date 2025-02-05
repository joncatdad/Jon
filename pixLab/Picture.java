import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture(){
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName){
    // let the parent class handle this fileName
    super(fileName);
  }
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width){
    // let the parent class handle this width and height
    super(width,height);
  }
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture){
    // let the parent class do the copy
    super(copyPicture);
  }
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image){
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString(){
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output; 
  }
  /** Method to set the blue to 0 */
  public void zeroBlue(){
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels){
      for(Pixel pixelObj : rowArray){
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical(){
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for(int row = 0; row < pixels.length; row++){
      for(int col = 0; col < width / 2; col++){
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple(){
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    // loop through the rows
    for(int row = 27; row < 97; row++){
      // loop from 13 to just before the mirror point
      for(int col = 13; col < mirrorPoint; col++){
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, int startRow, int startCol){
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for(int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length 
         && toRow < toPixels.length; 
         fromRow++, toRow++){
      for(int fromCol = 0, toCol = startCol; 
      fromCol < fromPixels[0].length 
      && toCol < toPixels[0].length; fromCol++, toCol++){
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  /** Method to create a collage of several pictures */
  public void createCollage(){
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist){
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for(int row = 0; row < pixels.length; row++){
      for(int col = 0; col < pixels[0].length-1; col++){
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if(leftPixel.colorDistance(rightColor) > edgeDist){
          leftPixel.setColor(Color.BLACK);
	    }
        else{
          leftPixel.setColor(Color.WHITE);
	    }
      }
    }
  }
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args){
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  public void keepOnlyBlue(){
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels){
      for(Pixel pixelObj : rowArray){
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  public void negate(){
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels){
        for(Pixel pixelObj : rowArray){
            pixelObj.setRed(255 - pixelObj.getRed());
            pixelObj.setGreen(255 - pixelObj.getGreen());
            pixelObj.setBlue(255 - pixelObj.getBlue());
        }
    }
 }
 public void grayscale(){
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels){
        for(Pixel pixelObj : rowArray){
            int avg =(pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3;
            pixelObj.setRed(avg);
            pixelObj.setGreen(avg);
            pixelObj.setBlue(avg);
        }
    }
 }
  /** To pixelate by dividing area into size x size.
 * @param size Side length of square area to pixelate.
 */
public void pixelate(int size){
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    for(int row = 0; row < height; row += size){
        for(int col = 0; col < width; col += size){
            int totalRed = 0, totalGreen = 0, totalBlue = 0, count = 0;
            // Calculate average color in the size x size square
            for(int r = row; r < row + size && r < height; r++){
                for(int c = col; c < col + size && c < width; c++){
                    Pixel p = pixels[r][c];
                    totalRed += p.getRed();
                    totalGreen += p.getGreen();
                    totalBlue += p.getBlue();
                    count++;
                }
            }
            int avgRed = totalRed / count;
            int avgGreen = totalGreen / count;
            int avgBlue = totalBlue / count;
            Color avgColor = new Color(avgRed, avgGreen, avgBlue);
            // Set all pixels in the square to the average color
            for(int r = row; r < row + size && r < height; r++){
                for(int c = col; c < col + size && c < width; c++){
                    pixels[r][c].setColor(avgColor);
                }
            }
        }
    }
}
/** Method that blurs the picture
 * @param size Blur size, greater is more blur
 * @return Blurred picture
 */
public Picture blur(int size){
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(this);
    Pixel[][] resultPixels = result.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
            int totalRed = 0, totalGreen = 0, totalBlue = 0, count = 0;
            for(int r = row - size / 2; r <= row + size / 2; r++){
                for(int c = col - size / 2; c <= col + size / 2; c++){
                    if(r >= 0 && r < height && c >= 0 && c < width){
                        Pixel p = pixels[r][c];
                        totalRed += p.getRed();
                        totalGreen += p.getGreen();
                        totalBlue += p.getBlue();
                        count++;
                    }
                }
            }
            int avgRed = totalRed / count;
            int avgGreen = totalGreen / count;
            int avgBlue = totalBlue / count;
            resultPixels[row][col].setColor(new Color(avgRed, avgGreen, avgBlue));
        }
    }
    return result;
}
/** Method that enhances a picture by getting average Color around
 * a pixel then applies the following formula:
 *
 * pixelColor <- 2 * currentValue - averageValue
 *
 * size is the area to sample for enhancement.
 *
 * @param size Larger means more area to average around pixel
 * and longer compute time.
 * @return enhanced picture
 */
public Picture enhance(int size){
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(this);
    Pixel[][] resultPixels = result.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
            int totalRed = 0, totalGreen = 0, totalBlue = 0, count = 0;
            for(int r = row - size / 2; r <= row + size / 2; r++){
                for(int c = col - size / 2; c <= col + size / 2; c++){
                    if(r >= 0 && r < height && c >= 0 && c < width){
                        Pixel p = pixels[r][c];
                        totalRed += p.getRed();
                        totalGreen += p.getGreen();
                        totalBlue += p.getBlue();
                        count++;
                    }
                }
            }
            int avgRed = totalRed / count;
            int avgGreen = totalGreen / count;
            int avgBlue = totalBlue / count;
            Pixel original = pixels[row][col];
            int newRed = Math.min(255, Math.max(0, 2 * original.getRed() - avgRed));
            int newGreen = Math.min(255, Math.max(0, 2 * original.getGreen() - avgGreen));
            int newBlue = Math.min(255, Math.max(0, 2 * original.getBlue() - avgBlue));
            resultPixels[row][col].setColor(new Color(newRed, newGreen, newBlue));
        }
    }
    return result;
}
/** Method to enhance fish visibility in the underwater image */
public void fixUnderwater(){
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] rowArray : pixels){
        for(Pixel pixelObj : rowArray){
            int red = pixelObj.getRed();
            int green = pixelObj.getGreen();
            int blue = pixelObj.getBlue();
            // Increase red component to make fish stand out
            red = Math.min(255,(int)(red * 1.5)); 
            // Reduce green and blue slightly to enhance contrast
            green =(int)(green * 0.8);
            blue =(int)(blue * 0.8);
            pixelObj.setColor(new Color(red, green, blue));
        }
    }
}
/** Method to add a text watermark to the image */
public void addWatermark(String text){
    Graphics2D g2d = this.getBufferedImage().createGraphics();
    // Set font and color(semi-transparent white)
    g2d.setFont(new Font("Arial", Font.BOLD, 40));
    g2d.setColor(new Color(255, 255, 255, 128)); // 128 = 50% transparency
    // Position watermark at bottom right
    int x = this.getWidth() - 200; 
    int y = this.getHeight() - 50;
    g2d.drawString(text, x, y);
    g2d.dispose();
}
} // this } is the end of class Picture, put all new methods before this
