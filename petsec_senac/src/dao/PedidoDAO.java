package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE
    public void inserir(Pedido pedido) {

        String sql = """
            INSERT INTO pedido
            (id_cliente, funcionario_id, id_pagamento, id_agendamento,
             data_pedido, status_pedido, valor_total, entregador_id, custo_entrega)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt =
                 con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (pedido.getIdCliente() != null)
                stmt.setInt(1, pedido.getIdCliente());
            else
                stmt.setNull(1, Types.INTEGER);

            if (pedido.getFuncionarioId() != null)
                stmt.setInt(2, pedido.getFuncionarioId());
            else
                stmt.setNull(2, Types.INTEGER);

            if (pedido.getIdPagamento() != null)
                stmt.setInt(3, pedido.getIdPagamento());
            else
                stmt.setNull(3, Types.INTEGER);

            if (pedido.getIdAgendamento() != null)
                stmt.setInt(4, pedido.getIdAgendamento());
            else
                stmt.setNull(4, Types.INTEGER);

            stmt.setTimestamp(5, pedido.getDataPedido());
            stmt.setString(6, pedido.getStatusPedido());
            stmt.setBigDecimal(7, pedido.getValorTotal());

            if (pedido.getEntregadorId() != null)
                stmt.setInt(8, pedido.getEntregadorId());
            else
                stmt.setNull(8, Types.INTEGER);

            stmt.setBigDecimal(9, pedido.getCustoEntrega());

            stmt.executeUpdate();

            // recupera ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setIdPedido(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Pedido> listar() {

        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    // READ - BUSCAR POR ID
    public Pedido buscarPorId(int id) {

        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        Pedido pedido = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pedido = mapearPedido(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedido;
    }

    // UPDATE - STATUS / VALORES
    public void atualizar(Pedido pedido) {

        String sql = """
            UPDATE pedido
               SET status_pedido = ?,
                   valor_total = ?,
                   id_pagamento = ?,
                   entregador_id = ?,
                   custo_entrega = ?
             WHERE id_pedido = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pedido.getStatusPedido());
            stmt.setBigDecimal(2, pedido.getValorTotal());

            if (pedido.getIdPagamento() != null)
                stmt.setInt(3, pedido.getIdPagamento());
            else
                stmt.setNull(3, Types.INTEGER);

            if (pedido.getEntregadorId() != null)
                stmt.setInt(4, pedido.getEntregadorId());
            else
                stmt.setNull(4, Types.INTEGER);

            stmt.setBigDecimal(5, pedido.getCustoEntrega());
            stmt.setInt(6, pedido.getIdPedido());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM pedido WHERE id_pedido = ?";

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
    private Pedido mapearPedido(ResultSet rs) throws SQLException {

        Pedido p = new Pedido();
        p.setIdPedido(rs.getInt("id_pedido"));
        p.setStatusPedido(rs.getString("status_pedido"));
        p.setValorTotal(rs.getBigDecimal("valor_total"));
        p.setCustoEntrega(rs.getBigDecimal("custo_entrega"));
        p.setDataPedido(rs.getTimestamp("data_pedido"));

        int idCliente = rs.getInt("id_cliente");
        if (!rs.wasNull()) p.setIdCliente(idCliente);

        int funcionarioId = rs.getInt("funcionario_id");
        if (!rs.wasNull()) p.setFuncionarioId(funcionarioId);

        int idPagamento = rs.getInt("id_pagamento");
        if (!rs.wasNull()) p.setIdPagamento(idPagamento);

        int idAgendamento = rs.getInt("id_agendamento");
        if (!rs.wasNull()) p.setIdAgendamento(idAgendamento);

        int entregadorId = rs.getInt("entregador_id");
        if (!rs.wasNull()) p.setEntregadorId(entregadorId);

        return p;
    }
}
