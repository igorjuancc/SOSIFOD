package br.com.sosifod.mb;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.facade.AdministradorFacade;
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
public class AdministradorMb implements Serializable {
    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private Administrador administrador;    
    
    @PostConstruct
    public void init() {
        try {
            this.administrador = new Administrador();           
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
}
