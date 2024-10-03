import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public static void main(String[] args) {
        String filePath = "src/random_structure_11.xml";
        List<Book> books = parseXML(filePath);
    }

    public static List<Book> parseXML(String filePath) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Book book = null;
            Review review = null;
            Publisher publisher = null;
            Address address = null;
            while ((line = br.readLine()) != null) {
                String[] lines = (line.replaceAll("><", ">\n<")).split("\n");
                for (String lines_item : lines) {
                    if (lines_item.startsWith("<book ")) {
                        book = new Book();
                        book.setId(Integer.parseInt(lines_item.replaceAll("[^0-9]", "")));
                    } else if (lines_item.startsWith("<title>")) {
                        book.setTitle(extractValue(lines_item, "title"));
                    } else if (lines_item.startsWith("<author>")) {
                        book.setAuthor(extractValue(lines_item, "author"));
                    } else if (lines_item.startsWith("<year>")) {
                        book.setYear(Integer.parseInt(extractValue(lines_item, "year")));
                    } else if (lines_item.startsWith("<genre>")) {
                        book.setGenre(extractValue(lines_item, "genre"));
                    } else if (lines_item.startsWith("<price")) {
                        Price price = new Price();
                        price.setCurrency(extractAttribute(lines_item, "currency"));
                        price.setAmount(Double.parseDouble(extractValue(lines_item, "price")));
                        book.setPrice(price);
                    } else if (lines_item.startsWith("<isbn>")) {
                        book.setIsbn(extractValue(lines_item, "isbn"));
                    } else if (lines_item.startsWith("<translator>")) {
                        book.setTranslator(extractValue(lines_item, "translator"));
                    } else if (lines_item.startsWith("<award>")) {
                        book.getAwards().add(extractValue(lines_item, "award"));
                    } else if (lines_item.startsWith("<review>")) {
                        review = new Review();
                    } else if (lines_item.startsWith("<user>")) {
                        review.setUser(extractValue(lines_item, "user"));
                    } else if (lines_item.startsWith("<rating>")) {
                        review.setRating(Integer.parseInt(extractValue(lines_item, "rating")));
                    } else if (lines_item.startsWith("<comment>")) {
                        review.setComment(extractValue(lines_item, "comment"));
                    } else if (lines_item.startsWith("<language>")) {
                        book.setLanguage(extractValue(lines_item, "language"));
                    } else if (lines_item.startsWith("<publisher>")) {
                        publisher = new Publisher();
                    } else if (lines_item.startsWith("<name>")) {
                        publisher.setName(extractValue(lines_item, "name"));
                    } else if (lines_item.startsWith("<address>")) {
                        address = new Address();
                    }else if (lines_item.startsWith("<city>")) {
                        address.setCity(extractValue(lines_item, "city"));
                    } else if (lines_item.startsWith("<country>")) {
                        address.setCountry(extractValue(lines_item, "country"));
                    } else if (lines_item.startsWith("<format>")) {
                        book.setFormat(extractValue(lines_item, "format"));
                    } else if (lines_item.startsWith("</address>")) {
                        publisher.setAddress(address);
                    } else if (lines_item.startsWith("</publisher>")) {
                        book.setPublisher(publisher);
                    } else if (lines_item.startsWith("</book>")) {
                        books.add(book);
                        System.out.println("Книга добавлена: " + book);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private static String extractValue(String line, String tag) {
        return line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
    }

    private static String extractAttribute(String line, String attribute) {
        int start = line.indexOf(attribute + "=\"") + attribute.length() + 2;
        int end = line.indexOf("\"", start);
        return line.substring(start, end);
    }
}

class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private String genre;
    private Price price;
    private String isbn;
    private String translator;
    private List<String> awards = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private String language;
    private Publisher publisher;
    private String format;

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

class Price {
    private String currency;
    private double amount;

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

class Review {
    private String user;
    private int rating;
    private String comment;

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

class Publisher {
    private String name;
    private Address address;

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

class Address {
    private String city;
    private String country;

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
