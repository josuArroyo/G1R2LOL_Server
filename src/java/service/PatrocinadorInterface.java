/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evento;
import entity.Patrocinador;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Egoitz
 */
public interface PatrocinadorInterface {

    public void createPatrocinador(Patrocinador patrocinador) throws CreateException;

    public void deletePatrocinador(Patrocinador patrocinador) throws DeleteException;

    public void modifyPatrocinador(Patrocinador patrocinador) throws UpdateException;

    public List<Patrocinador> viewPatrocinadores() throws ReadException;
    
    public List <Patrocinador> viewPatrocinadorByName(String nombre) throws ReadException;
    
    public List <Patrocinador> viewPatrocinadorByDuration(Date duracion) throws ReadException; 

    public Patrocinador viewPatrocinadorById(Integer id_patrocinador) throws ReadException;
    
    List<Evento> viewEventosByPatrocinador(Integer id_patrocinador) throws ReadException;


}
