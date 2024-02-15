package uk.ac.york.student.example;

import lombok.Getter;
import lombok.Setter;

@Getter // at the top of the class, lombok will generate the getters for us for all fields
public class Lombok {
    private final String name;
    @Setter // lombok will generate the setter for us specifically for this field
    private int age;

    public Lombok(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void test() {
        System.out.println(getName()); // lombok creates the getters for us
        System.out.println(getAge());

        setAge(20); // lombok creates the setters for us
        System.out.println(getAge());
    }

    public void testUtility() {
        // Utility test = new Utility(); // error thrown here -- @UtilityClass creates a private constructor
        // ^^ code is commented out so the project can compile, remove the // if you want to see the error
    }

    public void methodNotCompleted() {
        // todo: this is an example todo
    }
}
