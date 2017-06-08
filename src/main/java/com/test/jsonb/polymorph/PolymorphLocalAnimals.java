package com.test.jsonb.polymorph;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * Uses custom (local) PolymorphicAdapter with custom (local) typeWrapper
 */
public class PolymorphLocalAnimals {

    public static class AnimalAdapter extends LocalPolymorphicAdapter<Animal> {
        public AnimalAdapter() {
            super(Dog.class, Cat.class);
        }
    }

    public static class Animal {
        public Animal(String name) {
            this.name = name;
        }

        public Animal() {
            this.name = "NoName animal";
        }
        public String name;
    }

    public static class Dog extends Animal {
        public Dog() {}
        public Dog(String dogProperty) {
            this.dogProperty = dogProperty;
        }
        public String dogProperty;
    }

    public static class Cat extends Animal {
        public Cat() {}
        public Cat(String catProperty) {
            this.catProperty = catProperty;
        }
        public String catProperty;
    }

    public static class Animals {
        public List<Animal> listOfAnimals = new ArrayList<>();
    }

    public static void main(String[] args) {
        JsonbConfig config = new JsonbConfig().withAdapters(new AnimalAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);

        Animals animals = new Animals();
        animals.listOfAnimals.add(new Dog("Hunting"));
        animals.listOfAnimals.add(new Dog("Watching"));
        animals.listOfAnimals.add(new Cat("Sleeping"));
        animals.listOfAnimals.add(new Cat("Playing"));

        System.out.println("Wrapper object: ");
        final String s = jsonb.toJson(animals);
        System.out.println(s);

        System.out.println("List: ");
        System.out.println(jsonb.toJson(animals.listOfAnimals));

        System.out.println("\n1st: ");
        System.out.println(jsonb.toJson(animals.listOfAnimals.get(0), Animal.class));

        System.out.println("\n2nd: ");
        System.out.println(jsonb.toJson(animals.listOfAnimals.get(1), Dog.class));
    }


}
