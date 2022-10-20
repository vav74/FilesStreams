import java.io.*;

public class Basket {
    protected Product[] cart;

    //Конструктор, принимающий массив цен и названий продуктов;
    public Basket(Product[] products) {
        this.cart = products;
    }

    //Метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        this.cart[productNum - 1].setAmount(this.cart[productNum - 1].getAmount() + amount);
    }

    //метод вывода на экран покупательской корзины
    public void printCart() {
        int sumProducts = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < cart.length; i++) {
            if (cart[i].getAmount() != 0) {
                System.out.println((i + 1) + ". " + cart[i].getName() + " " + cart[i].getAmount() + " " + cart[i].getPrice() + " руб/шт " + cart[i].getAmount() * cart[i].getPrice() + " руб в сумме");
                sumProducts += cart[i].getAmount() * cart[i].getPrice();
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    public void saveBin(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(cart);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) {
        Product[] cartFromBin = new Product[0];
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            try {
                cartFromBin = (Product[]) in.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new Basket(cartFromBin);
    }
}