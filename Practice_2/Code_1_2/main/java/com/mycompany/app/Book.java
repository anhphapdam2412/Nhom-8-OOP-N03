public class Book {
 String title;
 String author;
 int numPages;
 Book() { } ; // default constructor
 public Book(String t, String a, int p) {
 title = t;
 author = a;
 numPages = p;
 }
 public static void main(String[] args) {
 Book myObj = new Book("a","b",2); 
 System.out.println(myObj.title);
 }
}
class Book {
 String title;
 String author;
 int numPages;
 Book() { } 
 Book(String t, String a, int p) {
 title = t;
 author = a;
 numPages = p;
}
