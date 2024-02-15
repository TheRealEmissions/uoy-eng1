package uk.ac.york.student.example;

public class Generic<T> {
    /**
     * This is a generic method that takes in a generic type and returns it
     * @param t
     * @return
     */
    public T test(T t) {
        return t;
    }

    public <U> U test2(U u) {
        return u;
    }

    public static void testGeneric() {
        Generic<String> generic = new Generic<>();
        System.out.println(generic.test("Hello world!"));

        Generic<Integer> generic2 = new Generic<>();
        System.out.println(generic2.test(5)); // requires integer input
        System.out.println(generic2.test2("hi")); // can be any input as the generic is defined on the method
    }
}
