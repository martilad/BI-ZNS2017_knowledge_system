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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author lamxi
 */
public class DataSave {
    ///Map<Question, Map<Conclusion,Value>>
    Map<String, Map<String, Double>> data = null;
    Map<String, Map<String, Double>> data_reve = null;
    List<String> conclusion = null;
    List<String> questions = null;
    Map<Integer, String> map_questions = null;
    Map<Integer, String> map_conclusions = null;
    Boolean data_read_succes = false;
    
    public String get_first_question(){
        return map_questions.get(1);
    }
    
    public Map<String,Double> get_conclusion_with_value_on_question(String question){
        return data_reve.get(question);
    }
    
    
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
    
    
    public void load_questions (BufferedReader br) throws IOException, ErrorException{
        String read;
        map_questions = new HashMap<>();
        questions = new ArrayList<>();
        while (true){
                read = br.readLine();
                if (read == null){
                    return;
                }
                read = read.replaceAll("^\\s+","");
                if (read.equals("")){
                    continue;
                }
                if (read.charAt(0) == 'C' ){
                    load_conclusions(br);
                    break;
                }
                if (read.charAt(0) == 'F' && conclusion != null ){
                    load_fuzzy(br);
                    break;
                }
                int res = new Scanner(read).useDelimiter("\\D+").nextInt();
                String text = read.substring(read.indexOf(" ") + 1);
                questions.add(text);
                map_questions.put(res, text);
            }



    }
    public void load_conclusions (BufferedReader br) throws IOException, ErrorException{
        String read;
        map_conclusions = new HashMap<>();
        conclusion = new ArrayList<>();
        while (true){
                read = br.readLine();
                if (read == null){
                    return;
                }
                read = read.replaceAll("^\\s+","");
                if (read.equals("")){
                    continue;
                }
                if (read.charAt(0) == 'Q' ){
                    load_questions(br);
                    break;
                }
                if (read.charAt(0) == 'F' && questions != null ){
                    load_fuzzy(br);
                    break;
                }
                int res = new Scanner(read).useDelimiter("\\D+").nextInt();
                String text = read.substring(read.indexOf(" ") + 1);
                conclusion.add(text);
                map_conclusions.put(res, text);
            }
    }
    public void load_fuzzy (BufferedReader br) throws IOException, ErrorException{
        String read;
        List<Integer> question_row = new ArrayList<>();
        data = new HashMap<>();
            while (true){
                read = br.readLine();
                if (read == null){
                    break;
                }
                read = read.replaceAll("^\\s+","");
                if (read.equals("")){
                    continue;
                }
                if (read.charAt(0) == 'Q' || read.charAt(0) == 'C' ){
                    throw new ErrorException("Chyba v datech");
                }
                if (read.charAt(0) == '0'){
                    String text = read.substring(read.indexOf(";") + 1);
                    while(true){
                        int res = new Scanner(text).useDelimiter("\\D+").nextInt();
                        question_row.add(res);
                        int index = text.indexOf(";");
                        if (index == -1){
                            break;
                        } 
                        text = text.substring(index + 1);
                    }
                    continue;
                }
                int res = new Scanner(read).useDelimiter("\\D+").nextInt();
                String text = read.substring(read.indexOf(";") + 1);
                Integer counter = 0;
                Map<String, Double> insert_map = new HashMap<>();
                data.put(map_conclusions.get(res), insert_map);
                while (true){
                    int index = text.indexOf(";");
                    if (index == -1){
                          Double hodnota = Double.parseDouble(text);
                          if (question_row.size() < counter-1){
                              throw new ErrorException("Chyba v datech");
                          }
                          data.get(map_conclusions.get(res)).put(map_questions.get(question_row.get(counter)), hodnota);
                          counter++;
                          break;
                    }else{
                        String hodnota_text = text.substring(0, index);
                        Double hodnota = Double.parseDouble(hodnota_text);
                        if (question_row.size() < counter-1){
                            throw new ErrorException("Chyba v datech");
                        }
                        data.get(map_conclusions.get(res)).put(map_questions.get(question_row.get(counter)), hodnota);
                        counter++;
                        text = text.substring(index+1);
                    }
                } 
            }
            data_reve = new HashMap<>();
            for (String question : questions) {
                Map<String,Double> conclusions_in_map = new HashMap<>();
                
                for (String conclusionS : conclusion) {
                    conclusions_in_map.put(conclusionS, data.get(conclusionS).get(question));
                }
                data_reve.put(question, conclusions_in_map);
        }
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
            throw new ErrorException("Chyba při čtení souboru nenasel");
        }catch (IOException ex){
            throw new ErrorException("Chyba při čtení souboru");
        }
        data_read_succes = true;
        
    }

    public Map<String, Map<String, Double>> getData() {
        return data;
    }

    public List<String> getConclusion() {
        return conclusion;
    }

    public List<String> getQuestions() {
        return questions;
    }

   
       
}
