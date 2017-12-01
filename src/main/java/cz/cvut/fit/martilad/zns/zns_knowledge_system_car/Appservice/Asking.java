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

/**
 *
 * @author lamxi
 */
public class Asking {
    ConclusionFuzzy possibly_con;
    InferenceEngine inference_engine;
    Boolean start = true;
    
    public Asking() throws ErrorException {
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
        possibly_con = inference_engine.answered(Question, answer, possibly_con);
    }
    
    public Boolean is_only_one_conclusion(){
        if (possibly_con.is_last()){
            return true;
        }
        return false;
    }
    
    public String return_final_conclusion(){
        String last = possibly_con.get_last();
        if (last == null){
            return "Neodpovídá žádné řešení.";
        }
        return last;
    }
    
    
    
}
