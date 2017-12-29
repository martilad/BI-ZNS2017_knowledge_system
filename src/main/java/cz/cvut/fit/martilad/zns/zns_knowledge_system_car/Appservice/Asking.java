/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.exceptions.ErrorException;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.knowledge_base.DataSave;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Constants;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.inference_engine.InferenceEngine;
import javafx.util.Pair;

/**
 *
 * @author lamxi
 */
public class Asking {
    ConclusionFuzzy possibly_con;
    InferenceEngine inference_engine;
    Boolean start = true;
    ExplainModule explain;
    
    public Asking() throws ErrorException {
        explain = new ExplainModule();
        explain.load_explaination(Constants.explaine_file);
        DataSave data = new DataSave();
        data.load_data(Constants.data_file);
        possibly_con = new ConclusionFuzzy(data.getConclusion());
        if (!data.is_read_succes()){
            throw new ErrorException("Chyba při čtení datového souboru.");
        }
        inference_engine = new InferenceEngine(data);
    }
    
    
    public String get_next_question(){
        if (start){
            start = false;
            return inference_engine.get_first_question();
        }else{
            return inference_engine.get_next_question(possibly_con);
        }
    }
    
    public ConclusionFuzzy get_posibly_conclusion(){
        return possibly_con;
    }
    
    public void anwer_question(String Question, double answer){
        explain.new_answer(Question, answer);
        possibly_con = inference_engine.answered(Question, answer, possibly_con);
    }
    
    public Boolean is_only_one_conclusion(){
        if (possibly_con.is_last()){
            return true;
        }
        return false;
    }
    
    public Pair<String,String> return_final_conclusion(){
        String last = possibly_con.get_last();
        if (last == null){
            last = "Neodpovídá žádné řešení.";
            return new Pair(last, explain.list_of_questions());
        }
        return new Pair(last, explain.get_eplanation());
    }
    
    
    
}
