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
public class MinimumP extends Uncertainty{

    public MinimumP() {
        probability = 1.;
    }

    
    @Override
    public void save_new_asked(Double probality_question) {
        if (probality_question < probability){
            probability = probality_question;
        }
    }
    
}
