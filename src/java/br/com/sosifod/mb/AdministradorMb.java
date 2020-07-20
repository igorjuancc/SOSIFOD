package br.com.sosifod.mb;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.bean.Intimacao;
import br.com.sosifod.facade.AdministradorFacade;
import br.com.sosifod.facade.IntimacaoFacade;
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
public class AdministradorMb implements Serializable {

    @Inject
    private LoginMb login;
    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private Administrador administrador;
    private Intimacao intimacao;
    private List<Intimacao> intimacoes;

    @PostConstruct
    public void init() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            if (ctx.getViewRoot().getViewId().equals("/Administrador/VerIntimacao.xhtml")) {
                if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idIntimacao") == null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Oficial/InicioOficial.jsf");
                } else {
                    int idProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idIntimacao");
                    this.intimacao = IntimacaoFacade.buscaIntimacaoId(idProcesso);
                }
            }
            this.administrador = new Administrador();
            if ((login != null) && (login.getAdm().getId() != 0)) {
                this.intimacoes = IntimacaoFacade.listaIntimacao();
            }

        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(AdministradorMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarAdministrador() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!this.administrador.getSenha().equals(this.confirmaSenha)) {
                msg = SosifodUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                List<String> mensagens = AdministradorFacade.cadastrarAdministrador(this.administrador);
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

    public void verIntimacao(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idIntimacao", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Administrador/VerIntimacao.jsf");
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(OficialMb.class.getName()).log(Level.SEVERE, null, ex);
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

    public void apagarIntimacao(Intimacao intimacao) {
        try {
            IntimacaoFacade.apagarIntimacao(intimacao);    
            this.intimacoes = IntimacaoFacade.listaIntimacao();
            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage msg = SosifodUtil.emiteMsg("Intimação deletada", 1);
            ctx.addMessage(null, msg);
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

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Boolean getCadastroConcluido() {
        return cadastroConcluido;
    }

    public void setCadastroConcluido(Boolean cadastroConcluido) {
        this.cadastroConcluido = cadastroConcluido;
    }

    public Intimacao getIntimacao() {
        return intimacao;
    }

    public void setIntimacao(Intimacao intimacao) {
        this.intimacao = intimacao;
    }

    public List<Intimacao> getIntimacoes() {
        return intimacoes;
    }

    public void setIntimacoes(List<Intimacao> intimacoes) {
        this.intimacoes = intimacoes;
    }
}
