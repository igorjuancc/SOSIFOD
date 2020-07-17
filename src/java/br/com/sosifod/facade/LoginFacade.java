package br.com.sosifod.facade;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.bean.Oficial;
import br.com.sosifod.dao.LoginDao;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.Seguranca;

public class LoginFacade {
    private static final LoginDao loginDao = new LoginDao();
    
    public static Administrador loginAdministrador(Administrador adm) throws DaoException {
        try {
            if (!Seguranca.isEmail(adm.getEmail()) || adm.getSenha() == null) {
                return null;                
            } else {
                return loginDao.loginAdministrador(adm);                
            }            
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao efetuar login de administrador [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }        
    }
    
    public static Oficial loginOficial(Oficial oficial) throws DaoException {
        try {
            if (!Seguranca.isEmail(oficial.getEmail()) || oficial.getSenha() == null) {
                return null;                
            } else {
                return loginDao.loginOficial(oficial);                
            }            
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao efetuar login de oficial de justiça [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }              
    }
}
