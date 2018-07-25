import java.sql.*;
import java.util.Random;

public class BancoDeDados {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String USUARIO = "root";
    private final String SENHA = "";
    private Connection conn;
    private Statement stmt;
    private int qtdRegistros;

    public BancoDeDados(int qtdRegistros) {
        if (qtdRegistros <= 2700)
            this.qtdRegistros = 2701;
        else if (qtdRegistros >= 9000)
            this.qtdRegistros = 8999;
        else
            this.qtdRegistros = qtdRegistros;

        criarConexao();
        verificarBancoExiste();
        fecharConexao();
    }

    private void verificarBancoExiste() {

        Boolean bancoExiste = false;

        try {

            ResultSet resultSet = conn.getMetaData().getCatalogs();

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);

                if (databaseName.equals("valemobi")) {
                    bancoExiste = true;
                    System.out.println("Banco já existe");
                    break;
                }
            }
            resultSet.close();

            if (!bancoExiste) {
                System.out.println("Banco inexistente, criando um...");
                criarBanco();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void criarConexao() {

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USUARIO, SENHA);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void fecharConexao() {

        try {
            if( stmt != null) stmt.close();
        } catch(SQLException se2) {
            se2.printStackTrace();
        }

        try{
            if( conn != null) conn.close();
        } catch(SQLException se){
            se.printStackTrace();
        }

    }

    private void criarBanco() {

        try {

            stmt = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS valemobi";
            stmt.executeUpdate(sql);

            conn.setCatalog("valemobi");
            stmt = conn.createStatement();

            sql = "CREATE TABLE IF NOT EXISTS tb_customer_account " +
                    "(id_customer INT(4) AUTO_INCREMENT PRIMARY KEY, " +
                    "cpf_cnpj VARCHAR(19) NOT NULL," +
                    "nm_customer VARCHAR(200) NOT NULL," +
                    "is_active INTEGER(1) DEFAULT 1," +
                    "vl_total DECIMAL(15,2) DEFAULT 0)";
            stmt.executeUpdate(sql);

            Random random = new Random();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO tb_customer_account(cpf_cnpj, " +
                    "nm_customer, vl_total) VALUES (?, ?, ?)");

            conn.setAutoCommit(false);
            for (int i = 0; i < qtdRegistros; i++) {

                double vl_total = 1 + random.nextDouble() * (10000 - 2);
                int cpf1 = random.nextInt(899) + 100;
                int cpf2 = random.nextInt(899) + 100;
                int cpf3 = random.nextInt(899) + 100;
                int cpf4 = random.nextInt(89) + 10;

                ps.setString(1,cpf1 + "." + cpf2 + "." + cpf3 + "-" + cpf4);
                ps.setString(2,"Joao " + i);
                ps.setDouble(3, vl_total);
                ps.addBatch();
            }

            ps.executeBatch();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public double mediaDoValorTotal() {

        double media = 0;
        int cont = 0;
        Cliente atual;

        criarConexao();

        try {
            conn.setCatalog("valemobi");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM tb_customer_account WHERE vl_total > 560 AND id_customer " +
                    "BETWEEN 1500 AND 2700 ORDER BY vl_total DESC";
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {

                atual = new Cliente(
                        resultSet.getString("nm_customer"),
                        resultSet.getDouble("vl_total")
                );

                System.out.println(atual.toString());
                media += atual.getVl_total();
                cont++;
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        fecharConexao();

        return media / cont;
    }

}
