package br.com.sosifod.facade;

import br.com.sosifod.bean.Oficial;
import br.com.sosifod.dao.OficialDao;
import br.com.sosifod.dto.OficialDto;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.Seguranca;
import br.com.sosifod.util.SosifodUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class OficialFacade {
    
    private static final OficialDao oficialDao = new OficialDao();
    
    public static List<String> cadastrarOficial(Oficial oficial) throws DaoException, NoSuchAlgorithmException {
        try {
            List<String> mensagens = new ArrayList();
            oficial.setCpf(oficial.getCpf().replace(".", "").replace("-", ""));
            oficial.setNome(oficial.getNome().trim().toUpperCase());
            oficial.setEmail(oficial.getEmail().trim());
            oficial.setSenha(Seguranca.md5(oficial.getSenha()));
            
            if (!SosifodUtil.isCPF(oficial.getCpf())) {
                mensagens.add("CPF Inválido");
            } else if ((buscaOficialCpf(oficial.getCpf()) != null) || (AdministradorFacade.buscaAdministradorCpf(oficial.getCpf()) != null)) {
                mensagens.add("CPF já cadastrado");
            }
            if (!Seguranca.isEmail(oficial.getEmail())) {
                mensagens.add("Email Inválido");
            } else if ((buscaOficialEmail(oficial.getEmail()) != null) || (AdministradorFacade.buscaAdministradorEmail(oficial.getEmail()) != null)) {
                mensagens.add("Email já cadastrado");
            }
            
            if (mensagens.isEmpty()) {
                oficialDao.cadastrarOficial(oficial);
            }            
            
            return mensagens;            
        } catch (NoSuchAlgorithmException e) {
            System.out.println("****Problemas com MD5 ao cadastrar novo oficial [Facade]****" + e);
            e.printStackTrace();
            throw e;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar novo oficial [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }      
    }
    
    public static Oficial buscaOficialCpf(String cpf) throws DaoException {
        try {
            return oficialDao.buscaOficialCpf(cpf);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar oficial por CPF [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static Oficial buscaOficialEmail(String email) throws DaoException {
        try {
            return oficialDao.buscaOficialEmail(email);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar oficial por email [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }  
    
    public static List<OficialDto> listaOficiaisDto() throws DaoException {
        try {
            List<Oficial> oficiais = oficialDao.listaOficiais();
            List<OficialDto> oficiaisDto = new ArrayList<>();
            
            for (Oficial o : oficiais) {
                OficialDto oD = new OficialDto();
                oD.setCpf(o.getCpf());
                oD.setEmail(o.getEmail());
                oD.setId(o.getId());
                oD.setNome(o.getNome());
                oficiaisDto.add(oD);
            }
            return oficiaisDto;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar lista de oficiais de justiça DTO [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }        
    }
}
