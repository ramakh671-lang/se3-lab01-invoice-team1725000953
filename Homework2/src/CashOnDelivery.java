/** Strategy pattern: concrete strategy 2 (cash on delivery, fixed fee). */
public class CashOnDelivery implements PaymentStrategy {
    private static final double HANDLING_FEE = 3.0;

    @Override
    public double pay(double amount) {
        double total = amount + HANDLING_FEE;
        System.out.printf("   Cash on delivery: %.2f to be collected (includes %.2f handling fee)%n", total, HANDLING_FEE);
        return total;
    }

    @Override
    public String getName() { return "Cash On Delivery"; }
}
