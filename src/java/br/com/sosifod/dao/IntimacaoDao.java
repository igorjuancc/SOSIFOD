package br.com.sosifod.dao;

import br.com.sosifod.bean.Intimacao;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.HibernateUtil;
import org.hibernate.HibernateException;
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
}
