import java.util.Scanner;
import java.io.*;

//После ввода каждой покупки пользователем вам следует сохранять пользовательскую корзину в файл basket.txt.
//При старте программа должна искать этот файл в корне проекта и если он находится, восстанавливать корзину из него;
//если файл не найдет, то стоит начать с пустой корзины.
public class Main {
    public static void main(String[] args) throws IOException {
        Basket basket;
        Product[] products = new Product[7];
        products[0] = new Product("Хлеб", 50);
        products[1] = new Product("Яблоки", 250);
        products[2] = new Product("Молоко", 100);
        products[3] = new Product("Мясо", 500);
        products[4] = new Product("Рыба", 400);
        products[5] = new Product("Печенье", 200);
        products[6] = new Product("Пиво", 150);
        File file = new File("basket.json");
        File logFile = new File("log.csv");

        if (file.exists()) {
            basket = Basket.loadFromJsonFile(file);
        } else {
            basket = new Basket(products);
        }
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i].getName() + " " + products[i].getPrice() + " руб/шт");
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.printCart();
                try {
                    basket.clientLog.exportAsCSV(logFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            String[] parts = input.split(" ");
            basket.addToCart(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            basket.saveJson(file);
        }
    }
}