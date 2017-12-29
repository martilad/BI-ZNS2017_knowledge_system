/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.exceptions.ErrorException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lamxi
 */
public class ExplainModule {
    private Map<String, Double> ansvered_questions;
    private Map<String, Map<String, String>> explanation = null;
    private boolean explanation_ex = false;
    

    public ExplainModule() {
        ansvered_questions = new HashMap<>();
    }
    
    public void new_answer(String question, Double answer) {
        ansvered_questions.put(question, answer);
    }
    
    public String get_eplanation(){
        String return_ex = "";
        if (!explanation_ex) {
            return list_of_questions();
        }else{
            return "fake";
        }
    }
    public String list_of_questions(){
        String return_ex = "Vaše odpovědi na otázky položené systémem: \n";
        for (Map.Entry<String, Double> entry : ansvered_questions.entrySet()) {
             return_ex = return_ex + entry.getKey() + "  -> " + get_type_of_answer(entry.getValue()) + "\n";
        }
        return return_ex;
    }
    public String get_type_of_answer(Double answer){
        if (answer >= 0 && answer <= 0.2 )
            return "Ne";
        if (answer > 0.2 && answer <= 0.4 )
            return "Asi ne";
        if (answer > 0.4 && answer < 0.6 )
            return "Nevím";
        if (answer >= 0.6 && answer < 0.8 )
            return "Asi ano";
        if (answer >= 0.8 && answer <= 1 )
            return "Ano";
        return "ANO";
    }
    
    public void load_explaination(String file_name){
        try{
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(file_name));
            String read = "";
            while (true){
                read = br.readLine();
                if (read == null){
                    break;
                }
                read = read.replaceAll("^\\s+","");
                if (!read.isEmpty() && read.charAt(0)=='Q'){
                    
                    break;
                }else if(!read.isEmpty() && read.charAt(0)=='C'){
                   
                    break;
                }else if(read.isEmpty() ) {
                    continue;
                }
                else{
                    explanation_ex = false;
                    System.out.println("Špatný formát souboru vysvětlování.");
                    return;
                }
            }
         
        } catch (FileNotFoundException ex) {
            explanation_ex = false;
            System.out.println("Neexistující soubor vysvětlování.");
            return;
        }catch (IOException ex){
            explanation_ex = false;
            System.out.println("IO except");
            return;
        }
        explanation_ex = true;
    }
    
    
    
}
