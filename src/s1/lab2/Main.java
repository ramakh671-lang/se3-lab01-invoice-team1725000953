```java
package s1.lab2;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        
        InvoiceCalculator calc = new InvoiceCalculator();
        InvoiceFormatter fmt = new InvoiceFormatter();

        
        InvoiceStorage db = new DatabaseStorage();
        InvoiceStorage files = new FileStorage();

        
        InvoiceSender email = new EmailSender();
        InvoiceSender whatsapp = new WhatsAppSender();

        
        InvoiceService dbEmail =
                new InvoiceService(calc, fmt, db, email);

        InvoiceService dbWhatsapp =
                new InvoiceService(calc, fmt, db, whatsapp);

        InvoiceService filesEmail =
                new InvoiceService(calc, fmt, files, email);

        InvoiceService filesWhatsapp =
                new InvoiceService(calc, fmt, files, whatsapp);

       
        Invoice inv1 = new Invoice(1, "lina@uni.edu");
        inv1.addItem("Book", 100, 2);

        dbEmail.printInvoice(inv1);

        dbEmail.saveInvoice(inv1);
        System.out.println("DATABASE: " + DatabaseStorage.DATABASE);

        dbEmail.sendInvoice(inv1);
        dbWhatsapp.sendInvoice(inv1);

        System.out.println("Total: " +
                String.format(Locale.US, "%.2f",
                        calc.calculateTotal(inv1)));

        System.out.println();

        
        Invoice inv2 = new Invoice(2, "omar@uni.edu");
        inv2.addItem("Laptop", 1200, 1);

        filesEmail.printInvoice(inv2);

        filesEmail.saveInvoice(inv2);
        System.out.println("FILES: " + FileStorage.FILES);

        filesEmail.sendInvoice(inv2);
        filesWhatsapp.sendInvoice(inv2);

        System.out.println("Total: " +
                String.format(Locale.US, "%.2f",
                        calc.calculateTotal(inv2)));
    }
}
```
