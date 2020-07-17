package br.com.sosifod.dao;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.bean.Oficial;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoginDao {
    public Administrador loginAdministrador(Administrador adm) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Administrador a WHERE a.email = :email AND a.senha = :senha");
                select.setParameter("email", adm.getEmail());
                select.setParameter("senha", adm.getSenha());
                adm = (Administrador) select.uniqueResult();
                return adm;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário administrador [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário administrador [DAO]****", e);
        }        
    }    
    
    public Oficial loginOficial(Oficial oficial) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Oficial o WHERE o.email = :email AND o.senha = :senha");
                select.setParameter("email", oficial.getEmail());
                select.setParameter("senha", oficial.getSenha());
                oficial = (Oficial) select.uniqueResult();
                return oficial;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário oficial de justiça [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário oficial de justiça [DAO]****", e);
        }        
    }    
}
