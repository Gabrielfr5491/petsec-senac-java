package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // CREATE
    public void inserir(Produto produto) {

        String sql = """
            INSERT INTO produto
            (nome_produto, descricao, preco_produto, estoque, categoria_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPrecoProduto());
            stmt.setInt(4, produto.getEstoque());

            if (produto.getCategoriaId() != null) {
                stmt.setInt(5, produto.getCategoriaId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Produto> listar() {

        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setDescricao(rs.getString("descricao"));
                p.setPrecoProduto(rs.getBigDecimal("preco_produto"));
                p.setEstoque(rs.getInt("estoque"));

                int categoriaId = rs.getInt("categoria_id");
                if (!rs.wasNull()) {
                    p.setCategoriaId(categoriaId);
                }

                produtos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // READ - BUSCAR POR ID
    public Produto buscarPorId(int id) {

        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        Produto produto = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setNomeProduto(rs.getString("nome_produto"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPrecoProduto(rs.getBigDecimal("preco_produto"));
                produto.setEstoque(rs.getInt("estoque"));

                int categoriaId = rs.getInt("categoria_id");
                if (!rs.wasNull()) {
                    produto.setCategoriaId(categoriaId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    // UPDATE - DADOS DO PRODUTO
    public void atualizar(Produto produto) {

        String sql = """
            UPDATE produto
               SET nome_produto = ?,
                   descricao = ?,
                   preco_produto = ?,
                   estoque = ?,
                   categoria_id = ?
             WHERE id_produto = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPrecoProduto());
            stmt.setInt(4, produto.getEstoque());

            if (produto.getCategoriaId() != null) {
                stmt.setInt(5, produto.getCategoriaId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, produto.getIdProduto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE EXTRA - ATUALIZAR ESTOQUE
    public void atualizarEstoque(int idProduto, int novoEstoque) {

        String sql = "UPDATE produto SET estoque = ? WHERE id_produto = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, novoEstoque);
            stmt.setInt(2, idProduto);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
