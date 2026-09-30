package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Filme;
import model.GeneroFilme;
import util.ConnectionFactory;

public class FilmeDAO {

    private static final String SELECT_JOIN =
            "SELECT f.id, f.titulo_original, f.titulo_traduzido, f.duracao_minutos, f.ano_lancamento, "
            + "g.id AS genero_id, g.descricao, g.classificacao_indicativa "
            + "FROM filme f INNER JOIN genero_filme g ON f.genero_id = g.id ";

    public void salvar(Filme filme) {
        String sql = "INSERT INTO filme (titulo_original, titulo_traduzido, duracao_minutos, "
                + "ano_lancamento, genero_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, filme.getTituloOriginal());
            ps.setString(2, filme.getTituloTraduzido());
            ps.setInt(3, filme.getDuracaoMinutos());
            ps.setInt(4, filme.getAnoLancamento());
            ps.setInt(5, filme.getGenero().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    filme.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar filme: " + e.getMessage());
        }
    }

    public Filme buscarPorId(int id) {
        String sql = SELECT_JOIN + "WHERE f.id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar filme: " + e.getMessage());
        }
        return null;
    }

    public List<Filme> listarTodos() {
        List<Filme> filmes = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY f.id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                filmes.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar filmes: " + e.getMessage());
        }
        return filmes;
    }

    public void atualizar(Filme filme) {
        String sql = "UPDATE filme SET titulo_original = ?, titulo_traduzido = ?, duracao_minutos = ?, "
                + "ano_lancamento = ?, genero_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, filme.getTituloOriginal());
            ps.setString(2, filme.getTituloTraduzido());
            ps.setInt(3, filme.getDuracaoMinutos());
            ps.setInt(4, filme.getAnoLancamento());
            ps.setInt(5, filme.getGenero().getId());
            ps.setInt(6, filme.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM filme WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar filme: " + e.getMessage());
        }
    }

    private Filme mapear(ResultSet rs) throws SQLException {
        GeneroFilme genero = new GeneroFilme(rs.getInt("genero_id"), rs.getString("descricao"),
                rs.getString("classificacao_indicativa"));

        return new Filme(rs.getInt("id"), rs.getString("titulo_original"),
                rs.getString("titulo_traduzido"), rs.getInt("duracao_minutos"),
                rs.getInt("ano_lancamento"), genero);
    }
}
