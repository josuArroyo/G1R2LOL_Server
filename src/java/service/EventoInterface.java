/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evento;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;

/**
 *
 * @author 2dam
 */
public interface EventoInterface {
    
public Evento filterEventById(Integer id_evento) throws ReadException;
public void deleteEvent(Evento event) throws DeleteException;
public List<Evento> viewEvents() throws ReadException;
public void createEvent(Evento event) throws CreateException;
public void modifyEvent(Evento event) throws UpdateException;
public List<Evento> findEventByEventId(Integer id_evento) throws ReadException;
public List<Evento> viewEventoAforoMax(Integer aforo) throws ReadException;
    
    
}
