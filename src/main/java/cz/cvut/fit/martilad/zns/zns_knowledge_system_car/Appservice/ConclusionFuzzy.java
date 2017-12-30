package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for resolvng fuzzy set in answering questions.
 * @author Ladislav Martínek
 * @since 30. 11. 2017
 */
public class ConclusionFuzzy {
    Map<String, Double> conlusion_and_meter_of_is;

    public ConclusionFuzzy(List<String> conclusion) {
        if (conclusion == null){return;}
        conlusion_and_meter_of_is = new HashMap<>();
        for (String string : conclusion) {
            conlusion_and_meter_of_is.put(string, 1.0);
        }
    }

    public Map<String, Double> getConlusion_and_meter_of_is() {
        return conlusion_and_meter_of_is;
    }
    
    public Double euclidean_metric (Map<String, Double> possible){
        Double sum = 0.0;     
        for (Map.Entry<String, Double> entry : conlusion_and_meter_of_is.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue() - possible.get(entry.getKey());
            value = value * value;
            sum = sum + value;
        }
        return Math.sqrt(sum);
    }
    
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}
    
    public void answer_new (Map<String, Double> possible,Double answer){
        
        Map<String, Double> new_con = new HashMap<>(conlusion_and_meter_of_is);
        if (!(answer > 0.4 && answer < 0.6)){
            for (Map.Entry<String, Double> entry : new_con.entrySet()) {
                Double value = possible.get(entry.getKey());
                Double to_avg = answer;
                Double avg = 0.0;
                if (answer >= 0.5 && value >= 0.5){
                    avg = (value+to_avg)/2;
                }else if (answer <= 0.5 && value >= 0.5){
                    value = 1-value;
                    avg = (value+to_avg)/2;
                }else if (answer >= 0.5 && value <= 0.5){
                    to_avg = 1-to_avg;
                    avg = (value+to_avg)/2;
                }else if (answer <= 0.5 && value <= 0.5){
                    value = 1-value;
                    to_avg = 1-to_avg;
                    avg = (value+to_avg)/2;
                }
                avg = round(avg, 2);
                if (entry.getValue() > avg){
                    conlusion_and_meter_of_is.put(entry.getKey(),avg); 
                }
            }
        }
        
        new_con = new HashMap<>(conlusion_and_meter_of_is);
        for (Map.Entry<String, Double> entry : new_con.entrySet()) {
            if (entry.getValue() < 0.1){
                conlusion_and_meter_of_is.remove(entry.getKey());
            }
        }
        
        
    }
    public Boolean is_last (){
        if (conlusion_and_meter_of_is.size()<=1){
            return true;
        }
        return false;
    }
    
    public String get_last(){
        for (Map.Entry<String, Double> entry : conlusion_and_meter_of_is.entrySet()) {
            return entry.getKey();
        }
        return null;
    }
}
