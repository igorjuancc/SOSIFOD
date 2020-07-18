package br.com.sosifod.dao;

import br.com.sosifod.bean.Oficial;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class OficialDao {
    public void cadastrarOficial(Oficial oficial) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(oficial);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo oficial [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo oficial [DAO]****", e);
        }
    }     
    
    public Oficial buscaOficialCpf(String cpf) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Oficial o WHERE o.cpf = :cpf");
                select.setParameter("cpf", cpf);
                Oficial oficial = (Oficial) select.uniqueResult();
                return oficial;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar oficial de justiça por CPF [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar oficial de justiça por CPF [DAO]****", e);
        }
    }     
    
    public Oficial buscaOficialEmail(String email) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Oficial o WHERE o.email = :email");
                select.setParameter("email", email);
                Oficial oficial = (Oficial) select.uniqueResult();
                return oficial;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar oficial por Email [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar oficial por Email [DAO]****", e);
        }
    }  
    
    public List<Oficial> listaOficiais() throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Oficial");
                List<Oficial> oficiais = select.list();
                return oficiais;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar lista de oficiais de justiça [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar lista de oficiais de justiça [DAO]****", e);
        }        
    }
}
