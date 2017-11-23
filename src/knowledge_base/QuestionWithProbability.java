/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knowledge_base;

/**
 *
 * @author lamxi
 */
public final class QuestionWithProbability {
    private final String question;
    private final Double probability;

    public QuestionWithProbability(String question, Double probability) {
        this.question = question;
        this.probability = probability;
    }

    public String getQuestion() {
        return question;
    }

    public Double getProbability() {
        return probability;
    }
    
}
