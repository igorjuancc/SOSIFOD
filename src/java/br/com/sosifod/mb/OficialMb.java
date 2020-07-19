package br.com.sosifod.mb;

import br.com.sosifod.bean.Intimacao;
import br.com.sosifod.bean.Oficial;
import br.com.sosifod.facade.IntimacaoFacade;
import br.com.sosifod.facade.OficialFacade;
import br.com.sosifod.util.SosifodUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class OficialMb implements Serializable {

    @Inject
    private LoginMb login;
    private Oficial oficial;
    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private List<Intimacao> intimacoes;
    private Intimacao intimacao;

    @PostConstruct
    public void init() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            if (ctx.getViewRoot().getViewId().equals("/Oficial/VerIntimacao.xhtml")) {
                if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idIntimacao") == null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Oficial/InicioOficial.jsf");
                } else {
                    int idProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idIntimacao");
                    this.intimacao = IntimacaoFacade.buscaIntimacaoId(idProcesso);
                }
            }
            this.oficial = new Oficial();
            if ((login != null) && (login.getOficial().getId() != 0)) {
                this.intimacoes = IntimacaoFacade.listaIntimacaoOficial(login.getOficial().getId());
            }
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(OficialMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarOficial() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!this.oficial.getSenha().equals(this.confirmaSenha)) {
                msg = SosifodUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                List<String> mensagens = OficialFacade.cadastrarOficial(this.oficial);
                if (!mensagens.isEmpty()) {
                    for (String print : mensagens) {
                        msg = SosifodUtil.emiteMsg(print, 2);
                        ctx.addMessage(null, msg);
                    }
                } else {
                    this.cadastroConcluido = true;
                }
            }
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(AdministradorMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String statusIntimacao(Intimacao intimacao) {
        String rtn = null;
        try {
            if (intimacao.getStatus() == null) {
                rtn = "PARA EXECUÇÃO";
            } else if (intimacao.getStatus() == false) {
                rtn = "EM EXECUÇÃO";
            } else {
                rtn = "CONCLUÍDO";
            }
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(OficialMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rtn;
    }

    public void verIntimacao(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idIntimacao", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Oficial/VerIntimacao.jsf");
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(OficialMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void atualizarIntimacao(Boolean opc) {
        try {
            this.intimacao.setStatus(opc);
            String mensagem = IntimacaoFacade.atualizarIntimacao(this.intimacao);
            if (mensagem == null) {
                ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/Oficial/InicioOficial.jsf");
            } else {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = SosifodUtil.emiteMsg(mensagem, 2);
                ctx.addMessage(null, msg);
            }
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(OficialMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String imprimeCpf(String cpf) {
        return SosifodUtil.imprimeCPF(cpf);
    }

    public Oficial getOficial() {
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public Boolean getCadastroConcluido() {
        return cadastroConcluido;
    }

    public void setCadastroConcluido(Boolean cadastroConcluido) {
        this.cadastroConcluido = cadastroConcluido;
    }

    public LoginMb getLogin() {
        return login;
    }

    public void setLogin(LoginMb login) {
        this.login = login;
    }

    public List<Intimacao> getIntimacoes() {
        return intimacoes;
    }

    public void setIntimacoes(List<Intimacao> intimacoes) {
        this.intimacoes = intimacoes;
    }

    public Intimacao getIntimacao() {
        return intimacao;
    }

    public void setIntimacao(Intimacao intimacao) {
        this.intimacao = intimacao;
    }
}
