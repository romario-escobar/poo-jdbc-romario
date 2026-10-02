package app;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

import dao.FilmeDAO;
import dao.GeneroFilmeDAO;
import model.Filme;
import model.GeneroFilme;

public class Main {

    // O Scanner lê o teclado usando a codificação do próprio console (evita acentos quebrados)
    private static final Scanner sc = new Scanner(System.in, charsetDoConsole().name());
    private static final GeneroFilmeDAO generoDAO = new GeneroFilmeDAO();
    private static final FilmeDAO filmeDAO = new FilmeDAO();

    private static final int LARGURA_MENU = 44;
    private static final String FORMATO_GENERO = "  %-3s | %-24s | %-13s%n";
    private static final String FORMATO_FILME = "  %-3s | %-26s | %-32s | %-5s | %-4s | %-18s%n";

    // Coloque false se o seu console não interpretar o comando de limpar tela
    private static final boolean LIMPAR_TELA = true;

    public static void main(String[] args) {
        boolean sair = false;

        while (!sair) {
            limparTela();
            exibirMenu();
            int opcao = lerInt("  Escolha uma opção: ");
            limparTela();

            switch (opcao) {
                case 1:
                    cadastrarGenero();
                    break;
                case 2:
                    listarGeneros();
                    break;
                case 3:
                    atualizarGenero();
                    break;
                case 4:
                    removerGenero();
                    break;
                case 5:
                    cadastrarFilme();
                    break;
                case 6:
                    listarFilmes();
                    break;
                case 7:
                    buscarFilme();
                    break;
                case 8:
                    atualizarFilme();
                    break;
                case 9:
                    removerFilme();
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    aviso("Opção inválida. Escolha um número do menu.");
            }

            if (!sair) {
                sair = perguntarVoltar();
            }
        }

        System.out.println("  Encerrando o sistema... Até logo!");
        sc.close();
    }

    // ---------- Menu ----------

    private static void exibirMenu() {
        String borda = "  +" + repetir('=', LARGURA_MENU) + "+";
        String divisoria = "  +" + repetir('-', LARGURA_MENU) + "+";

        System.out.println();
        System.out.println(borda);
        System.out.println("  |" + centralizar("CATÁLOGO DE FILMES", LARGURA_MENU) + "|");
        System.out.println(borda);
        linhaMenu("GÊNEROS");
        linhaMenu("   1 - Cadastrar gênero");
        linhaMenu("   2 - Listar gêneros");
        linhaMenu("   3 - Atualizar gênero");
        linhaMenu("   4 - Remover gênero");
        System.out.println(divisoria);
        linhaMenu("FILMES");
        linhaMenu("   5 - Cadastrar filme");
        linhaMenu("   6 - Listar filmes (com gênero)");
        linhaMenu("   7 - Buscar filme por ID");
        linhaMenu("   8 - Atualizar filme");
        linhaMenu("   9 - Remover filme");
        System.out.println(divisoria);
        linhaMenu("   0 - Sair");
        System.out.println(borda);
        System.out.println();
    }

    private static void linhaMenu(String texto) {
        System.out.printf("  | %-" + (LARGURA_MENU - 2) + "s |%n", texto);
    }

    // ---------- Gêneros ----------

    private static void cadastrarGenero() {
        titulo("CADASTRAR GÊNERO");

        String descricao = lerTexto("  Descrição do gênero: ");
        String classificacao = lerTexto("  Classificação indicativa (ex.: L, 12, 16): ");

        if (descricao.isEmpty() || classificacao.isEmpty()) {
            System.out.println();
            aviso("Descrição e classificação não podem ficar vazias.");
            return;
        }

        GeneroFilme genero = new GeneroFilme(descricao, classificacao);
        generoDAO.salvar(genero);

        System.out.println();
        if (genero.getId() > 0) {
            sucesso("Gênero cadastrado com ID " + genero.getId() + ".");
        }
    }

    private static void listarGeneros() {
        titulo("LISTA DE GÊNEROS");

        List<GeneroFilme> generos = generoDAO.listarTodos();

        if (generos.isEmpty()) {
            aviso("Nenhum gênero cadastrado.");
            return;
        }

        imprimirTabelaGeneros(generos);
        System.out.println();
        System.out.println("  Total: " + generos.size() + " gênero(s)");
    }

