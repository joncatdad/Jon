/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester{
  /** Method to test zeroBlue */
  public static void testZeroBlue(){
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  /** Method to test mirrorVertical */
  public static void testMirrorVertical(){
    Picture caterpillar = new Picture("images/caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  /** Method to test mirrorTemple */
  public static void testMirrorTemple(){
    Picture temple = new Picture("images/temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  /** Method to test the collage method */
  public static void testCollage(){
    Picture canvas = new Picture("images/640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  /** Method to test edgeDetection */
  public static void testEdgeDetection(){
    Picture swan = new Picture("images/swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  public static void testKeepOnlyBlue(){
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.keepOnlyBlue();
    beach.explore();
  }
  public static void testNegate(){
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.negate();
    beach.explore();
  }
  public static void testGrayscale(){
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.grayscale();
	beach.explore();
  }
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args){
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //testZeroBlue();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
    //testPixelate();
    //testBlur();
    //testEnhance();
    //testSwapLeftRight();
    //testStairStep();
    //testLiquify();
    //testWavy();
    testEdgeDetectionBelow();
    //testGreenScreen();
    //testRotate();
  }
  /** Method to test pixelate */
  public static void testPixelate(){
	Picture pic = new Picture("images/swan.jpg");
    pic.explore();
    pic.pixelate(10);
    pic.explore();
 }
 /** Method to test blur */
 public static void testBlur(){
    Picture pic = new Picture("images/beach.jpg");
    pic.explore();
    Picture blurred = pic.blur(10);
    blurred.explore();
 }
 /** Method to test enhance */
 public static void testEnhance(){
    Picture pic = new Picture("images/beach.jpg");
    pic.explore();
    Picture enhanced = pic.enhance(10);
    enhanced.explore();
 }
 /** Method to test swapping the left and right*/
 public static void testSwapLeftRight(){
	Picture pic = new Picture("images/redMotorcycle.jpg");
	pic.explore();
	Picture swap = pic.swapLeftRight();
	swap.explore();
 }
 /** Method to test stairs step */
 public static void testStairStep(){
	Picture motorcycle = new Picture("images/redMotorcycle.jpg");
	motorcycle.explore();
	Picture steps = motorcycle.stairStep(10, 10);
	steps.explore();
 }
 /** Method to test liquify */
 public static void testLiquify(){
	Picture pic = new Picture("images/redMotorcycle.jpg");
	pic.explore();
	Picture watery = pic.liquify(100);
	watery.explore();
 }
 /** Method to test wavy */
 public static void testWavy(){
	Picture pic = new Picture("images/redMotorcycle.jpg");
	pic.explore();
	Picture waves = pic.wavy(200);
	waves.explore();
 }
 /** Method to test edge dectection */
 public static void testEdgeDetectionBelow(){
    Picture pic = new Picture("images/beach.jpg"); // Use an actual image file
    pic.explore();
    Picture edgePic = pic.edgeDetectionBelow(20); // Adjust threshold as needed
    edgePic.explore(); // View the result
 }
 /** Method to test green screen */
 public static void testGreenScreen(){
    Picture pic = new Picture("images/beach.jpg"); // This is just a placeholder
    Picture result = pic.greenScreen();
    result.explore(); // View result
 }
 /** Method to test rotate */
 public static void testRotate(){
    Picture pic = new Picture("images/beach.jpg"); // Change to an actual image path
    Picture rotatedPic = pic.rotate(Math.PI / 6); // Rotate by 30 degrees
    rotatedPic.explore();
 }
}
