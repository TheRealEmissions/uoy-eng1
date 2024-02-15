package uk.ac.york.student.example;

/**
 * This is a class that takes in a generic type T
 * Classes can be generic, methods can be generic, and interfaces can be generic
 * Keep in mind that the generic type is not a type, but a placeholder for a type
 * This means that the generic type can be any type
 * You can enforce a generic type to be a specific type by using the extends keyword and the type you want to enforce, for example T extends List
 *
 * Static methods can't use the generic type of the class, but they can have their own generic type
 * This is because the generic type is associated with the instance of the class, and static methods are not associated with an instance of the class
 * @param <T>
 */
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

    public <U extends String> U test3(U u) {
        return (U) u.toUpperCase();
    }

    public static void testGeneric() {
        Generic<String> generic = new Generic<>();
        System.out.println(generic.test("Hello world!"));

        Generic<Integer> generic2 = new Generic<>();
        System.out.println(generic2.test(5)); // requires integer input
        System.out.println(generic2.test2("hi")); // can be any input as the generic is defined on the method
        System.out.println(generic2.test3("hi")); // requires string inputc
    }
}
