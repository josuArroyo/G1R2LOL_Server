/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Sede;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;

/**
 *
 * @author Eneko.
 */
public interface SedeInterface {

    public List<Sede> viewSedes() throws ReadException;

    public void deleteSede(Integer id_sede) throws DeleteException;

    public void createSede(Sede sede) throws CreateException;

    public void modifySede(Sede sede) throws UpdateException;

    public List<Sede> viewSedeById(Integer id_sede) throws ReadException;

    public List<Sede> viewSedeByCountry(Integer id_sede) throws ReadException;

    public List<Sede> viewSedeAforoMax(Integer id_sede) throws ReadException;
}
