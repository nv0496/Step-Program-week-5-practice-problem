// File: AccessModifierDemo.java
package com.company.security;

public class AccessModifierDemo {
    // Fields with different access modifiers
    private int privateField;           // Only accessible within this class
              String defaultField;      // Accessible within the same package
    protected double protectedField;    // Accessible in the same package + subclasses
    public boolean publicField;         // Accessible everywhere

    // Constructor that initializes all fields
    public AccessModifierDemo(int p, String d, double pr, boolean pu) {
        this.privateField = p;
        this.defaultField = d;
        this.protectedField = pr;
        this.publicField = pu;
    }

    // Methods with different access modifiers
    private void privateMethod() {
        System.out.println("Private method called");
    }

    void defaultMethod() {
        System.out.println("Default method called");
    }

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    public void publicMethod() {
        System.out.println("Public method called");
    }

    // Method to test access inside the same class
    public void testInternalAccess() {
        System.out.println("--- Inside testInternalAccess() ---");
        // Accessing all fields
        System.out.println("privateField: " + privateField);
        System.out.println("defaultField: " + defaultField);
        System.out.println("protectedField: " + protectedField);
        System.out.println("publicField: " + publicField);

        // Calling all methods
        privateMethod();
        defaultMethod();
        protectedMethod();
        publicMethod();
    }

    public static void main(String[] args) {
        AccessModifierDemo demo = new AccessModifierDemo(10, "Hello", 3.14, true);

        // Accessing fields directly
        // System.out.println(demo.privateField); 
        System.out.println(demo.defaultField);    
        System.out.println(demo.protectedField);   
        System.out.println(demo.publicField);      

        // Accessing methods directly
        // demo.privateMethod(); 
        demo.defaultMethod();     
        demo.protectedMethod();   
        demo.publicMethod();     

        // Test full access internally
        demo.testInternalAccess();
    }
}

// Second class in the SAME package
class SamePackageTest {
    public static void testAccess() {
        AccessModifierDemo demo = new AccessModifierDemo(20, "World", 6.28, false);

        // System.out.println(demo.privateField); 
        System.out.println(demo.defaultField);     
        System.out.println(demo.protectedField);   
        System.out.println(demo.publicField);      

        // demo.privateMethod(); 
        demo.defaultMethod();     
        demo.protectedMethod();   
        demo.publicMethod();      
    }
}
