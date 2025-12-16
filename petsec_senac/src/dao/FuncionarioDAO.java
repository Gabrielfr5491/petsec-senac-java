package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // CREATE
    public void inserir(Funcionario funcionario) {

        String sql = """
            INSERT INTO funcionario (nome_funcionário, funcao, administrador)
            VALUES (?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getFuncao());
            stmt.setBoolean(3, funcionario.isAdministrador());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Funcionario> listar() {

        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setNomeFuncionario(rs.getString("nome_funcionário"));
                f.setFuncao(rs.getString("funcao"));
                f.setAdministrador(rs.getBoolean("administrador"));

                funcionarios.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }

    // READ - BUSCAR POR ID
    public Funcionario buscarPorId(int id) {

        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        Funcionario funcionario = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setNomeFuncionario(rs.getString("nome_funcionário"));
                funcionario.setFuncao(rs.getString("funcao"));
                funcionario.setAdministrador(rs.getBoolean("administrador"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionario;
    }

    // UPDATE
    public void atualizar(Funcionario funcionario) {

        String sql = """
            UPDATE funcionario
               SET nome_funcionário = ?,
                   funcao = ?,
                   administrador = ?
             WHERE id_funcionario = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getFuncao());
            stmt.setBoolean(3, funcionario.isAdministrador());
            stmt.setInt(4, funcionario.getIdFuncionario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
