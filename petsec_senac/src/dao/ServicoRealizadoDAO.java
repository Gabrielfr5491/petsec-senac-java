package dao;

import jdbc.ConnectionFactory;
import petsec_senac.ServicoRealizado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoRealizadoDAO {

    // CREATE
    public void inserir(ServicoRealizado sr) {

        String sql = """
            INSERT INTO servico_realizado
            (id_pedido, id_servico, descricao, valor, id_pet, funcionario_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt =
                 con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sr.getIdPedido());

            if (sr.getIdServico() != null)
                stmt.setInt(2, sr.getIdServico());
            else
                stmt.setNull(2, Types.INTEGER);

            stmt.setString(3, sr.getDescricao());
            stmt.setBigDecimal(4, sr.getValorBD() != null ? sr.getValorBD() : 
                                   java.math.BigDecimal.valueOf(sr.getValor()));

            if (sr.getIdPet() != null)
                stmt.setInt(5, sr.getIdPet());
            else
                stmt.setNull(5, Types.INTEGER);

            if (sr.getFuncionarioId() != null)
                stmt.setInt(6, sr.getFuncionarioId());
            else
                stmt.setNull(6, Types.INTEGER);

            stmt.executeUpdate();

            // recupera ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sr.setIdServicoRealizado(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR POR PEDIDO
    public List<ServicoRealizado> listarPorPedido(int idPedido) {

        List<ServicoRealizado> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico_realizado WHERE id_pedido = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearServicoRealizado(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // UPDATE
    public void atualizar(ServicoRealizado sr) {

        String sql = """
            UPDATE servico_realizado
               SET descricao = ?,
                   valor = ?,
                   funcionario_id = ?
             WHERE id_servico_realizado = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, sr.getDescricao());
            stmt.setBigDecimal(2, sr.getValorBD() != null ? sr.getValorBD() : 
                                   java.math.BigDecimal.valueOf(sr.getValor()));

            if (sr.getFuncionarioId() != null)
                stmt.setInt(3, sr.getFuncionarioId());
            else
                stmt.setNull(3, Types.INTEGER);

            stmt.setInt(4, sr.getIdServicoRealizado());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - UM SERVIÇO REALIZADO
    public void remover(int idServicoRealizado) {

        String sql = "DELETE FROM servico_realizado WHERE id_servico_realizado = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idServicoRealizado);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - TODOS DO PEDIDO
    public void removerPorPedido(int idPedido) {

        String sql = "DELETE FROM servico_realizado WHERE id_pedido = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================
    private ServicoRealizado mapearServicoRealizado(ResultSet rs) throws SQLException {

        ServicoRealizado sr = new ServicoRealizado();
        sr.setIdServicoRealizado(rs.getInt("id_servico_realizado"));
        sr.setDescricao(rs.getString("descricao"));
        sr.setValor(rs.getBigDecimal("valor"));

        sr.setIdPedido(rs.getInt("id_pedido"));

        int idServico = rs.getInt("id_servico");
        if (!rs.wasNull()) sr.setIdServico(idServico);

        int idPet = rs.getInt("id_pet");
        if (!rs.wasNull()) sr.setIdPet(idPet);

        int funcionarioId = rs.getInt("funcionario_id");
        if (!rs.wasNull()) sr.setFuncionarioId(funcionarioId);

        return sr;
    }
}
