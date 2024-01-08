/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import javax.persistence.PersistenceException;

/**
 *
 * @author Eneko.
 */
public class ReadException extends Exception {

    public ReadException(String msg) {
        super(msg);
    }

}
