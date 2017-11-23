package inference_engine;

import java.util.List;
import java.util.Set;
import knowledge_base.DataSave;

/**
 * Class whith can be implemented for users inference mechanism.
 * @author Ladislav Martinek
 */
public abstract class InferenceEngine {
    DataSave data = null;
    
    public int get_sum_of_question(){
        return data.get_sum_question();
    }
    
    public int get_sum_of_conclusion(){
        return data.get_sum_conclusion();
    }
    
    /**
     * Get intersection of two Set. The fastest implementation is in TreeSet O(logn).
     * @param first
     * @param second
     * @return 
     */
    public Set<Integer> intersection (Set<Integer> first, Set<Integer> second){
        first.retainAll(second);
        return first;
    }
    
    /**
     * Get String reprezentation of question.
     * @param question
     * @return 
     */
    public String get_question (int question){
        return data.get_question(question);
    }
    
    /**
     * Get probability of conclusion if question is asked.
     * @param question
     * @return 
     */
    public Double get_probability (int question){
        return data.get_probability_of_question(question);
    }
    
    /**
     * Get String reprezentation of conclusion.
     * @param conclusion
     * @return 
     */
    public String get_conclusion (int conclusion){
        return data.get_conclusion_from_line_number(conclusion);
    }
    
    /**
     * Return posible conclusion on questions.
     * @param questions
     * @param answer
     * @return 
     */
    public Set<Integer> get_conclusion_for_questions(int questions, double answer) {
        if (answer >= 0.5){
            return data.get_conclusions_for_questions(questions, 1);
        }else{
            return data.get_conclusions_for_questions(questions, 0);
        }
    }
    
    /**
     * Check what user answer and convert to number.
     * @param answer
     * @return 
     */
    /*private int return_answer(String answer){
        String answer_change = answer.toLowerCase().replaceAll("\\s+","");
        if ("ano".equals(answer_change) || "yes".equals(answer_change)){
            return 1;
        }
        return 0;
        
    }*/
    
    /**
     * Return next question to give to user.
     * @param number
     * @param conclusions
     * @return 
     */
    public abstract List<Integer> get_next_question(int number, Set<Integer> conclusions);
    
    
    /**
     * Set DAta Base to inference mechanism.
     * @param data 
     */
    public void set_dataSet(DataSave data){
        this.data = data;
    }
       
}
