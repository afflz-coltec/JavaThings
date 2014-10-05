/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexercise.business;

/**
 *
 * @author strudel
 */
public class WorkPlace {
    
    private int ID;
    private String enterpriseName;
    private String enterpriseAddr;

    public int getID() {
        return ID;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseAddr() {
        return enterpriseAddr;
    }

    public void setEnterpriseAddr(String enterpriseAddr) {
        this.enterpriseAddr = enterpriseAddr;
    }
    
}
