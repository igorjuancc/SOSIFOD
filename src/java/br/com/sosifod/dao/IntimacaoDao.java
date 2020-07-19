package br.com.sosifod.dao;

import br.com.sosifod.bean.Intimacao;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class IntimacaoDao {
    public void cadastrarIntimacao(Intimacao intimacao) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(intimacao);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar nova intimacao [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar nova intimacao [DAO]****", e);
        }
    }   
    
    public void atualizarIntimacao(Intimacao intimacao) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.update(intimacao);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao atualizar intimacao [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao atualizar intimacao [DAO]****", e);
        }
    }   
    
    public List<Intimacao> listaIntimacaoOficial(int idOficial) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("SELECT i FROM Intimacao i JOIN i.oficial WHERE i.oficial.id = :idOficial");
                select.setParameter("idOficial", idOficial);     
                List<Intimacao> intimacoes = select.list();
                return intimacoes;
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar intimacoes por oficial [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar intimacoes por oficial [DAO]****", e);
        }        
    }
    
    public Intimacao buscaIntimacaoId(int id) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("SELECT i FROM Intimacao i WHERE i.id = :id");
                select.setParameter("id", id);     
                Intimacao intimacao = (Intimacao) select.uniqueResult();
                return intimacao;
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar intimacao por id [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar intimacao por id [DAO]****", e);
        }        
    }
}
