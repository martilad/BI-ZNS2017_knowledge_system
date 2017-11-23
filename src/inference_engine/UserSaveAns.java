/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inference_engine;

/**
 *
 * @author Ladislav Martinek
 */
public class UserSaveAns {

    Double[] users_answers;
    Double[] probabilities;
    Uncertainty probabilityComp;
    SimilarityEngine similarity;

    public UserSaveAns(Uncertainty probabilityComp, Integer questions_number, SimilarityEngine similarity) {
        this.probabilityComp = probabilityComp;
        this.similarity = similarity;
        users_answers = new Double[questions_number+1];
        for (int i = 0; i < users_answers.length; i++) {
            users_answers[i] = 0.;
        }
        probabilities = new Double[questions_number+1];
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = 0.;            
        }
    }

    public void save(int question, Double probability, Double user_answer) {
        this.probabilityComp.save_new_asked(probability);
        users_answers[question] = user_answer;
        probabilities[question] = probability;
    }

    public Double get_similarity() {
        return similarity.get_similarity(users_answers, probabilities);
    }

    public Double get_probability() {
        return probabilityComp.getProbability();
    }

}
