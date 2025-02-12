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
        rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
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
	Picture pic = new Picture();
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
             int avg = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3;
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
	Picture result = new Picture(pixels.length, pixels[0].length);
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
  * size is the area to sample for blur.
  *
  * @param size Larger means more area to average around pixel
  * and longer compute time.
  * @return enhanced picture
  */
 public Picture enhance(int size){
	Pixel[][] pixels = this.getPixels2D();
	Picture result = new Picture(pixels.length, pixels[0].length);
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
 /** Moves the left half of the picture to the right and vice versa.*/
 public Picture swapLeftRight(){
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    Picture result = new Picture(this); // Create a copy of the current picture
    Pixel[][] newPixels = result.getPixels2D();
    for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
            int newCol = (col + width / 2) % width; // Shift right and wrap
            newPixels[row][newCol].setColor(pixels[row][col].getColor());
        }
    }
    return result;
 }
 /** Creates a stair-step distortion effect.
  * @param shiftCount The number of pixels to shift to the right
  * @param steps The number of steps
  * @return The picture with pixels shifted in stair steps
  */
 public Picture stairStep(int shiftCount, int steps){
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    Picture result = new Picture(this);
    Pixel[][] newPixels = result.getPixels2D();
    for(int row = 0; row < height; row++){
        int stepShift = (row /(height / steps)) * shiftCount; // Calculate step shift
        for(int col = 0; col < width; col++){
            int newCol = (col + stepShift) % width; // Wrap around
            newPixels[row][newCol].setColor(pixels[row][col].getColor());
        }
    }
    return result;
 }
 /** Uses a Gaussian curve to warp the horizontal center.
  * @param maxFactor Max height(shift) of curve in pixels
  * @return Liquified picture
  */
 public Picture liquify(int maxHeight){
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    int bellWidth = height / 3; // Standard deviation for Gaussian curve
    Picture result = new Picture(this);
    Pixel[][] newPixels = result.getPixels2D();
    for(int row = 0; row < height; row++){
        double exponent = Math.pow(row - height / 2.0, 2) /(2.0 * Math.pow(bellWidth, 2));
        int rightShift = (int)(maxHeight * Math.exp(-exponent)); // Gaussian shift
        for(int col = 0; col < width; col++){
            int newCol = (col + rightShift) % width; // Wrap around
            newPixels[row][newCol].setColor(pixels[row][col].getColor());
        }
    }
    return result;
 }
 /** Creates a sinusoidal wave distortion effect.
  * @param amplitude The maximum shift of pixels
  * @return Wavy picture
  */
 public Picture wavy(int amplitude){
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    double frequency = 2 * Math.PI / height; // Controls wave frequency
    Picture result = new Picture(this);
    Pixel[][] newPixels = result.getPixels2D();
    for(int row = 0; row < height; row++){
        int waveShift = (int)(amplitude * Math.sin(frequency * row)); // Sinusoidal shift
        for(int col = 0; col < width; col++){
            int newCol = (col + waveShift + width) % width; // Ensure positive index
            newPixels[row][newCol].setColor(pixels[row][col].getColor());
        }
    }
    return result;
 }
 /** Method that detects edges by comparing each pixel with the one below
  * @param threshold Threshold for edge detection
  * @return Edge-detected black and white picture
  */
 public Picture edgeDetectionBelow(int threshold){
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();   
    for(int row = 0; row < pixels.length - 1; row++){ // Stop before the last row
        for(int col = 0; col < pixels[0].length; col++){
            Pixel currentPixel = pixels[row][col];
            Pixel belowPixel = pixels[row + 1][col]; // Pixel below
            if(currentPixel.colorDistance(belowPixel.getColor()) > threshold){
                resultPixels[row][col].setColor(Color.BLACK);
            }
            else{
                resultPixels[row][col].setColor(Color.WHITE);
            }
        }
    }
    return result;
 }
 /** Method that applies a green screen effect
  * @return Picture with green screen objects placed on a background
  */
 public Picture greenScreen(){
    // Load background image
    Picture background = new Picture("greenScreenImages/IndoorHouseLibraryBackground.jpg");
    Pixel[][] bgPixels = background.getPixels2D();
    // Load and scale foreground images
    Picture cat = new Picture("greenScreenImages/kitten1GreenScreen.jpg");
    cat = scaleDownManual(cat, 200, 180);
    Picture mouse = new Picture("greenScreenImages/mouse1GreenScreen.jpg");
    mouse = scaleDownManual(mouse, 140, 80);
    Pixel[][] catPixels = cat.getPixels2D();
    Pixel[][] mousePixels = mouse.getPixels2D();
    // Positions to place cat and mouse
    int catStartRow = 350, catStartCol = 500;
    int mouseStartRow = 350, mouseStartCol = 300;
    // Green screen threshold values
    int blendRange = 10;
    // Process cat image
    for(int row = 0; row < catPixels.length; row++){
        for(int col = 0; col < catPixels[0].length; col++){
            Pixel catPixel = catPixels[row][col];
            Pixel bgPixel = bgPixels[catStartRow + row][catStartCol + col];
            int red = catPixel.getRed();
            int green = catPixel.getGreen();
            int blue = catPixel.getBlue();
            // Check if it's green screen
            if(green > red * 1.5 && green > blue * 1.5){
                int greenDiff = green - Math.max(red, blue);
                if(greenDiff < blendRange){
                    double alpha = greenDiff /(double) blendRange;
                    int blendedRed = (int)((1 - alpha) * red + alpha * bgPixel.getRed());
                    int blendedGreen = (int)((1 - alpha) * green + alpha * bgPixel.getGreen());
                    int blendedBlue = (int)((1 - alpha) * blue + alpha * bgPixel.getBlue());
                    bgPixel.setColor(new Color(blendedRed, blendedGreen, blendedBlue));
                }
            }
            else{
                bgPixel.setColor(catPixel.getColor());
            }
        }
    }
    // Process mouse image(same logic)
    for(int row = 0; row < mousePixels.length; row++){
        for(int col = 0; col < mousePixels[0].length; col++){
            Pixel mousePixel = mousePixels[row][col];
            Pixel bgPixel = bgPixels[mouseStartRow + row][mouseStartCol + col];
            int red = mousePixel.getRed();
            int green = mousePixel.getGreen();
            int blue = mousePixel.getBlue();
            if(green > red * 1.5 && green > blue * 1.5){
                int greenDiff = green - Math.max(red, blue);
                if(greenDiff < blendRange){
                    double alpha = greenDiff /(double) blendRange;
                    int blendedRed = (int)((1 - alpha) * red + alpha * bgPixel.getRed());
                    int blendedGreen = (int)((1 - alpha) * green + alpha * bgPixel.getGreen());
                    int blendedBlue = (int)((1 - alpha) * blue + alpha * bgPixel.getBlue());
                    bgPixel.setColor(new Color(blendedRed, blendedGreen, blendedBlue));
                }
            }
            else{
                bgPixel.setColor(mousePixel.getColor());
            }
        }
    }
    return background;
 }
 public Picture scaleDownManual(Picture original, int newWidth, int newHeight){
    Pixel[][] originalPixels = original.getPixels2D();
    Picture resized = new Picture(newHeight, newWidth);
    Pixel[][] resizedPixels = resized.getPixels2D();
    double xRatio = (double) originalPixels[0].length / newWidth;
    double yRatio = (double) originalPixels.length / newHeight;
    for(int newRow = 0; newRow < newHeight; newRow++){
        for(int newCol = 0; newCol < newWidth; newCol++){
            int origX = (int)(newCol * xRatio);
            int origY = (int)(newRow * yRatio);
            resizedPixels[newRow][newCol].setColor(originalPixels[origY][origX].getColor());
        }
    }
    return resized;
 }
 /** Method to rotate an image by a given angle(in radians)
  * @param angle Angle of rotation in radians
  * @return Rotated picture
  */
 public Picture rotate(double angle){
    Pixel[][] pixels = this.getPixels2D();
    int height = pixels.length;
    int width = pixels[0].length;
    // Compute new dimensions
    int newWidth = (int)(Math.abs(width * Math.cos(angle)) + Math.abs(height * Math.sin(angle)));
    int newHeight = (int)(Math.abs(width * Math.sin(angle)) + Math.abs(height * Math.cos(angle)));
    Picture result = new Picture(newHeight, newWidth);
    Pixel[][] resultPixels = result.getPixels2D();
    int centerX = width / 2;
    int centerY = height / 2;
    int newCenterX = newWidth / 2;
    int newCenterY = newHeight / 2;
    // Reverse mapping: iterate over the new image
    for(int newRow = 0; newRow < newHeight; newRow++){
        for(int newCol = 0; newCol < newWidth; newCol++){
            // Find corresponding original coordinates
            int x = newCol - newCenterX;
            int y = newRow - newCenterY;
            int origX = (int)(x * Math.cos(-angle) - y * Math.sin(-angle)) + centerX;
            int origY = (int)(x * Math.sin(-angle) + y * Math.cos(-angle)) + centerY;
            // Check if the original coordinates are within bounds
            if(origX >= 0 && origX < width && origY >= 0 && origY < height){
                resultPixels[newRow][newCol].setColor(pixels[origY][origX].getColor());
            }
            else{
                // Fill empty spots with a nearby pixel(optional: interpolation)
                resultPixels[newRow][newCol].setColor(Color.WHITE); // Or use average color
            }
        }
    }
    return result;
 }
} // this } is the end of class Picture, put all new methods before this
