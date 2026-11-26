/**
 * Adapter pattern: the ADAPTEE.
 * Simulates a third-party library. Its interface is different from PaymentStrategy
 * and it MUST NOT be modified.
 */
public class ExternalPayPalLib {
    /** Executes a transaction and returns the total charged (includes a 3.5% PayPal fee). */
    public double makeTransaction(String currency, double value) {
        double total = value * 1.035;
        System.out.printf("   [PayPal Lib] Transaction of %.2f %s executed%n", total, currency);
        return total;
    }
}
