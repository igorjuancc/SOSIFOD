package br.com.sosifod.facade;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.dao.AdministradorDao;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.Seguranca;
import br.com.sosifod.util.SosifodUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorFacade {

    private static final AdministradorDao administradorDao = new AdministradorDao();

    public static List<String> cadastrarAdministrador(Administrador administrador) throws NoSuchAlgorithmException, DaoException {
        try {
            List<String> mensagens = new ArrayList();
            administrador.setCpf(administrador.getCpf().replace(".", "").replace("-", ""));
            administrador.setNome(administrador.getNome().trim().toUpperCase());
            if (administrador.getFone() != null) {
                administrador.setFone(administrador.getFone().replace("(", "").replace(")", "").replace("-", ""));
            }
            administrador.setEmail(administrador.getEmail().trim());
            administrador.setSenha(Seguranca.md5(administrador.getSenha()));

            if (!SosifodUtil.isCPF(administrador.getCpf())) {
                mensagens.add("CPF Inválido");
            } else if ((buscaAdministradorCpf(administrador.getCpf()) != null) || (OficialFacade.buscaOficialCpf(administrador.getCpf()) != null)) {
                mensagens.add("CPF já cadastrado");
            }
            if (!Seguranca.isEmail(administrador.getEmail())) {
                mensagens.add("Email Inválido");
            } else if ((buscaAdministradorEmail(administrador.getEmail()) != null) || (OficialFacade.buscaOficialEmail(administrador.getEmail()) != null)) {
                mensagens.add("Email já cadastrado");
            }

            if (mensagens.isEmpty()) {
                administradorDao.cadastrarAdministrador(administrador);
            }

            return mensagens;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("****Problemas com MD5 ao cadastrar novo administrador [Facade]****" + e);
            e.printStackTrace();
            throw e;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar novo administrador [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Administrador buscaAdministradorCpf(String cpf) throws DaoException {
        try {
            return administradorDao.buscaAdministradorCpf(cpf);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar administrador por CPF [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Administrador buscaAdministradorEmail(String email) throws DaoException {
        try {
            return administradorDao.buscaAdministradorEmail(email);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar administrador por email [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
