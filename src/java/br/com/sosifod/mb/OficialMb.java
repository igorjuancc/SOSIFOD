package br.com.sosifod.mb;

import br.com.sosifod.bean.Oficial;
import br.com.sosifod.facade.OficialFacade;
import br.com.sosifod.util.SosifodUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class OficialMb implements Serializable {

    private Oficial oficial;
    private String confirmaSenha;
    private Boolean cadastroConcluido;

    @PostConstruct
    public void init() {
        try {
            this.oficial = new Oficial();
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
}
