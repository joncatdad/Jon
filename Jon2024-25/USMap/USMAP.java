/**
 *	USMAP is for drawing the map for United States
 *	
 *	@author Jonathan Chen
 *	@since	September 4, 2024
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class USMAP {
    private int MAX_CITIES = 10000;
    private int MAX_BIG_CITIES = 276;
    private int TOP_10_LARGEST_CITIES = 10;
    private double[] xCoordinates = new double[MAX_CITIES];
    private double[] yCoordinates = new double[MAX_CITIES];
    private String[] cityNames = new String[MAX_CITIES];
    private int[] population = new int[MAX_BIG_CITIES];
    private String[] bigCityNames = new String[MAX_BIG_CITIES];
    private int cityCount = 0;
    private int bigCityCount = 0;

    public static void main(String[] args) {
        USMAP usmap = new USMAP();
        usmap.run();
    }
    public void run() {
        readCities();
        readBigCities();
        setupCanvas();
        drawCities();
        drawCities2();
    }
    public void readCities() { // Read city locations from cities.txt
        try {
            File file = new File("cities.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine() && cityCount < MAX_CITIES) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    try {
                        double longitude = Double.parseDouble(parts[0]);
                        double latitude = Double.parseDouble(parts[1]);
                        String cityName = "";
                        for (int i = 2; i < parts.length; i++) {
                            if (i > 2) {
                                cityName += " ";
                            }
                            cityName += parts[i];
                        }
                        xCoordinates[cityCount] = latitude;
                        yCoordinates[cityCount] = longitude;
                        cityNames[cityCount] = cityName;
                        cityCount++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid line: " + line);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cities file not found.");
        }
    }
    public void readBigCities() { // Read city populations from bigCities.txt
        try {
            File file = new File("bigCities.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine() && bigCityCount < MAX_BIG_CITIES) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String cityName = "";
                    for (int i = 0; i < parts.length - 1; i++) {
                        if (i > 0) {
                            cityName += " ";
                        }
                        cityName += parts[i];
                    }              
                    try {
						cityName = cityName.substring(cityName.indexOf(" ")+1);
                        int pop = Integer.parseInt(parts[parts.length - 1]);
                        bigCityNames[bigCityCount] = cityName;
                        population[bigCityCount] = pop;
                        bigCityCount++;
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Error line: " + line);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Big cities file not found.");
        }
    }
    public void setupCanvas() { // Set up the canvas for drawing
        StdDraw.setTitle("US Map");
        StdDraw.setCanvasSize(900, 512);
        StdDraw.setXscale(128.0, 65.0); // Longitude range
        StdDraw.setYscale(22.0, 52.0);  // Latitude range
    }
    public void drawCities() { // Draw all cities on the canvas
        for (int i = 0; i < cityCount; i++) {
            double x = xCoordinates[i];
            double y = yCoordinates[i];
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.setPenRadius(0.006);
            StdDraw.point(x, y); // Use point to draw small dots
        }
    }
    public void drawCities2() { // Draw big cities with population
        for (int i = 0; i < bigCityCount; i++) {
            String bigCityName = bigCityNames[i];
            int pop = population[i];
            for (int j = 0; j < cityCount; j++) {
                if (cityNames[j].equals(bigCityName)) {
                    double x = xCoordinates[j];
                    double y = yCoordinates[j];
                    double size = 0.6 * (Math.sqrt(pop)/18500.0);
                    if (i < TOP_10_LARGEST_CITIES) {
                        StdDraw.setPenColor(StdDraw.RED);
                    } else {
                        StdDraw.setPenColor(StdDraw.BLUE);
                    }
                    StdDraw.setPenRadius(size);
                    StdDraw.point(x, y); // Use point to draw big cities
                }
            }
        }
    }
}
