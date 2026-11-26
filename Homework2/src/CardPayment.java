/** Strategy pattern: concrete strategy 1 (bank card, 2% fee). */
public class CardPayment implements PaymentStrategy {
    private static final double FEE_RATE = 0.02;

    @Override
    public double pay(double amount) {
        double total = amount * (1 + FEE_RATE);
        System.out.printf("   Charging bank card: %.2f (includes 2%% card fee)%n", total);
        return total;
    }

    @Override
    public String getName() { return "Bank Card"; }
}
