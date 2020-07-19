package br.com.sosifod.facade;

import br.com.sosifod.bean.Cidade;
import br.com.sosifod.bean.Endereco;
import br.com.sosifod.bean.Intimacao;
import br.com.sosifod.bean.Oficial;
import br.com.sosifod.dao.IntimacaoDao;
import br.com.sosifod.dto.EnderecoDto;
import br.com.sosifod.dto.IntimacaoDto;
import br.com.sosifod.dto.OficialDto;
import br.com.sosifod.exception.DaoException;
import br.com.sosifod.util.SosifodUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class IntimacaoFacade {

    private static final IntimacaoDao intimacaoDao = new IntimacaoDao();

    public static List<String> cadastrarIntimacao(Intimacao intimacao) throws DaoException {
        try {
            List<String> mensagens = new ArrayList();

            if (intimacao == null) {
                mensagens.add("Intimação não é válida");
            } else if (intimacao.getEndereco() == null) {
                mensagens.add("Endereço não é válido");
            } else if (intimacao.getOficial() == null) {
                mensagens.add("Oficial de justiça não é válido");
            } else {
                intimacao.setCpf(intimacao.getCpf().replace(".", "").replace("-", ""));
                intimacao.setNome(intimacao.getNome().trim().toUpperCase());
                intimacao.getEndereco().setRua(intimacao.getEndereco().getRua().trim().toUpperCase());
                intimacao.getEndereco().setBairro(intimacao.getEndereco().getBairro().trim().toUpperCase());
                intimacao.getEndereco().setCep(intimacao.getEndereco().getCep().replace(".", "").replace("-", "").trim());

                if (intimacao.getEndereco().getComplemento() != null) {
                    intimacao.getEndereco().setComplemento(intimacao.getEndereco().getComplemento().trim().toUpperCase());
                }

                if (intimacao.getProcesso() == 0) {
                    mensagens.add("Número do processo não é válido");
                }
                if ((intimacao.getCpf() == null) || (intimacao.getCpf().equals("")) || (SosifodUtil.isCPF(intimacao.getCpf()))) {
                    mensagens.add("CPF do intimado não é válido");
                }
                if ((intimacao.getNome() == null) || (intimacao.getNome().equals(""))) {
                    mensagens.add("Nome do intimado não é válido");
                }

                if (intimacao.getEndereco().getNumero() == 0) {
                    mensagens.add("Número do endereço de intimação não é válido");
                }
                if ((intimacao.getEndereco().getCep() == null) || (intimacao.getEndereco().getCep().equals(""))) {
                    mensagens.add("CEP de intimação não é válido");
                }
                if ((intimacao.getEndereco().getRua() == null) || (intimacao.getEndereco().getRua().equals(""))) {
                    mensagens.add("Rua de endereço de intimação não é válido");
                }
                if ((intimacao.getEndereco().getBairro() == null) || (intimacao.getEndereco().getBairro().equals(""))) {
                    mensagens.add("Bairro de endereço de intimação não é válido");
                }
                if (intimacao.getEndereco().getCidade() == null) {
                    mensagens.add("Cidade de endereço de intimação não é válido");
                }

                if (mensagens.isEmpty()) {
                    intimacao.setDataHora(new Date());
                    intimacaoDao.cadastrarIntimacao(intimacao);
                }
            }
            return mensagens;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar nova intimacao [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Intimacao cadastrarIntimacaoDto(IntimacaoDto intimacaoDto) throws DaoException {
        try {
            Intimacao intimacao = new Intimacao();
            intimacao.setCpf(intimacaoDto.getCpf());
            intimacao.setDataHora(intimacaoDto.getDataHora());
            intimacao.setNome(intimacaoDto.getNome());
            intimacao.setProcesso(intimacaoDto.getProcesso());

            intimacao.setOficial(new Oficial());
            intimacao.getOficial().setId(intimacaoDto.getOficial().getId());

            intimacao.setEndereco(new Endereco());
            intimacao.getEndereco().setBairro(intimacaoDto.getEndereco().getBairro());
            intimacao.getEndereco().setCep(intimacaoDto.getEndereco().getCep());

            intimacao.getEndereco().setCidade(new Cidade());
            intimacao.getEndereco().getCidade().setId(intimacaoDto.getEndereco().getCidade());

            intimacao.getEndereco().setComplemento(intimacaoDto.getEndereco().getComplemento());
            intimacao.getEndereco().setNumero(intimacaoDto.getEndereco().getNumero());
            intimacao.getEndereco().setRua(intimacaoDto.getEndereco().getRua());

            intimacaoDao.cadastrarIntimacao(intimacao);
            return intimacao;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar nova intimacao DTO [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Intimacao> listaIntimacaoOficial(int idOficial) throws DaoException {
        try {
            return intimacaoDao.listaIntimacaoOficial(idOficial);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar intimacoes por oficial [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Intimacao buscaIntimacaoId(int id) throws DaoException {
        try {
            return intimacaoDao.buscaIntimacaoId(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar intimacao por id [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static String atualizarIntimacao(Intimacao intimacao) throws DaoException {
        try {
            Intimacao intVerificar = intimacaoDao.buscaIntimacaoId(intimacao.getId());
            if ((intVerificar != null) && (intVerificar.getStatus())) {
                return "Intimação já foi executada";
            } else {
                intimacao.setDataHoraExec(new Date());
                intimacaoDao.atualizarIntimacao(intimacao);

                IntimacaoDto intimacaoDto = new IntimacaoDto();
                intimacaoDto.setCpf(intimacao.getCpf());
                intimacaoDto.setDataHora(intimacao.getDataHora());
                intimacaoDto.setDataHoraExec(intimacao.getDataHoraExec());
                intimacaoDto.setId(intimacao.getId());
                intimacaoDto.setNome(intimacao.getNome());
                intimacaoDto.setProcesso(intimacao.getProcesso());
                intimacaoDto.setStatus(intimacao.getStatus());
                
                intimacaoDto.setOficial(new OficialDto());
                intimacaoDto.getOficial().setId(intimacao.getOficial().getId());
                intimacaoDto.getOficial().setNome(intimacao.getOficial().getNome());
                
                intimacaoDto.setEndereco(new EnderecoDto());
                intimacaoDto.getEndereco().setBairro(intimacao.getEndereco().getBairro());
                intimacaoDto.getEndereco().setCep(intimacao.getEndereco().getCep());
                intimacaoDto.getEndereco().setCidade(intimacao.getEndereco().getCidade().getId());
                intimacaoDto.getEndereco().setComplemento(intimacao.getEndereco().getComplemento());
                intimacaoDto.getEndereco().setNumero(intimacao.getEndereco().getNumero());
                intimacaoDto.getEndereco().setRua(intimacao.getEndereco().getRua());
                
                Client client = ClientBuilder.newClient();
                intimacaoDto = client.target("http://localhost:8080/SIJOGA/webresources/sijoga/execIntimacao").request(MediaType.APPLICATION_JSON).post(Entity.json(intimacaoDto), IntimacaoDto.class);
                
                if (intimacaoDto.getId() == 0) {
                    return "Problemas ao criar nova fase no processo!";
                } else {
                    return null;
                }
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao atualizar intimacao [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
