package dao;

import jdbc.ConnectionFactory;
import petsec_senac.ItemPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    // CREATE
    public void inserir(ItemPedido item) {

        String sql = """
            INSERT INTO item_pedido
            (id_pedido, id_produto, quantidade, preco_unitario)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, item.getIdPedido());
            stmt.setInt(2, item.getIdProduto());
            stmt.setInt(3, item.getQuantidade());
            stmt.setBigDecimal(4, item.getPrecoUnitarioBD() != null ? item.getPrecoUnitarioBD() : 
                                   java.math.BigDecimal.valueOf(item.getPrecoUnitario()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR ITENS DE UM PEDIDO
    public List<ItemPedido> listarPorPedido(int idPedido) {

        List<ItemPedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_pedido WHERE id_pedido = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemPedido item = new ItemPedido();
                item.setIdPedido(rs.getInt("id_pedido"));
                item.setIdProduto(rs.getInt("id_produto"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));

                itens.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }

    // UPDATE
    public void atualizar(ItemPedido item) {

        String sql = """
            UPDATE item_pedido
               SET quantidade = ?,
                   preco_unitario = ?
             WHERE id_pedido = ?
               AND id_produto = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, item.getQuantidade());
            stmt.setBigDecimal(2, item.getPrecoUnitarioBD() != null ? item.getPrecoUnitarioBD() : 
                                   java.math.BigDecimal.valueOf(item.getPrecoUnitario()));
            stmt.setInt(3, item.getIdPedido());
            stmt.setInt(4, item.getIdProduto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - ITEM ESPECÍFICO
    public void remover(int idPedido, int idProduto) {

        String sql = """
            DELETE FROM item_pedido
             WHERE id_pedido = ?
               AND id_produto = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - TODOS OS ITENS DO PEDIDO
    public void removerPorPedido(int idPedido) {

        String sql = "DELETE FROM item_pedido WHERE id_pedido = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
