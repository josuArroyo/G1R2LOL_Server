/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Voluntario;
import exception.CreateException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;

/**
 *
 * @author 2dam
 */
public interface VoluntarioInterface {
    
    public void recuperarContra(Voluntario volun) throws UpdateException;
    
    public void cambiarContra(Voluntario volun) throws UpdateException;
    
    public void createVoluntario(Voluntario volun) throws CreateException;
    
    public List<Voluntario> viewAllVoluntarios() throws ReadException;
    
    public Voluntario filtrarVoluntarioPorID(Integer id) throws ReadException;
    
    
    
    
}
