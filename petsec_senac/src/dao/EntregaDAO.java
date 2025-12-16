package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    // CREATE
    public void inserir(Entrega entrega) {

        String sql = """
            INSERT INTO entrega
            (id_pedido, id_entregador, custo_entrega,
             data_envio, data_entrega_realizada, entrega_status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt =
                 con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entrega.getIdPedido());

            if (entrega.getIdEntregador() != null)
                stmt.setInt(2, entrega.getIdEntregador());
            else
                stmt.setNull(2, Types.INTEGER);

            stmt.setBigDecimal(3, entrega.getCustoEntregaBD() != null ? entrega.getCustoEntregaBD() : 
                                   java.math.BigDecimal.valueOf(entrega.getCustoEntrega()));

            if (entrega.getDataEnvioTS() != null)
                stmt.setTimestamp(4, entrega.getDataEnvioTS());
            else
                stmt.setNull(4, Types.TIMESTAMP);

            if (entrega.getDataEntregaRealizadaTS() != null)
                stmt.setTimestamp(5, entrega.getDataEntregaRealizadaTS());
            else
                stmt.setNull(5, Types.TIMESTAMP);

            stmt.setString(6, entrega.getEntregaStatus());

            stmt.executeUpdate();

            // recupera ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entrega.setIdEntrega(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODAS
    public List<Entrega> listar() {

        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM entrega";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                entregas.add(mapearEntrega(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entregas;
    }

    // READ - BUSCAR POR ID
    public Entrega buscarPorId(int id) {

        String sql = "SELECT * FROM entrega WHERE id_entrega = ?";
        Entrega entrega = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entrega = mapearEntrega(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entrega;
    }

    // READ - BUSCAR POR PEDIDO
    public Entrega buscarPorPedido(int idPedido) {

        String sql = "SELECT * FROM entrega WHERE id_pedido = ?";
        Entrega entrega = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entrega = mapearEntrega(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entrega;
    }

    // UPDATE
    public void atualizar(Entrega entrega) {

        String sql = """
            UPDATE entrega
               SET id_entregador = ?,
                   custo_entrega = ?,
                   data_envio = ?,
                   data_entrega_realizada = ?,
                   entrega_status = ?
             WHERE id_entrega = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (entrega.getIdEntregador() != null)
                stmt.setInt(1, entrega.getIdEntregador());
            else
                stmt.setNull(1, Types.INTEGER);

            stmt.setBigDecimal(2, entrega.getCustoEntregaBD() != null ? entrega.getCustoEntregaBD() : 
                                   java.math.BigDecimal.valueOf(entrega.getCustoEntrega()));

            if (entrega.getDataEnvioTS() != null)
                stmt.setTimestamp(3, entrega.getDataEnvioTS());
            else
                stmt.setNull(3, Types.TIMESTAMP);

            if (entrega.getDataEntregaRealizadaTS() != null)
                stmt.setTimestamp(4, entrega.getDataEntregaRealizadaTS());
            else
                stmt.setNull(4, Types.TIMESTAMP);

            stmt.setString(5, entrega.getEntregaStatus());
            stmt.setInt(6, entrega.getIdEntrega());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM entrega WHERE id_entrega = ?";

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
    private Entrega mapearEntrega(ResultSet rs) throws SQLException {

        Entrega e = new Entrega();
        e.setIdEntrega(rs.getInt("id_entrega"));
        e.setIdPedido(rs.getInt("id_pedido"));
        e.setEntregaStatus(rs.getString("entrega_status"));
        e.setCustoEntrega(rs.getBigDecimal("custo_entrega"));

        Timestamp envio = rs.getTimestamp("data_envio");
        if (envio != null) e.setDataEnvio(envio);

        Timestamp realizada = rs.getTimestamp("data_entrega_realizada");
        if (realizada != null) e.setDataEntregaRealizada(realizada);

        int idEntregador = rs.getInt("id_entregador");
        if (!rs.wasNull()) e.setIdEntregador(idEntregador);

        return e;
    }
}