    private static void atualizarGenero() {
        titulo("ATUALIZAR GÊNERO");

        int id = lerInt("  ID do gênero a atualizar: ");
        GeneroFilme genero = generoDAO.buscarPorId(id);

        if (genero == null) {
            System.out.println();
            aviso("Gênero não encontrado.");
            return;
        }

        System.out.println();
        System.out.println("  Dados atuais:");
        imprimirGenero(genero);
        System.out.println();

        genero.setDescricao(lerTexto("  Nova descrição: "));
        genero.setClassificacaoIndicativa(lerTexto("  Nova classificação indicativa: "));
        generoDAO.atualizar(genero);

        System.out.println();
        sucesso("Gênero atualizado.");
    }

    private static void removerGenero() {
        titulo("REMOVER GÊNERO");

        int id = lerInt("  ID do gênero a remover: ");
        System.out.println();

        if (generoDAO.buscarPorId(id) == null) {
            aviso("Gênero não encontrado.");
            return;
        }

        generoDAO.deletar(id);

        if (generoDAO.buscarPorId(id) == null) {
            sucesso("Gênero removido.");
        } else {
            aviso("Não foi possível remover: o gênero ainda possui filmes cadastrados.");
        }
    }

    // ---------- Filmes ----------

    private static void cadastrarFilme() {
        titulo("CADASTRAR FILME");

        List<GeneroFilme> generos = generoDAO.listarTodos();

        if (generos.isEmpty()) {
            aviso("Nenhum gênero cadastrado. Cadastre um gênero primeiro.");
            return;
        }

        System.out.println("  Gêneros disponíveis:");
        imprimirTabelaGeneros(generos);
        System.out.println();

        int generoId = lerInt("  ID do gênero do filme: ");
        GeneroFilme genero = generoDAO.buscarPorId(generoId);

        if (genero == null) {
            System.out.println();
            aviso("Gênero não encontrado.");
            return;
        }

        String original = lerTexto("  Título original: ");
        String traduzido = lerTexto("  Título traduzido: ");
        int duracao = lerInt("  Duração (minutos): ");
        int ano = lerInt("  Ano de lançamento: ");

        if (original.isEmpty()) {
            System.out.println();
            aviso("O título original não pode ficar vazio.");
            return;
        }

        Filme filme = new Filme(original, traduzido, duracao, ano, genero);
        filmeDAO.salvar(filme);

        System.out.println();
        if (filme.getId() > 0) {
            sucesso("Filme cadastrado com ID " + filme.getId() + ".");
        }
    }

    private static void listarFilmes() {
        titulo("LISTA DE FILMES");

        List<Filme> filmes = filmeDAO.listarTodos();

        if (filmes.isEmpty()) {
            aviso("Nenhum filme cadastrado.");
            return;
        }

        System.out.printf(FORMATO_FILME, "ID", "Título original", "Título traduzido", "Min", "Ano", "Gênero");
        System.out.println("  " + repetir('-', 103));
        for (Filme f : filmes) {
            System.out.printf(FORMATO_FILME, f.getId(), cortar(f.getTituloOriginal(), 26),
                    cortar(f.getTituloTraduzido(), 32), f.getDuracaoMinutos(), f.getAnoLancamento(),
                    cortar(f.getGenero().getDescricao(), 18));
        }
        System.out.println();
        System.out.println("  Total: " + filmes.size() + " filme(s)");
    }

    private static void buscarFilme() {
        titulo("BUSCAR FILME POR ID");

        int id = lerInt("  ID do filme: ");
        Filme filme = filmeDAO.buscarPorId(id);

        System.out.println();
        if (filme == null) {
            aviso("Filme não encontrado.");
        } else {
            imprimirFilme(filme);
        }
    }

    private static void atualizarFilme() {
        titulo("ATUALIZAR FILME");

        int id = lerInt("  ID do filme a atualizar: ");
        Filme filme = filmeDAO.buscarPorId(id);

        if (filme == null) {
            System.out.println();
            aviso("Filme não encontrado.");
            return;
        }

        System.out.println();
        System.out.println("  Dados atuais:");
        imprimirFilme(filme);
        System.out.println();

        System.out.println("  Gêneros disponíveis:");
        imprimirTabelaGeneros(generoDAO.listarTodos());
        System.out.println();

        int generoId = lerInt("  ID do gênero: ");
        GeneroFilme genero = generoDAO.buscarPorId(generoId);

        if (genero == null) {
            System.out.println();
            aviso("Gênero não encontrado. Atualização cancelada.");
            return;
        }

        filme.setTituloOriginal(lerTexto("  Novo título original: "));
        filme.setTituloTraduzido(lerTexto("  Novo título traduzido: "));
        filme.setDuracaoMinutos(lerInt("  Nova duração (minutos): "));
        filme.setAnoLancamento(lerInt("  Novo ano de lançamento: "));
        filme.setGenero(genero);
        filmeDAO.atualizar(filme);

        System.out.println();
        sucesso("Filme atualizado.");
    }

