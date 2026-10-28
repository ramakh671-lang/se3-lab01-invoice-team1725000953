package s1.lab2;

public class EmailSender implements InvoiceSender {
    @Override
    public String send(Invoice invoice, String text) {
        String message = "To: " + invoice.getCustomerEmail() + "\n"
                + "Subject: Invoice INV-" + invoice.getNumber() + "\n"
                + text;
        System.out.println("Sending email...");
        return message;
    }
}