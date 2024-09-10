import AC1.IaCExercise1;

public class app {

    public static void main(String[] args){
        //Book
        Book book = new Book("2025", "SGK TIeng Viet", 2);
        System.out.println(book);

        //recursion
        Recursion recursion = new Recursion();
        long result = recursion.factorial(5);
        System.out.println("Factorial of 5 is: " + result);

        //Time
        Time t1 = new Time();
        Time t2 = new Time(20, 3, 45);
        t1.setHour(7).setMinute(32).setSecond(23);
        System.out.println("t1 is " + t1);
        System.out.println("t2 is " + t2);

        //NNCollection
        NNCollection nnCollection = new NNCollection();
        nnCollection.findNumber("George Orwell");
        nnCollection.findNumber("AnhPhap");
        System.out.println(nnCollection);

        //Ex1
        IaCExercise1 Ex1 = new IaCExercise1();
    }
}