package br.com.dlweb.conmed.paciente;

public class DadosPaciente {
    private String id;
    private String nome;
    private String grp_sanguineo;
    private String logradouro;
    private String numero;
    private String cidade;
    private String uf;
    private String celular;
    private String fixo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrp_sanguineo() {
        return grp_sanguineo;
    }

    public void setGrp_sanguineo(String grp_sanguineo) {
        this.grp_sanguineo = grp_sanguineo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFixo() {
        return fixo;
    }

    public void setFixo(String fixo) {
        this.fixo = fixo;
    }

}