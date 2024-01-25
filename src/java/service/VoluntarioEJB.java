/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cipher.HashContra;
import cipher.mailCypher;
import entity.Voluntario;
import exception.UpdateException;

/**
 *
 * @author Egoitz
 */
public class VoluntarioEJB implements VoluntarioInterface{
    
    private AbstractFacade abs;

    @Override
    public void recuperarContra(Voluntario volun) throws UpdateException {
        String nuevaContra = null;
        mailCypher email = new mailCypher();
        
        try{
        nuevaContra = email.sendEmail(volun.getEmail());
        nuevaContra = HashContra.hashContra(nuevaContra.getBytes());
        volun.setEmail(nuevaContra);
        abs.edit(volun);
        }catch (Exception e){
              
        }
    }
    
    
        
    
}
