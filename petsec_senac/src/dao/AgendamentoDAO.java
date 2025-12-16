package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Agendamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    // CREATE
    public void inserir(Agendamento ag) {

        String sql = """
            INSERT INTO agendamento
            (id_cliente, id_pet, data_agendamento, horario, descricao, gera_pedido, funcionario_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (ag.getIdCliente() != null)
                stmt.setInt(1, ag.getIdCliente());
            else
                stmt.setNull(1, Types.INTEGER);

            if (ag.getIdPet() != null)
                stmt.setInt(2, ag.getIdPet());
            else
                stmt.setNull(2, Types.INTEGER);

            stmt.setDate(3, ag.getDataAgendamento());
            stmt.setString(4, ag.getHorario());
            stmt.setString(5, ag.getDescricao());
            stmt.setBoolean(6, ag.isGeraPedido());

            if (ag.getFuncionarioId() != null)
                stmt.setInt(7, ag.getFuncionarioId());
            else
                stmt.setNull(7, Types.INTEGER);

            stmt.executeUpdate();

            // recupera ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                ag.setIdAgendamento(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Agendamento> listar() {

        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamento";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Agendamento ag = mapearAgendamento(rs);
                lista.add(ag);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // READ - BUSCAR POR ID
    public Agendamento buscarPorId(int id) {

        String sql = "SELECT * FROM agendamento WHERE id_agendamento = ?";
        Agendamento ag = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ag = mapearAgendamento(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ag;
    }

    // READ - LISTAR POR CLIENTE
    public List<Agendamento> listarPorCliente(int idCliente) {

        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamento WHERE id_cliente = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearAgendamento(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // UPDATE
    public void atualizar(Agendamento ag) {

        String sql = """
            UPDATE agendamento
               SET id_cliente = ?,
                   id_pet = ?,
                   data_agendamento = ?,
                   horario = ?,
                   descricao = ?,
                   gera_pedido = ?,
                   funcionario_id = ?
             WHERE id_agendamento = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (ag.getIdCliente() != null)
                stmt.setInt(1, ag.getIdCliente());
            else
                stmt.setNull(1, Types.INTEGER);

            if (ag.getIdPet() != null)
                stmt.setInt(2, ag.getIdPet());
            else
                stmt.setNull(2, Types.INTEGER);

            stmt.setDate(3, ag.getDataAgendamento());
            stmt.setString(4, ag.getHorario());
            stmt.setString(5, ag.getDescricao());
            stmt.setBoolean(6, ag.isGeraPedido());

            if (ag.getFuncionarioId() != null)
                stmt.setInt(7, ag.getFuncionarioId());
            else
                stmt.setNull(7, Types.INTEGER);

            stmt.setInt(8, ag.getIdAgendamento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM agendamento WHERE id_agendamento = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================
    // N:N — AGENDAMENTO_SERVICO
    // ============================

    public void adicionarServico(int idAgendamento, int idServico) {

        String sql = """
            INSERT INTO agendamento_servico (id_agendamento, id_servico)
            VALUES (?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idAgendamento);
            stmt.setInt(2, idServico);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerServicosDoAgendamento(int idAgendamento) {

        String sql = "DELETE FROM agendamento_servico WHERE id_agendamento = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idAgendamento);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================
    private Agendamento mapearAgendamento(ResultSet rs) throws SQLException {

        Agendamento ag = new Agendamento();
        ag.setIdAgendamento(rs.getInt("id_agendamento"));
        ag.setDataAgendamento(rs.getDate("data_agendamento"));
        ag.setHorario(rs.getString("horario"));
        ag.setDescricao(rs.getString("descricao"));
        ag.setGeraPedido(rs.getBoolean("gera_pedido"));

        int idCliente = rs.getInt("id_cliente");
        if (!rs.wasNull()) ag.setIdCliente(idCliente);

        int idPet = rs.getInt("id_pet");
        if (!rs.wasNull()) ag.setIdPet(idPet);

        int funcionarioId = rs.getInt("funcionario_id");
        if (!rs.wasNull()) ag.setFuncionarioId(funcionarioId);

        return ag;
    }
}
