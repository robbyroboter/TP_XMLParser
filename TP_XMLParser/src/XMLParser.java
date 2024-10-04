import java.io.BufferedReader; // Импорт для чтения файла построчно
import java.io.FileReader; // Импорт для чтения файла из файловой системы
import java.util.ArrayList; // Импорт для создания списка книг
import java.util.List; // Импорт для использования интерфейса List

public class XMLParser {
    public static void main(String[] args) {
        String filePath = "src/random_structure_11.xml"; // Путь к XML-файлу
        List<Book> books = parseXML(filePath); // Вызов метода для парсинга XML-файла и сохранение списка книг
    }

    public static List<Book> parseXML(String filePath) {
        List<Book> books = new ArrayList<>(); // Список для хранения объектов Book
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // Открытие файла для чтения
            String line; // Переменная для хранения текущей строки
            Book book = null; // Переменная для текущей книги
            Review review = null; // Переменная для текущего отзыва
            Publisher publisher = null; // Переменная для текущего издателя
            Address address = null; // Переменная для текущего адреса
            while ((line = br.readLine()) != null) { // Чтение файла построчно
                String[] lines = (line.replaceAll("><", ">\n<")).split("\n"); // Разделение строк между тегами для их отдельной обработки
                for (String lines_item : lines) { // Обработка каждой строки в массиве
                    if (lines_item.startsWith("<book ")) {
                        book = new Book(); // Создание нового объекта Book
                        book.setId(Integer.parseInt(lines_item.replaceAll("[^0-9]", ""))); // Установка ID книги
                    } else if (lines_item.startsWith("<title>")) {
                        book.setTitle(extractValue(lines_item, "title")); // Установка названия книги
                    } else if (lines_item.startsWith("<author>")) {
                        book.setAuthor(extractValue(lines_item, "author")); // Установка автора книги
                    } else if (lines_item.startsWith("<year>")) {
                        book.setYear(Integer.parseInt(extractValue(lines_item, "year"))); // Установка года выпуска
                    } else if (lines_item.startsWith("<genre>")) {
                        book.setGenre(extractValue(lines_item, "genre")); // Установка жанра книги
                    } else if (lines_item.startsWith("<price")) {
                        Price price = new Price(); // Создание нового объекта Price
                        price.setCurrency(extractAttribute(lines_item, "currency")); // Установка валюты цены
                        price.setAmount(Double.parseDouble(extractValue(lines_item, "price"))); // Установка суммы цены
                        book.setPrice(price); // Установка объекта Price в Book
                    } else if (lines_item.startsWith("<isbn>")) {
                        book.setIsbn(extractValue(lines_item, "isbn")); // Установка ISBN
                    } else if (lines_item.startsWith("<translator>")) {
                        book.setTranslator(extractValue(lines_item, "translator")); // Установка переводчика книги
                    } else if (lines_item.startsWith("<award>")) {
                        book.getAwards().add(extractValue(lines_item, "award")); // Добавление награды в список наград книги
                    } else if (lines_item.startsWith("<review>")) {
                        review = new Review(); // Создание нового объекта Review
                    } else if (lines_item.startsWith("<user>")) {
                        review.setUser(extractValue(lines_item, "user")); // Установка пользователя в отзыве
                    } else if (lines_item.startsWith("<rating>")) {
                        review.setRating(Integer.parseInt(extractValue(lines_item, "rating"))); // Установка рейтинга в отзыве
                    } else if (lines_item.startsWith("<comment>")) {
                        review.setComment(extractValue(lines_item, "comment")); // Установка комментария в отзыве
                    } else if (lines_item.startsWith("<language>")) {
                        book.setLanguage(extractValue(lines_item, "language")); // Установка языка книги
                    } else if (lines_item.startsWith("<publisher>")) {
                        publisher = new Publisher(); // Создание нового объекта Publisher
                    } else if (lines_item.startsWith("<name>")) {
                        publisher.setName(extractValue(lines_item, "name")); // Установка имени издателя
                    } else if (lines_item.startsWith("<address>")) {
                        address = new Address(); // Создание нового объекта Address
                    } else if (lines_item.startsWith("<city>")) {
                        address.setCity(extractValue(lines_item, "city")); // Установка города в адресе
                    } else if (lines_item.startsWith("<country>")) {
                        address.setCountry(extractValue(lines_item, "country")); // Установка страны в адресе
                    } else if (lines_item.startsWith("<format>")) {
                        book.setFormat(extractValue(lines_item, "format")); // Установка формата книги
                    } else if (lines_item.startsWith("</address>")) {
                        publisher.setAddress(address); // Добавление адреса к издателю
                    } else if (lines_item.startsWith("</publisher>")) {
                        book.setPublisher(publisher); // Добавление издателя к книге
                    } else if (lines_item.startsWith("</book>")) {
                        books.add(book); // Добавление книги в список
                        System.out.println("Книга добавлена: " + book); // Вывод информации о добавленной книге
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Вывод ошибки, если чтение файла не удалось
        }
        return books; // Возвращение списка книг
    }

    // Метод для извлечения значения из тега
    private static String extractValue(String line, String tag) {
        return line.substring(line.indexOf(">") + 1, line.lastIndexOf("<")); // Извлечение значения между тегами
    }

    // Метод для извлечения значения атрибута
    private static String extractAttribute(String line, String attribute) {
        int start = line.indexOf(attribute + "=\"") + attribute.length() + 2; // Определение начала значения атрибута
        int end = line.indexOf("\"", start); // Определение конца значения атрибута
        return line.substring(start, end); // Возврат значения атрибута
    }
}

// Класс для хранения информации о книге
class Book {
    private int id; // ID книги
    private String title; // Название книги
    private String author; // Автор книги
    private int year; // Год выпуска книги
    private String genre; // Жанр книги
    private Price price; // Цена книги
    private String isbn; // ISBN книги
    private String translator; // Переводчик книги
    private List<String> awards = new ArrayList<>(); // Список наград книги
    private List<Review> reviews = new ArrayList<>(); // Список отзывов о книге
    private String language; // Язык книги
    private Publisher publisher; // Издатель книги
    private String format; // Формат книги

    // Методы доступа и установки для каждого поля
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", isbn='" + isbn + '\'' +
                ", translator='" + translator + '\'' +
                ", awards=" + awards +
                ", reviews=" + reviews +
                ", language='" + language + '\'' +
                ", publisher=" + publisher +
                ", format='" + format + '\'' +
                '}';
    }
}

// Класс для хранения информации о цене
class Price {
    private String currency; // Валюта
    private double amount; // Сумма

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}

// Класс для хранения информации об отзыве
class Review {
    private String user; // Имя пользователя, оставившего отзыв
    private int rating; // Рейтинг отзыва
    private String comment; // Комментарий отзыва

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "user='" + user + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}

// Класс для хранения информации об издателе
class Publisher {
    private String name; // Название издательства
    private Address address; // Адрес издательства

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}

// Класс для хранения информации об адресе
class Address {
    private String city; // Город
    private String country; // Страна

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
