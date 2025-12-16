package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    // CREATE
    public void inserir(Servico servico) {

        String sql = """
            INSERT INTO servico (nome_servico, preco_servico)
            VALUES (?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, servico.getNomeServico());
            stmt.setBigDecimal(2, servico.getPrecoServico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Servico> listar() {

        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servico";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servico s = new Servico();
                s.setIdServico(rs.getInt("id_servico"));
                s.setNomeServico(rs.getString("nome_servico"));
                s.setPrecoServico(rs.getBigDecimal("preco_servico"));

                servicos.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }

    // READ - BUSCAR POR ID
    public Servico buscarPorId(int id) {

        String sql = "SELECT * FROM servico WHERE id_servico = ?";
        Servico servico = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                servico = new Servico();
                servico.setIdServico(rs.getInt("id_servico"));
                servico.setNomeServico(rs.getString("nome_servico"));
                servico.setPrecoServico(rs.getBigDecimal("preco_servico"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servico;
    }

    // UPDATE
    public void atualizar(Servico servico) {

        String sql = """
            UPDATE servico
               SET nome_servico = ?,
                   preco_servico = ?
             WHERE id_servico = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, servico.getNomeServico());
            stmt.setBigDecimal(2, servico.getPrecoServico());
            stmt.setInt(3, servico.getIdServico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM servico WHERE id_servico = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
