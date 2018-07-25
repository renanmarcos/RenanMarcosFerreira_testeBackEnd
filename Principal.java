public class Principal {

    public static void main(String args[]) {

        BancoDeDados bd = new BancoDeDados(4200);

        System.out.println(String.format("R$ %.2f", bd.mediaDoValorTotal()));

    }

}
