package br.com.sosifod.mb;

import br.com.sosifod.bean.Administrador;
import br.com.sosifod.bean.Oficial;
import br.com.sosifod.facade.LoginFacade;
import br.com.sosifod.util.Seguranca;
import br.com.sosifod.util.SosifodUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginMb implements Serializable {

    private String email;
    private String senha;
    private Administrador adm = new Administrador();
    private Oficial oficial = new Oficial();

    public LoginMb() {
    }

    public void efetuaLogin() {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage msg;

            this.adm.setSenha(Seguranca.md5(this.senha));
            this.adm.setEmail(this.email);
            this.oficial.setSenha(Seguranca.md5(this.senha));
            this.oficial.setEmail(this.email);

            this.adm = LoginFacade.loginAdministrador(this.adm);
            this.oficial = LoginFacade.loginOficial(this.oficial);

            if (this.adm != null) {
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/Administrador/InicioAdministrador.jsf");
                this.oficial = new Oficial();
            } else if (this.oficial != null) {
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/Oficial/InicioOficial.jsf");
                this.adm = new Administrador();
            } else {
                this.senha = "";
                this.adm = new Administrador();
                this.oficial = new Oficial();
                msg = SosifodUtil.emiteMsg("Usuario ou Senha Inválido!", 3);
                ctx.addMessage(null, msg);
            }
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void logout() {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            if ((this.adm.getId() != 0) || (this.oficial.getId() != 0)) {
                ctxExt.invalidateSession();
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/index.jsf");
            }
        } catch (Exception e) {
            try {
                SosifodUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Administrador getAdm() {
        return adm;
    }

    public void setAdm(Administrador adm) {
        this.adm = adm;
    }

    public Oficial getOficial() {
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
    }
}
