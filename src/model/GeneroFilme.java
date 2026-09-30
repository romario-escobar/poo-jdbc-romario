package model;

public class GeneroFilme {

    private int id;
    private String descricao;
    private String classificacaoIndicativa;

    public GeneroFilme() {
    }

    public GeneroFilme(String descricao, String classificacaoIndicativa) {
        this.descricao = descricao;
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public GeneroFilme(int id, String descricao, String classificacaoIndicativa) {
        this(descricao, classificacaoIndicativa);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(String classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    @Override
    public String toString() {
        return "GeneroFilme [id=" + id + ", descricao=" + descricao
                + ", classificacaoIndicativa=" + classificacaoIndicativa + "]";
    }
}
