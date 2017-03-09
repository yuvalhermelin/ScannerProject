import java.util.ArrayList;

import processing.core.PImage;

public class Main {
    public static final String PDF_PATH = "/omrtest.pdf";
    public static OpticalMarkReader markReader = new OpticalMarkReader();

    public static void main(String[] args) {
	System.out.println("Welcome!  I will now auto-score your pdf!");
	System.out.println("Loading file..." + PDF_PATH);
	ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);

	System.out.println("Scoring all pages...");
	scoreAllPages(images);

	System.out.println("Complete!");

	// Optional: add a saveResults() method to save answers to a csv file
    }

    /***
     * Score all pages in list, using index 0 as the key.
     * 
     * NOTE: YOU MAY CHANGE THE RETURN TYPE SO YOU RETURN SOMETHING IF YOU'D
     * LIKE
     * 
     * @param images
     *            List of images corresponding to each page of original pdf
     */
    private static void scoreAllPages(ArrayList<PImage> images) {
	ArrayList<AnswerSheet> scoredSheets = new ArrayList<AnswerSheet>();
	CSVData csvData = new CSVData(images.size(), 4);
	CSVData csvAnalysis = new CSVData(100, 2);

	// Score the first page as the key
	AnswerSheet key = markReader.processPageImage(images.get(0));
	csvData.setIndividualValue(0, 0, 100);
	csvData.setIndividualValue(0, 1, 0);
	csvData.setIndividualValue(0, 2, 100);
	csvData.setIndividualValue(0, 3, 0);

	double[] problems = new double[100];

	for (int i = 1; i < images.size(); i++) {
	    PImage image = images.get(i);
	    AnswerSheet answers = markReader.processPageImage(image);
	    int numberCorrect = 0;
	    for (int j = 0; j < answers.getAnswers().length; j++)
		if (answers.getAnswers()[j] == key.getAnswer(j))
		    numberCorrect++;
		else
		    problems[j]++;
	    csvData.setIndividualValue(i, 0, numberCorrect);
	    csvData.setIndividualValue(i, 1, 100 - numberCorrect);
	    csvData.setIndividualValue(i, 2, numberCorrect);
	    csvData.setIndividualValue(i, 3, 100 - numberCorrect);
	}

	for (int i = 0; i < problems.length; i++) {
	    csvAnalysis.setIndividualValue(i, 0, problems[i]);
	    csvAnalysis.setIndividualValue(i, 0, problems[i] / 100);
	}
	
	csvData.saveCurrentState("Data");
	csvAnalysis.saveCurrentState("Analysis");
    }
}