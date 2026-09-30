package model;

public class Filme {

    private int id;
    private String tituloOriginal;
    private String tituloTraduzido;
    private int duracaoMinutos;
    private int anoLancamento;
    private GeneroFilme genero;

    public Filme() {
    }

    public Filme(String tituloOriginal, String tituloTraduzido, int duracaoMinutos,
                 int anoLancamento, GeneroFilme genero) {
        this.tituloOriginal = tituloOriginal;
        this.tituloTraduzido = tituloTraduzido;
        this.duracaoMinutos = duracaoMinutos;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
    }

    public Filme(int id, String tituloOriginal, String tituloTraduzido, int duracaoMinutos,
                 int anoLancamento, GeneroFilme genero) {
        this(tituloOriginal, tituloTraduzido, duracaoMinutos, anoLancamento, genero);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getTituloTraduzido() {
        return tituloTraduzido;
    }

    public void setTituloTraduzido(String tituloTraduzido) {
        this.tituloTraduzido = tituloTraduzido;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public GeneroFilme getGenero() {
        return genero;
    }

    public void setGenero(GeneroFilme genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Filme [id=" + id + ", tituloOriginal=" + tituloOriginal
                + ", tituloTraduzido=" + tituloTraduzido + ", duracaoMinutos=" + duracaoMinutos
                + ", anoLancamento=" + anoLancamento + ", genero=" + genero.getDescricao() + "]";
    }
}
