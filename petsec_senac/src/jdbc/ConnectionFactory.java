package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory{

    private static final String URL =
        "jdbc:mysql://localhost:3306/petsec_senac?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "gabi5491";

    public static Connection getConnection() {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Driver MySQL não encontrado: " + ex.getMessage());
            }
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    // Apenas para testes
    public static void main(String[] args) {
        try {
            System.out.println("Testando conexão...");
            Connection con = getConnection();
            System.out.println("Conectado com sucesso!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
