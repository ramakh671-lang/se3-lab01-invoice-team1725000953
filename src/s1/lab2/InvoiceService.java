package s1.lab2;

public class InvoiceService {
    private final InvoiceCalculator calculator;
    private final InvoiceFormatter formatter;
    private final InvoiceStorage storage;
    private final InvoiceSender sender;

    public InvoiceService(InvoiceCalculator calculator, InvoiceFormatter formatter,
                          InvoiceStorage storage, InvoiceSender sender) {
        this.calculator = calculator;
        this.formatter = formatter;
        this.storage = storage;
        this.sender = sender;
    }

    public String printInvoice(Invoice invoice) {
        String text = formatter.format(invoice, calculator.calculateTotal(invoice));
        System.out.println(text);
        return text;
    }

    public void saveInvoice(Invoice invoice) {
        storage.save(invoice, calculator.calculateTotal(invoice));
    }

    public String sendInvoice(Invoice invoice) {
        String text = printInvoice(invoice);   // تطبع النص كما في القديم
        return sender.send(invoice, text);     // تطبع "Sending ..." وتُرجع الرسالة دون طباعتها
    }
}