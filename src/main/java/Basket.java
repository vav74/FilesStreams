import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Basket {
    protected Product[] cart;
    protected ClientLog clientLog;

    //Конструктор, принимающий массив цен и названий продуктов;
    public Basket(Product[] products) {
        this.cart = products;
        this.clientLog = new ClientLog();
    }

    //Метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        this.cart[productNum - 1].setAmount(this.cart[productNum - 1].getAmount() + amount);
        this.clientLog.log(productNum, amount);
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

    /*
      Также вместо вызова метода saveTxt в методе main сериализуйте корзину в json-формате в файл basket.json.
      Аналогично при старте программы загружайте корзину десериализацией из json-а из файла basket.json,
      а не из обычной текстовой сериализации как было до того.
      При этом логику сериализации в методах в классе корзины трогать не нужно.*/
    public void saveJson(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, cart);
    }

    public static Basket loadFromJsonFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product[] cartFromJson = mapper.readValue(file, new TypeReference<>() {
        });
        return new Basket(cartFromJson);
    }
}