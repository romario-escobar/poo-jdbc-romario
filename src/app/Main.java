package app;

import java.util.List;
import java.util.Scanner;

import dao.FilmeDAO;
import dao.GeneroFilmeDAO;
import model.Filme;
import model.GeneroFilme;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final GeneroFilmeDAO generoDAO = new GeneroFilmeDAO();
    private static final FilmeDAO filmeDAO = new FilmeDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

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
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== CATÁLOGO DE FILMES =====");
        System.out.println("1 - Cadastrar gênero");
        System.out.println("2 - Listar gêneros");
        System.out.println("3 - Atualizar gênero");
        System.out.println("4 - Remover gênero");
        System.out.println("5 - Cadastrar filme");
        System.out.println("6 - Listar filmes (com gênero)");
        System.out.println("7 - Buscar filme por ID");
        System.out.println("8 - Atualizar filme");
        System.out.println("9 - Remover filme");
        System.out.println("0 - Sair");
    }

    // ---------- Gêneros ----------

    private static void cadastrarGenero() {
        String descricao = lerTexto("Descrição do gênero: ");
        String classificacao = lerTexto("Classificação indicativa (ex.: L, 12, 16): ");

        GeneroFilme genero = new GeneroFilme(descricao, classificacao);
        generoDAO.salvar(genero);

        if (genero.getId() > 0) {
            System.out.println("Gênero cadastrado com ID " + genero.getId() + ".");
        }
    }

    private static void listarGeneros() {
        List<GeneroFilme> generos = generoDAO.listarTodos();

        if (generos.isEmpty()) {
            System.out.println("Nenhum gênero cadastrado.");
            return;
        }
        for (GeneroFilme g : generos) {
            System.out.println(g);
        }
    }

    private static void atualizarGenero() {
        int id = lerInt("ID do gênero a atualizar: ");
        GeneroFilme genero = generoDAO.buscarPorId(id);

        if (genero == null) {
            System.out.println("Gênero não encontrado.");
            return;
        }

        System.out.println("Atual: " + genero);
        genero.setDescricao(lerTexto("Nova descrição: "));
        genero.setClassificacaoIndicativa(lerTexto("Nova classificação indicativa: "));
        generoDAO.atualizar(genero);
        System.out.println("Gênero atualizado.");
    }

    private static void removerGenero() {
        int id = lerInt("ID do gênero a remover: ");

        if (generoDAO.buscarPorId(id) == null) {
            System.out.println("Gênero não encontrado.");
            return;
        }

        generoDAO.deletar(id);

        if (generoDAO.buscarPorId(id) == null) {
            System.out.println("Gênero removido.");
        } else {
            System.out.println("Não foi possível remover: o gênero ainda possui filmes cadastrados.");
        }
    }

    // ---------- Filmes ----------

    private static void cadastrarFilme() {
        int generoId = lerInt("ID do gênero do filme: ");
        GeneroFilme genero = generoDAO.buscarPorId(generoId);

        if (genero == null) {
            System.out.println("Gênero não encontrado. Cadastre o gênero primeiro.");
            return;
        }

        String original = lerTexto("Título original: ");
        String traduzido = lerTexto("Título traduzido: ");
        int duracao = lerInt("Duração (minutos): ");
        int ano = lerInt("Ano de lançamento: ");

        Filme filme = new Filme(original, traduzido, duracao, ano, genero);
        filmeDAO.salvar(filme);

        if (filme.getId() > 0) {
            System.out.println("Filme cadastrado com ID " + filme.getId() + ".");
        }
    }

    private static void listarFilmes() {
        List<Filme> filmes = filmeDAO.listarTodos();

        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
            return;
        }
        for (Filme f : filmes) {
            System.out.println(f);
        }
    }

    private static void buscarFilme() {
        int id = lerInt("ID do filme: ");
        Filme filme = filmeDAO.buscarPorId(id);

        if (filme == null) {
            System.out.println("Filme não encontrado.");
        } else {
            System.out.println(filme);
        }
    }

    private static void atualizarFilme() {
        int id = lerInt("ID do filme a atualizar: ");
        Filme filme = filmeDAO.buscarPorId(id);

        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        System.out.println("Atual: " + filme);
        int generoId = lerInt("ID do gênero: ");
        GeneroFilme genero = generoDAO.buscarPorId(generoId);

        if (genero == null) {
            System.out.println("Gênero não encontrado. Atualização cancelada.");
            return;
        }

        filme.setTituloOriginal(lerTexto("Novo título original: "));
        filme.setTituloTraduzido(lerTexto("Novo título traduzido: "));
        filme.setDuracaoMinutos(lerInt("Nova duração (minutos): "));
        filme.setAnoLancamento(lerInt("Novo ano de lançamento: "));
        filme.setGenero(genero);
        filmeDAO.atualizar(filme);
        System.out.println("Filme atualizado.");
    }

    private static void removerFilme() {
        int id = lerInt("ID do filme a remover: ");

        if (filmeDAO.buscarPorId(id) == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        filmeDAO.deletar(id);
        System.out.println("Filme removido.");
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
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
}