    private static void removerFilme() {
        titulo("REMOVER FILME");

        int id = lerInt("  ID do filme a remover: ");
        System.out.println();

        if (filmeDAO.buscarPorId(id) == null) {
            aviso("Filme não encontrado.");
            return;
        }

        filmeDAO.deletar(id);
        sucesso("Filme removido.");
    }

    // ---------- Apresentação ----------

    private static void limparTela() {
        if (LIMPAR_TELA) {
            System.out.print("\033[H\033[2J\033[3J");
            System.out.flush();
        }
    }

    // Rodapé exibido depois de cada ação; devolve true se o usuário quiser sair
    private static boolean perguntarVoltar() {
        System.out.println();
        System.out.println("  " + repetir('-', 58));
        String resposta = lerTexto("  ENTER = voltar ao menu  |  0 = sair: ");
        return resposta.equals("0");
    }

    private static void titulo(String texto) {
        System.out.println("  " + texto);
        System.out.println("  " + repetir('-', texto.length()));
        System.out.println();
    }

    private static void sucesso(String mensagem) {
        System.out.println("  [OK] " + mensagem);
    }

    private static void aviso(String mensagem) {
        System.out.println("  [!] " + mensagem);
    }

    private static void imprimirTabelaGeneros(List<GeneroFilme> generos) {
        System.out.printf(FORMATO_GENERO, "ID", "Descrição", "Classificação");
        System.out.println("  " + repetir('-', 46));
        for (GeneroFilme g : generos) {
            System.out.printf(FORMATO_GENERO, g.getId(), cortar(g.getDescricao(), 24),
                    cortar(g.getClassificacaoIndicativa(), 13));
        }
    }

    private static void imprimirGenero(GeneroFilme g) {
        System.out.printf("  %-18s %s%n", "ID:", g.getId());
        System.out.printf("  %-18s %s%n", "Descrição:", g.getDescricao());
        System.out.printf("  %-18s %s%n", "Classificação:", g.getClassificacaoIndicativa());
    }

    private static void imprimirFilme(Filme f) {
        System.out.printf("  %-18s %s%n", "ID:", f.getId());
        System.out.printf("  %-18s %s%n", "Título original:", f.getTituloOriginal());
        System.out.printf("  %-18s %s%n", "Título traduzido:", f.getTituloTraduzido());
        System.out.printf("  %-18s %s%n", "Duração:", f.getDuracaoMinutos() + " min");
        System.out.printf("  %-18s %s%n", "Ano:", f.getAnoLancamento());
        System.out.printf("  %-18s %s%n", "Gênero:", f.getGenero().getDescricao());
    }

    private static String repetir(char c, int vezes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vezes; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static String centralizar(String texto, int largura) {
        int esquerda = (largura - texto.length()) / 2;
        int direita = largura - texto.length() - esquerda;
        return repetir(' ', esquerda) + texto + repetir(' ', direita);
    }

    private static String cortar(String texto, int max) {
        if (texto == null) {
            return "-";
        }
        if (texto.length() <= max) {
            return texto;
        }
        return texto.substring(0, max - 3) + "...";
    }

    // ---------- Leitura de dados ----------

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Digite um número inteiro válido.");
            }
        }
    }

    // Descobre a codificação que o console usa para o teclado
    private static Charset charsetDoConsole() {
        // 1. Permite forçar a codificação: java -Dentrada.encoding=UTF-8 ...
        String nome = System.getProperty("entrada.encoding");

        // 2. Codificação informada pela JVM quando o teclado é reconhecido como console
        if (nome == null) {
            nome = System.getProperty("stdin.encoding");
        }
        if (nome == null) {
            nome = System.getProperty("sun.stdin.encoding");
        }

        // 3. Windows sem informação: o console em português usa a página de código 850
        if (nome == null && System.getProperty("os.name").toLowerCase().contains("win")) {
            nome = "Cp850";
        }

        if (nome != null) {
            try {
                return Charset.forName(nome);
            } catch (Exception e) {
                // se o nome não for reconhecido, usa o padrão
            }
        }
        return Charset.defaultCharset();
    }
}