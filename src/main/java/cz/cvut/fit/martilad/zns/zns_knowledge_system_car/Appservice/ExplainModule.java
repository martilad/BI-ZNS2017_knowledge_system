/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author lamxi
 */
public class ExplainModule {
    private Map<String, Double> ansvered_questions;
    private Map<String, Map<Integer, String>> explanation = null;
    

    public ExplainModule() {
        ansvered_questions = new HashMap<>();
    }
    
    public void new_answer(String question, Double answer) {
        ansvered_questions.put(question, answer);
    }
    
    public String get_eplanation(){
        String return_ex = "Vaše odpovědi nebo věty vyvozené systémem.\n";
        if (explanation == null) {
            System.out.println("prdelka");
            return list_of_questions();
        }else{
            for (Map.Entry<String, Double> entry : ansvered_questions.entrySet()) {
                System.out.println(entry.getKey());
                if (explanation.containsKey(entry.getKey()) && explanation.get(entry.getKey()).containsKey(get_number_of_answer(entry.getValue()))){
                    return_ex = return_ex + explanation.get(entry.getKey()).get(get_number_of_answer(entry.getValue())) + "\n";
                }else{
                    return_ex = return_ex + answert_to_line_explain(entry);
                }
        }
        }
        return return_ex;
    }
    public String list_of_questions(){
        String return_ex = "Vaše odpovědi na otázky položené systémem: \n";
        for (Map.Entry<String, Double> entry : ansvered_questions.entrySet()) {
             return_ex = return_ex + answert_to_line_explain(entry);
        }
        return return_ex;
    }
    public String answert_to_line_explain(Map.Entry<String,Double> entry){
        return entry.getKey() + "  -> " + get_type_of_answer(entry.getValue()) + "\n";
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
    public Integer get_number_of_answer(Double answer){
        if (answer >= 0 && answer <= 0.2 )
            return 5;
        if (answer > 0.2 && answer <= 0.4 )
            return 4;
        if (answer > 0.4 && answer < 0.6 )
            return 3;
        if (answer >= 0.6 && answer < 0.8 )
            return 2;
        if (answer >= 0.8 && answer <= 1 )
            return 1;
        return 0;
    }
    
    public void load_explaination(String file_name, Map<Integer, String> map_of_question){
        
        try{
            System.out.println("prdel");
            explanation = new HashMap<>();
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(file_name));
            String read = "";
            while (true){
                read = br.readLine();
                if (read == null){
                    break;
                }
                read = read.replaceAll("^\\s+","");
                if (!read.isEmpty()){
                    int question_number = new Scanner(read).useDelimiter("\\D+").nextInt();
                    String tmp = read.substring(read.indexOf(" ") + 1);
                    int answer_number = new Scanner(tmp).useDelimiter("\\D+").nextInt();
                    String explain_text = tmp.substring(tmp.indexOf(" ") + 1);
                    
                    if (explanation.containsKey(map_of_question.get(question_number))){
                        explanation.get(map_of_question.get(question_number)).put(answer_number, explain_text);
                        continue;
                    }else{
                        Map<Integer, String> map = new HashMap<Integer, String>();
                        map.put(answer_number, explain_text);
                        explanation.put(map_of_question.get(question_number), map);
                        continue;
                    }
                }else if(read.isEmpty() ) {
                    continue;
                }
                else{
                    System.out.println("Špatný formát souboru vysvětlování.");
                    return;
                }
            }
         
        } catch (FileNotFoundException ex) {
            System.out.println("Neexistující soubor vysvětlování.");
            return;
        }catch (IOException ex){
            System.out.println("IO except");
            return;
        }
    }
}