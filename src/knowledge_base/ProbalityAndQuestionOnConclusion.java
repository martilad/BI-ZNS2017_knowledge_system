/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knowledge_base;

/**
 * Class to hold Q->C(w) w is probability of question to -> conclusion.
 * @author Ladislav Martinek
 */
public final class ProbalityAndQuestionOnConclusion {
    private final int question;
    private final Double probability;

    public ProbalityAndQuestionOnConclusion(int question, Double probability) {
        this.question = question;
        this.probability = probability;
    }

    public int getQuestion() {
        return question;
    }

    public Double getProbability() {
        return probability;
    }
    
    
}
