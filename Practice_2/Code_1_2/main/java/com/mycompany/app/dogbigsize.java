public class Dog {
    public void bark() {
        System.out.println("Gau");
    }
    public void bark(String str) {
        System.out.println(str);
    }    
    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.bark();
        myDog.bark("Woof!"); 
    }
}
