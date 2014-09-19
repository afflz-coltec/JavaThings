/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduler;

import scheduler.businesstier.Schedule;
import scheduler.datatier.DAO;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Scheduler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Schedule schd = new Schedule("AgendaTeste", "AgendaDaFelicidade");
        schd.store();
        
    }
    
}
