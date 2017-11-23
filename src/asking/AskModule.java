package asking;

import inference_engine.InferenceEngine;
import inference_engine.SimilarityEngine;
import inference_engine.Uncertainty;
import inference_engine.UserSaveAns;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import knowledge_base.DataSave;

/**
 * Mechanism to ask on question and try to give conclusion based on data base and inference mechanism.
 * @author Ladislav Martinek
 */
public class AskModule {
    InferenceEngine inference_engine = null;
    UserSaveAns users_save = null;
    private Scanner scanner;
    Set<Integer> conclusions;
    
    /**
     * Method to start asking on database and use inference mechanism.
     * @param inference_engine Implementation of inference mechanism.  
     * @param data Implementation of DataSave.
     */
    public void start_ask(InferenceEngine inference_engine, DataSave data, Uncertainty probability, SimilarityEngine similarity) {
        
        scanner = new Scanner(System.in);
        this.inference_engine = inference_engine;
        this.inference_engine.set_dataSet(data);
        users_save = new UserSaveAns(probability, this.inference_engine.get_sum_of_question(), similarity);
        System.out.println(inference_engine.get_question(1));
        String buffer;
        buffer = scanner.next();
        Double answer = Double.parseDouble(buffer);
        if ( answer > 0.5){
            this.users_save.save(1, inference_engine.get_probability(1), answer);
        }else if ( answer < 0.5){
            this.users_save.save(1, 1- inference_engine.get_probability(1), answer);
        }
        conclusions = inference_engine.get_conclusion_for_questions(1, answer);
        asking();
    }
    
    /**
     * Asking while I get all questions to can give conclusion.
     */
    private void asking (){
        List<Integer> questions_to_ask = null;
        int i = 1;
        while (true){
            if (conclusions.size() <=0) break;
            if (questions_to_ask == null || questions_to_ask.size() <=0 ){
                questions_to_ask = inference_engine.get_next_question(i, conclusions);
                if (questions_to_ask.size() <= 0){
                    break;
                }
                Collections.sort(questions_to_ask);
                continue;
            }
            int nextQ = questions_to_ask.get(0);
            if (nextQ == Integer.MAX_VALUE) break;
            System.out.println(inference_engine.get_question(nextQ));
            questions_to_ask.remove(0);
            i = nextQ;
            String buffer;
            buffer = scanner.next();
            Double answer = Double.parseDouble(buffer);

            if ( answer > 0.5){
                this.users_save.save(nextQ, inference_engine.get_probability(nextQ), answer);
            }else if ( answer < 0.5){
                this.users_save.save(nextQ,1- inference_engine.get_probability(nextQ), answer);
            }
            conclusions = inference_engine.intersection(conclusions, inference_engine.get_conclusion_for_questions(nextQ,answer));
        }
        if (conclusions.isEmpty()){
            System.out.println("Neznam reseni.");
        }
        for (Integer conclusion : conclusions) {
            System.out.println(inference_engine.get_conclusion(conclusion));   
            System.out.println("Probability = " + Math.round(users_save.get_probability()*1000.)/1000.);
            System.out.println("Similarity = " + Math.round(users_save.get_similarity()*1000.)/1000.);
        }
    }
    
}
