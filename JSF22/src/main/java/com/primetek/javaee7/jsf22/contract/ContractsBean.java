/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primetek.javaee7.jsf22.contract;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author mertcaliskan
 */
@Named(value = "contractsBean")
@SessionScoped
public class ContractsBean implements Serializable {
    
    String contract = "red";

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }
}