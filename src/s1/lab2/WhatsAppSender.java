package s1.lab2;

public class WhatsAppSender implements InvoiceSender {
    @Override
    public String send(Invoice invoice, String text) {
        String message = "To: sendwhatsapp\n"
                + "Subject: Invoice INV-" + invoice.getNumber() + "\n"
                + text;
        System.out.println("Sending sendwhatsapp...");
        return message;
    }
}