package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.inference_engine;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice.ConclusionFuzzy;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.knowledge_base.DataSave;
import java.util.List;

/**
 * Inference engine for finding nex question, keep data and get answers
 * @author Ladislav Martínek
 * @since 30. 11. 2017
 */
public class InferenceEngine {
    DataSave data;
    List<String> questions;
    
    public InferenceEngine(DataSave data) {
        this.data = data;
        questions = data.getQuestions();
    }
    
    public String get_first_question(){
        questions.remove(data.get_first_question());
        return data.get_first_question();
    }
    
    public String get_next_question(ConclusionFuzzy possibly_con){
        Double minimum = Double.MAX_VALUE;
        String quest = "";
        for (String question : questions) {
            Double value = possibly_con.euclidean_metric(data.get_conclusion_with_value_on_question(question));
            if (value < minimum){
                minimum = value;
                quest = question;
            }
        }
        questions.remove(quest);
        return quest;
    }
    
    public ConclusionFuzzy answered (String question, Double value, ConclusionFuzzy con){
        con.answer_new(data.get_conclusion_with_value_on_question(question), value);
        return con;
    }
}
