package br.com.sosifod.dao;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class AdministradorDao {
    public void cadastrarAdministrador(Administrador administrador) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(administrador);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo administrador [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo administrador [DAO]****", e);
        }
    }     
}
