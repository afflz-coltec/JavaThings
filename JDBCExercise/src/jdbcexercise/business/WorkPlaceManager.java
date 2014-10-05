/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexercise.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdbcexercise.datatile.WorkPlaceDAO;
import jdbcexercise.exceptions.FailedToQueryException;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class WorkPlaceManager implements Runnable {

    private ArrayList<WorkPlace> workplaceList;

    public WorkPlaceManager() {
        this.workplaceList = new ArrayList<>();
    }

    public void addNewWorkPlace(WorkPlace wp) throws FailedToQueryException {

        try {
            WorkPlaceDAO.addWorkPlace(wp);
        } catch (SQLException ex) {
            throw new FailedToQueryException();
        }

        synchronized (this) {
            notify();
        }

    }

    public void removeWorkPlace(WorkPlace wp) throws FailedToQueryException {

        try {
            WorkPlaceDAO.removeWorkPlace(wp);
        } catch (SQLException ex) {
            throw new FailedToQueryException();
        }

    }

    public ArrayList getEnterpriseCharges() throws FailedToQueryException {
        try {
            return WorkPlaceDAO.getCharges();
        } catch (SQLException ex) {
            throw new FailedToQueryException();
        }
    }

    public ArrayList getUpdatedList() {
        return this.workplaceList;
    }

    @Override
    public void run() {

        while (true) {

            try {
                workplaceList = WorkPlaceDAO.getWorkPlaces();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Connection to DB Server got down. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
            }

            synchronized (this) {
                notify();
            }

            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ContactManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
