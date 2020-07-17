package br.com.sosifod.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "tb_intimacao")
@SequenceGenerator(name = "seq_intimacao", sequenceName = "tb_intimaca_id_intimacao_seq")
public class Intimacao implements Serializable {
    private int id;
    private int processo;
    private Date dataHora;
    private Date dataHoraExec;
    private String cpf;
    private String nome;
    private Oficial oficial;
    private Endereco endereco;

    public Intimacao() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_intimacao")
    @Column(name = "id_intimacao")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "processo_intimacao")
    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }
    
    @Column(name = "dataHora_intimacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
    
    @Column(name = "dataHora_exec_intimacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDataHoraExec() {
        return dataHoraExec;
    }

    public void setDataHoraExec(Date dataHoraExec) {
        this.dataHoraExec = dataHoraExec;
    }    
    
    @Column(name = "cpf_intimacao")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @Column(name = "nome_intimacao")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @ManyToOne
    @JoinColumn(name="id_oficial_intimacao")
    public Oficial getOficial() {
        return oficial;
    }

    public void setOficial(Oficial oficial) {
        this.oficial = oficial;
    }
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco_intimacao", updatable = true)
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        hash = 71 * hash + this.processo;
        hash = 71 * hash + Objects.hashCode(this.dataHora);
        hash = 71 * hash + Objects.hashCode(this.dataHoraExec);
        hash = 71 * hash + Objects.hashCode(this.cpf);
        hash = 71 * hash + Objects.hashCode(this.nome);
        hash = 71 * hash + Objects.hashCode(this.oficial);
        hash = 71 * hash + Objects.hashCode(this.endereco);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Intimacao other = (Intimacao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.processo != other.processo) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.dataHora, other.dataHora)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraExec, other.dataHoraExec)) {
            return false;
        }
        if (!Objects.equals(this.oficial, other.oficial)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        return true;
    }
}
