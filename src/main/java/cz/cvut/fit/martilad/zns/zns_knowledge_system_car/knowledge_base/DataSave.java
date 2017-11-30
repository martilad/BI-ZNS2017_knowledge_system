/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.knowledge_base;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.exceptions.ErrorException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lamxi
 */
public class DataSave {
    ///Map<Question, Map<Conclusion,Value>>
    Map<String, Map<String, Double>> data = null;
    List<String> conclusion = null;
    List<String> questions = null;
    Map<Integer, String> map_questions = null;
    Map<Integer, String> map_conclusions = null;
    Boolean data_read_succes = false;
    
    public Map<String,Double> get_value_on_question_to_each_conclusion(String question){
        if (data == null)return null;
        if (data.containsKey(question)){
            return data.get(question);
        }
        return null;
    }
    
    public Boolean is_read_succes (){
        return data_read_succes;
    }
    
    
    public void load_questions (BufferedReader br){
        
    }
    public void load_conclusions (BufferedReader br){
        
    }
    public void load_fuzzy (BufferedReader br){
        
    }
    
    public void load_data(String file_name) throws ErrorException{
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
                    load_questions(br);
                    break;
                }else if(!read.isEmpty() && read.charAt(0)=='C'){
                    load_conclusions(br);
                    break;
                }else if(read.isEmpty() ) {
                    continue;
                }
                else{
                    throw new ErrorException("Nesprávný formát textového souboru nejdříve je nutné uvést výčet otázek a možných výsledků");
                }
            }
         
        } catch (FileNotFoundException ex) {
            System.err.println("Chyba při čtení souboru nenasel");
        }catch (IOException ex){
            System.err.println("Chyba při čtení souboru");
        }
    }

    public List<String> getConclusion() {
        return conclusion;
    }

    public List<String> getQuestions() {
        return questions;
    }

   
       
}
