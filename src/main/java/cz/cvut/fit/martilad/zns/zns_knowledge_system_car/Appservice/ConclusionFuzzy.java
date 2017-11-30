/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lamxi
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
    
    
    
}
