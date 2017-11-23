package knowledge_base;

import java.util.Set;

/**
 * Abstract class for represent data file for knowledge system.
 * @author Ladislav Martinek
 */
public abstract class DataSave {
    
    
    public abstract int get_sum_question();
    public abstract int get_sum_conclusion();
    
    /**
     * Next possible question for that conclusion.
     * @param start
     * @param conclusion
     * @return 
     */
    public abstract int next_fill_column_value(int start, int conclusion);
    
    /**
     * Load data file from text file.
     * @param file_name 
     */
    public abstract void load_data(String file_name);
    
    /**
     * Get String reprezentation form number reprezent.
     * @param question
     * @return 
     */
    public abstract String get_question(int question);
    
    /**
     * Get probability coclusion if question is asked.
     * @param question
     * @return 
     */
    public abstract Double get_probability_of_question(int question);
    
    
    /**
     * Get String reprezentation from number of conclusion.
     * @param conclusion_line_number
     * @return 
     */
    public abstract String get_conclusion_from_line_number(int conclusion_line_number);
    
    /**
     * Convert data to string vizualization.
     * @return 
     */
    public String data_to_string_vizualization(){
        return "Not Implemented";
    }
    
    /**
     * Convert data to string.
     * @return 
     */
    public String data_to_string(){
        return "Not Implemented";
    }
    
    /**
     * Write database to file.
     * @param file_name 
     */
    public void print_to_file(String file_name){
        System.err.println("Data not write, dataset not implement print");
    }
    
    /**
     * Write database vizualization to file.
     * @param file_name 
     */
    public void print_to_file_vizualization(String file_name){
        System.err.println("Data not write, dataset not implement print");
    }
    
    /**
     * Get all posible coclusion on this answered question.
     * @param questions Number of question.
     * @param answer 1 for yes or 0 for no.
     * @return List of possible conclusion.
     */
    public abstract Set<Integer> get_conclusions_for_questions(int questions, int answer);
}
