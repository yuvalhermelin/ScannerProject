import processing.core.PImage;

/***
 * Class to perform image processing for optical mark reading
 * 
 */
public class OpticalMarkReader {

    private static final int HEIGHT_OF_FIRST_ROW = 461;
    private static final int HEIGHT_OF_EACH_PROBLEM = 42;
    private static final int MIDDLE_OF_IMAGE = 600;

    /***
     * Method to do optical mark reading on page image. Return an AnswerSheet
     * object representing the page answers.
     * 
     * @param image
     * @return
     */
    public AnswerSheet processPageImage(PImage image) {
	image.filter(PImage.GRAY);
	int xi = 129;
	int yi = 463;
	int xf = 313;
	int yf = 489;
	int[] problems = new int[100];
	for (int i = 0; i < 100; i++) {
	    if (i % 25 == 0 && i != 0) {
		xi += 283;
		xf += 283;
		yi = 463;
		yf = 489;
	    }

	    problems[i] = determineBubble(xi, yi, xf, yf, 5, image);

	    yi += 40;
	    yf += 40;
	}
	return new AnswerSheet(problems);
    }

    public static int getPixelAt(int row, int col, PImage image) {
	image.loadPixels();
	int color = image.pixels[(row * image.width) + col];
	return color & 255;
    }

    public int determineBubble(int r1, int c1, int r2, int c2, int numBubbles, PImage image) {
	int width = c2 - c1;
	width /= numBubbles;
	int currentProblemID = 1;
	int largestID = 1;
	int mostBlack = Integer.MAX_VALUE;
	int currentBlack = Integer.MAX_VALUE;

	for (int i = 0; i < numBubbles; i++) {
	    for (int row = r1; row < r2; row++)
		for (int col = c1; col < c1 + (width * currentProblemID); col++) {
		    currentBlack -= getPixelAt(row, col, image);
		    // System.out.println(getPixelAt(row, col, image));
		}
	    if (currentBlack > mostBlack) {
		largestID = currentProblemID;
		mostBlack = currentBlack;
	    }
	    currentBlack = Integer.MAX_VALUE;
	    currentProblemID++;
	}

	return largestID;
    }

}
