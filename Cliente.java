public class Cliente {

    private String nm_customer;
    private Double vl_total;

    public Cliente(String nm_customer, Double vl_total) {
        this.nm_customer = nm_customer;
        this.vl_total = vl_total;
    }

    @Override
    public String toString() {
        return "Cliente: " + nm_customer + " - Saldo: R$ " + vl_total;
    }

    public Double getVl_total() {
        return vl_total;
    }
}
