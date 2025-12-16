package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    // CREATE
    public void inserir(Pagamento pagamento) {

        String sql = """
            INSERT INTO pagamento (metodo, valor_total, status)
            VALUES (?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt =
                 con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pagamento.getMetodo()); // ENUM como String
            stmt.setBigDecimal(2, pagamento.getValorTotalBD() != null ? pagamento.getValorTotalBD() : 
                                   java.math.BigDecimal.valueOf(pagamento.getValorTotal()));
            stmt.setString(3, pagamento.getStatus());

            stmt.executeUpdate();

            // recupera ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pagamento.setIdPagamento(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Pagamento> listar() {

        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamento";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pagamentos.add(mapearPagamento(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }

    // READ - BUSCAR POR ID
    public Pagamento buscarPorId(int id) {

        String sql = "SELECT * FROM pagamento WHERE id_pagamento = ?";
        Pagamento pagamento = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pagamento = mapearPagamento(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamento;
    }

    // UPDATE
    public void atualizar(Pagamento pagamento) {

        String sql = """
            UPDATE pagamento
               SET metodo = ?,
                   valor_total = ?,
                   status = ?
             WHERE id_pagamento = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pagamento.getMetodo());
            stmt.setBigDecimal(2, pagamento.getValorTotalBD() != null ? pagamento.getValorTotalBD() : 
                                   java.math.BigDecimal.valueOf(pagamento.getValorTotal()));
            stmt.setString(3, pagamento.getStatus());
            stmt.setInt(4, pagamento.getIdPagamento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM pagamento WHERE id_pagamento = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================
    private Pagamento mapearPagamento(ResultSet rs) throws SQLException {

        Pagamento p = new Pagamento();
        p.setIdPagamento(rs.getInt("id_pagamento"));
        p.setMetodo(rs.getString("metodo"));
        p.setValorTotal(rs.getBigDecimal("valor_total"));
        p.setStatus(rs.getString("status"));
        // timestamps são automáticos → não mapeados

        return p;
    }
}
