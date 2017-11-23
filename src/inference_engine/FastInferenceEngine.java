package inference_engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of inference mechanism which take the first nubmer of next question.
 * @author Ladislav Martinek
 */
public class FastInferenceEngine extends InferenceEngine{

    
    @Override
    public List<Integer> get_next_question(int number, Set<Integer> conclusions) {
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
}
