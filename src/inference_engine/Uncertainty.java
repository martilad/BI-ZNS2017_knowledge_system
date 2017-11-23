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
public abstract class Uncertainty {
    
    protected Double probability;

       
    
    public abstract void save_new_asked(Double probality_question);

    public Double getProbability() {
        return probability;
    }
    
    
    
    
    
    
    
}
