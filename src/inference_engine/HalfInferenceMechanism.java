/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inference_engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lamxi
 */
public class HalfInferenceMechanism extends InferenceEngine{

    @Override
    public List<Integer> get_next_question(int number, Set<Integer> conclusions) {
        /////JE NUTN0 VRACET V9CE OT8ZEK NEJLEPE NEJBLIZSI V KAZDYM RADKU ABY SE M2L NA CO PT8T A VYHODNOCOVAT
        //999
        List<Integer> set_of_questions = new LinkedList<>();
        int min = Integer.MAX_VALUE;
        for (Integer conclusion : conclusions) {
            min = min(min, data.next_fill_column_value(number, conclusion));
        }
        set_of_questions.add(min);
        return set_of_questions;
    }
    
    /**
     * Return minimum of two values.
     * @param first
     * @param second
     * @return 
     */
    private int min(int first, int second){
        if (first > second) return second;
        return first;
    }    
    
    public Set<Integer> get_conclusion_for_questions(int questions, double answer) {
        if (answer > 0.5){
            return data.get_conclusions_for_questions(questions, 1);
        }else if (answer < 0.5){
            return data.get_conclusions_for_questions(questions, 0);
        }else {
            Set<Integer> con = data.get_conclusions_for_questions(questions, 1);
            con.addAll(data.get_conclusions_for_questions(questions, 0));
            return con;
        }
    }
    
}
