/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import entity.UserType;
import exception.ReadException;
import java.util.List;

/**
 *
 * @author Eneko.
 */
public interface UserInterface {

    public List<User> findUserByEmailAndPasswd(String email, String passwd) throws ReadException;

    public List<User> findForUserType(UserType userType) throws ReadException;

    public List<User> viewAllUsers() throws ReadException;

}
