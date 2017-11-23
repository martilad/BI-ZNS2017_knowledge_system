package knowledge_base;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data base implementation by 2D matrix of yes/no answered questions.
 * @author lamxi
 */
public class DataSave2DMatrix extends DataSave{
    Map<Integer,QuestionWithProbability> question_map;
    Map<Integer,String> conclusion_map;
    Map<Integer, Integer> row_con_number_to_conclusion;
    int[][] read_data;
    
    
    @Override
    public int next_fill_column_value(int start, int conclusion){
        for (int i = start; i<read_data[0].length;i++){
            if (read_data[conclusion][i]!=-1){
                return i+1;
            }
        }
        return Integer.MAX_VALUE;
    }
    
    @Override
    public Set<Integer> get_conclusions_for_questions(int questions, int answer) {
        if (answer != 1 && answer != 0){
            System.err.println("Chyba nebylo možné dostat výsledky");
        }
        Set<Integer> list_conclusion = new TreeSet<>();
        for (int i = 0;i < read_data.length ; i++)
            if (answer == read_data[i][questions-1])
                list_conclusion.add(i);
        return list_conclusion;
    }
    
    @Override
    public String get_question(int question) {
        return question_map.get(question).getQuestion();
    }
    
    @Override
    public Double get_probability_of_question(int question) {
        return question_map.get(question).getProbability();
    }
    
    @Override
    public String get_conclusion_from_line_number(int conclusion_line_number){
        return conclusion_map.get(row_con_number_to_conclusion.get(conclusion_line_number));
    }

    /**
     * Print data vizualization to terminal.
     */
    public void print(){
        System.out.println(data_to_string());
    }
    
    @Override
    public void print_to_file_vizualization(String file_name){
        try {
            Writer writer = new PrintWriter(file_name, "UTF-8");
            writer.append(data_to_string_vizualization());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DataSave2DMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void print_to_file(String file_name){
        
        try (Writer writer = new PrintWriter(file_name, "UTF-8")) {
                writer.append(data_to_string());
        }
        catch (IOException ex) {
            Logger.getLogger(DataSave2DMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public String data_to_string_vizualization(){
        StringBuilder build = new StringBuilder();
        build.append("     │");
        for (int j = 0; j < question_map.size(); j++) {
            build.append(String.format("%3d", j+1)).append("│");
        }
        build.append("\n");
        build.append("     ┌");
        for (int j = 0; j < question_map.size()-1; j++) {
            build.append("---┬");
        }
        build.append("---┐\n");
         for (int i = 0; i < read_data.length; i++) {
            build.append(String.format("%2d", i)).append("-").append(String.format("%2d", row_con_number_to_conclusion.get(i))).append("│");
            for (int j = 0; j < question_map.size(); j++) {
                if (read_data[i][j] == -1){
                    build.append("   ").append("│");
                }else{
                    build.append(String.format("%3d", read_data[i][j])).append("│");
                }
                
            }
           build.append("\n");
           build.append("-----├");
            for (int j = 0; j < question_map.size()-1; j++) {
                 build.append("---┼");
            }
            build.append("---┤\n");
        }
        return build.toString();
    }
    
    @Override
    public String data_to_string(){
        StringBuilder build = new StringBuilder();
        build.append("Q:\n");
        for(Map.Entry<Integer, QuestionWithProbability> entry : question_map.entrySet()) {
            build.append(entry.getKey()).append(". ").append(entry.getValue().getQuestion()).append("[").append(entry.getValue().getProbability()).append("]\n");
        }
        build.append("C:\n");
        for(Map.Entry<Integer, String> entry : conclusion_map.entrySet()) {
            build.append(entry.getKey()).append(". ").append(entry.getValue()).append("\n");
            }
        build.append("R:\n");
       
        for (int i = 0 ; i < read_data.length ; i++){
            build.append(row_con_number_to_conclusion.get(i)).append(" > ");
            for (int j = 0 ; j < read_data[0].length ; j++){
                switch (read_data[i][j]) {
                    case 1:
                        build.append(" ").append(j+1).append(",");
                        break;
                    case 0:
                        build.append(" -").append(j+1).append(",");
                        break;
                    default:
                        break;
                }
            }
            build.append("\n");
        }
         return build.toString();
    }
    
    @Override
    public void load_data(String file_name) {
        ReadDataFile read = new ReadDataFile(file_name);
        List<NumberLineText> list_q = read.get_questions();
        question_map = new HashMap<>();
        list_q.forEach((numberLineText) -> {
            question_map.put(numberLineText.getNumber(), new QuestionWithProbability(numberLineText.getText(), numberLineText.getProbability()) );
        });
        
        List<NumberLineText> list_c = read.get_conclusions();
        conclusion_map = new HashMap<>();
        list_c.forEach((numberLineText) -> {
            conclusion_map.put(numberLineText.getNumber(), numberLineText.getText());
        });
        
        List<List<Integer>> list_r = read.get_relations();
        row_con_number_to_conclusion = new HashMap<>();
        read_data = new int[list_r.size()][question_map.size()];
        for (int i = 0; i < list_r.size(); i++) {
            for (int j = 0; j < question_map.size(); j++) {
                read_data[i][j] = -1;
            }
        }
        
        int number_const = 0;
        for (List<Integer> list : list_r) {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0){
                    row_con_number_to_conclusion.put(number_const, list.get(i));
                }else{
                    if (list.get(i)<0){
                       read_data[number_const][Math.abs(list.get(i))-1] = 0;  
                    }else{
                       read_data[number_const][Math.abs(list.get(i))-1] = 1;
                    }
                }
            }
             number_const++;
        }
    }

    @Override
    public int get_sum_question() {
        return question_map.size();
    }

    @Override
    public int get_sum_conclusion() {
        return conclusion_map.size();
    }

    
}
