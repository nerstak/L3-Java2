package oo.Questions;

/**
 * Statement interface
 *
 * @param <T> Type of the correct answer (Boolean or String)
 */
public interface Statement<T> {
    /**
     * Display the statement
     */
    void display();

    /**
     * Check the answer
     *
     * @param u Proposed answer
     * @return Boolean
     */
    boolean checkAnswer(T u);

    /**
     * Get the text of the statement
     *
     * @return Text of statement
     */
    String getText();

    /**
     * Get the correct answer
     *
     * @return Correct answer in String
     */
    String getCorrectAnswer();

    /**
     * Get the type of statement
     *
     * @return String of type
     */
    String getInstance();
}
