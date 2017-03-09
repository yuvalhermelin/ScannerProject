/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
    int[] answers;

    public AnswerSheet(int[] answers) {
	this.answers = answers;
    }

    public int[] getAnswers() {
	return answers;
    }

    public int getAnswer(int problemNumber) {
	return answers[problemNumber];
    }

    public int getNumberCorrect(AnswerSheet key) {
	int correct = 0;
	for (int i = 0; i < answers.length; i++)
	    if (answers[i] == key.getAnswer(i))
		correct++;
	return correct;
    }
}
