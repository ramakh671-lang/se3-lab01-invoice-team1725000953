package s1.lab1;

public class Main {

    public static void main(String[] args) {


        Invoice inv1 = new Invoice(1, "lina@uni.edu");
        inv1.addItem("Book", 100, 2);

        inv1.printInvoice();

        inv1.saveToDatabase("DATABASE");
        System.out.println("DATABASE: " + Invoice.DATABASE);
        inv1.sendEmail();
        inv1.sendwhatsapp();

        System.out.println("Total: " +
                String.format("%.2f", inv1.calculateTotal()));

        System.out.println();


        Invoice inv2 = new Invoice(2, "omar@uni.edu");
        inv2.addItem("Laptop", 1200, 1);

        inv2.printInvoice();

        inv2.saveToDatabase("FILES");
        System.out.println("FILES: " + Invoice.FILES);

        inv2.sendEmail();
        inv2.sendwhatsapp();

        System.out.println("Total: " +
                String.format("%.2f", inv2.calculateTotal()));
    }
}