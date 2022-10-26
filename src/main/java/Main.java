import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Scanner;
import java.io.*;

//После ввода каждой покупки пользователем вам следует сохранять пользовательскую корзину в файл basket.txt.
//При старте программа должна искать этот файл в корне проекта и если он находится, восстанавливать корзину из него;
//если файл не найдет, то стоит начать с пустой корзины.
public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Basket basket = null;
        boolean load = false;
        boolean save = false;
        boolean log = false;
        String cartLoadFileName = "";
        String cartSaveFileName = "";
        String logFileName = "";
        String saveFormat = "";
        String loadFormat = "";
        File cartLoadFile = null;
        File cartSaveFile = null;
        File logFile = null;

        Product[] products = new Product[7];
        products[0] = new Product("Хлеб", 50);
        products[1] = new Product("Яблоки", 250);
        products[2] = new Product("Молоко", 100);
        products[3] = new Product("Мясо", 500);
        products[4] = new Product("Рыба", 400);
        products[5] = new Product("Печенье", 200);
        products[6] = new Product("Пиво", 150);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            switch (currentNode.getNodeName()) {
                case "load": {
                    Element element = (Element) currentNode;
                    load = Boolean.parseBoolean((element.getElementsByTagName("enabled").item(0).getTextContent()));
                    cartLoadFileName = (element.getElementsByTagName("fileName").item(0).getTextContent());
                    loadFormat = (element.getElementsByTagName("format").item(0).getTextContent());
                    break;
                }
                case "save": {
                    Element element = (Element) currentNode;
                    save = Boolean.parseBoolean((element.getElementsByTagName("enabled").item(0).getTextContent()));
                    cartSaveFileName = (element.getElementsByTagName("fileName").item(0).getTextContent());
                    saveFormat = (element.getElementsByTagName("format").item(0).getTextContent());
                    break;
                }
                case "log": {
                    Element element = (Element) currentNode;
                    log = Boolean.parseBoolean((element.getElementsByTagName("enabled").item(0).getTextContent()));
                    logFileName = (element.getElementsByTagName("fileName").item(0).getTextContent());
                    break;
                }
            }
        }

        if (load) {
            cartLoadFile = new File(cartLoadFileName);
        }
        if (save) {
            cartSaveFile = new File(cartSaveFileName);
        }
        if (log) {
            logFile = new File(logFileName);
        }

        if (load && cartLoadFile.exists()) {
            if (loadFormat.equals("json")) {
                basket = Basket.loadFromJsonFile(cartLoadFile);
            } else if (loadFormat.equals("txt")) {
                basket = Basket.loadFromTxtFile(cartLoadFile);
            }
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
                assert basket != null;
                basket.printCart();
                if (log) {
                    try {
                        basket.clientLog.exportAsCSV(logFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            String[] parts = input.split(" ");
            assert basket != null;
            basket.addToCart(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            if (save) {
                if (saveFormat.equals("json")) {
                    basket.saveJson(cartSaveFile);
                } else if (saveFormat.equals("txt")) {
                    basket.saveTxt(cartSaveFile);
                }
            }
        }
    }
}