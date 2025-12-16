package dao;

import jdbc.ConnectionFactory;
import petsec_senac.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    // CREATE
    public void inserir(Pet pet) {

        String sql = """
            INSERT INTO pet (nome_pet, tipo, idade, id_cliente)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pet.getNomePet());
            stmt.setString(2, pet.getTipo());
            stmt.setInt(3, pet.getIdade());

            if (pet.getIdCliente() != null) {
                stmt.setInt(4, pet.getIdCliente());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - LISTAR TODOS
    public List<Pet> listar() {

        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pet p = new Pet();
                p.setIdPet(rs.getInt("id_pet"));
                p.setNomePet(rs.getString("nome_pet"));
                p.setTipo(rs.getString("tipo"));
                p.setIdade(rs.getInt("idade"));

                int idCliente = rs.getInt("id_cliente");
                if (!rs.wasNull()) {
                    p.setIdCliente(idCliente);
                }

                pets.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }

    // READ - BUSCAR POR ID
    public Pet buscarPorId(int id) {

        String sql = "SELECT * FROM pet WHERE id_pet = ?";
        Pet pet = null;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pet = new Pet();
                pet.setIdPet(rs.getInt("id_pet"));
                pet.setNomePet(rs.getString("nome_pet"));
                pet.setTipo(rs.getString("tipo"));
                pet.setIdade(rs.getInt("idade"));

                int idCliente = rs.getInt("id_cliente");
                if (!rs.wasNull()) {
                    pet.setIdCliente(idCliente);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pet;
    }

    // READ - LISTAR POR CLIENTE
    public List<Pet> listarPorCliente(int idCliente) {

        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet WHERE id_cliente = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pet p = new Pet();
                p.setIdPet(rs.getInt("id_pet"));
                p.setNomePet(rs.getString("nome_pet"));
                p.setTipo(rs.getString("tipo"));
                p.setIdade(rs.getInt("idade"));
                p.setIdCliente(idCliente);

                pets.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }

    // UPDATE
    public void atualizar(Pet pet) {

        String sql = """
            UPDATE pet
               SET nome_pet = ?,
                   tipo = ?,
                   idade = ?,
                   id_cliente = ?
             WHERE id_pet = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pet.getNomePet());
            stmt.setString(2, pet.getTipo());
            stmt.setInt(3, pet.getIdade());

            if (pet.getIdCliente() != null) {
                stmt.setInt(4, pet.getIdCliente());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, pet.getIdPet());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void remover(int id) {

        String sql = "DELETE FROM pet WHERE id_pet = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
