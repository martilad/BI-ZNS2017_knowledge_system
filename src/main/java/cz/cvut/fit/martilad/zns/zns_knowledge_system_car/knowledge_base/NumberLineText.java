/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.knowledge_base;

/**
 *
 * @author lamxi
 */
public class NumberLineText {
    private Integer number;
    private String text;
    
    public NumberLineText(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    
}
