package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.GeneroFilme;
import util.ConnectionFactory;

public class GeneroFilmeDAO {

    public void salvar(GeneroFilme genero) {
        String sql = "INSERT INTO genero_filme (descricao, classificacao_indicativa) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, genero.getDescricao());
            ps.setString(2, genero.getClassificacaoIndicativa());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    genero.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar gênero: " + e.getMessage());
        }
    }

    public GeneroFilme buscarPorId(int id) {
        String sql = "SELECT id, descricao, classificacao_indicativa FROM genero_filme WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GeneroFilme(rs.getInt("id"), rs.getString("descricao"),
                            rs.getString("classificacao_indicativa"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar gênero: " + e.getMessage());
        }
        return null;
    }

    public List<GeneroFilme> listarTodos() {
        List<GeneroFilme> generos = new ArrayList<>();
        String sql = "SELECT id, descricao, classificacao_indicativa FROM genero_filme ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                generos.add(new GeneroFilme(rs.getInt("id"), rs.getString("descricao"),
                        rs.getString("classificacao_indicativa")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar gêneros: " + e.getMessage());
        }
        return generos;
    }

    public void atualizar(GeneroFilme genero) {
        String sql = "UPDATE genero_filme SET descricao = ?, classificacao_indicativa = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, genero.getDescricao());
            ps.setString(2, genero.getClassificacaoIndicativa());
            ps.setInt(3, genero.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar gênero: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM genero_filme WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar gênero: " + e.getMessage());
        }
    }
}
