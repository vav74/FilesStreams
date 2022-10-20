import java.io.*;
import java.util.ArrayList;
import java.util.List;

//После ввода каждой покупки пользователем вам следует сохранять пользовательскую корзину в файл basket.txt.
//При старте программа должна искать этот файл в корне проекта и если он находится, восстанавливать корзину из него;
//если файл не найдет, то стоит начать с пустой корзины.
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

    //статический(!) метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public static Basket loadFromTxtFile(File file) {
        List<Product> cartFromSavedFile = new ArrayList<>();
        String[] parts;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                parts = s.trim().split(" ");
                cartFromSavedFile.add(new Product(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new Basket(cartFromSavedFile.toArray(Product[]::new));
    }

    //Метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя;
//Если все данные у вас хранятся в массивах, то просто сохраните по массиву на каждой строке.
//Например, если бы это был лонговый массив в поле нашей корзины, то можно было бы сделать так:
    public void saveTxt(File file) {
        try (PrintWriter out = new PrintWriter(file)) {
            for (Product product : cart) {
                out.println(product.getName() + " " + product.getPrice() + " " + product.getAmount());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
//А при чтении вы сможете строку файла разбить через split(" ")и пройдясь циклом каждое
//значение превратить в long
}