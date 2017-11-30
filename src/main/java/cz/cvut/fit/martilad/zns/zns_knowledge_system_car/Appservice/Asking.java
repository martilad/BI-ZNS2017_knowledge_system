/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.exceptions.ErrorException;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.knowledge_base.DataSave;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Constants;

/**
 *
 * @author lamxi
 */
public class Asking {
    ConclusionFuzzy possibly_con;
    DataSave data = new DataSave();
    
    
    public Asking() throws ErrorException {
        data.load_data(Constants.data_file);
        possibly_con = new ConclusionFuzzy(data.getConclusion());
    }
    
    
}
