package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // CREATE
    public void inserir(Cliente cliente) {

        String sql = """
            INSERT INTO cliente (nome_cliente, email, data_nascimento, endereco)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEmail());
            stmt.setDate(3, cliente.getDataNascimentoDate() != null ? cliente.getDataNascimentoDate() : null);
            stmt.setString(4, cliente.getEndereco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR
    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNomeCliente(rs.getString("nome_cliente"));
                c.setEmail(rs.getString("email"));
                c.setDataNascimento(rs.getDate("data_nascimento"));
                c.setEndereco(rs.getString("endereco"));

                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    // READ - BUSCAR POR ID
    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNomeCliente(rs.getString("nome_cliente"));
                cliente.setEmail(rs.getString("email"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setEndereco(rs.getString("endereco"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    // UPDATE
    public void atualizar(Cliente cliente) {

        String sql = """
            UPDATE cliente
               SET nome_cliente = ?,
                   email = ?,
                   data_nascimento = ?,
                   endereco = ?
             WHERE id_cliente = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEmail());
            stmt.setDate(3, cliente.getDataNascimentoDate() != null ? cliente.getDataNascimentoDate() : null);
            stmt.setString(4, cliente.getEndereco());
            stmt.setInt(5, cliente.getIdCliente());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
