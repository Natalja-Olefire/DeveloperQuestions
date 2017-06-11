package question5;


import java.util.ArrayList;
import java.util.List;

class Main {
    private static class Fruit {

    }

    private static class Apple extends Fruit {
    }

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        eatFruit(apples);
    }

    private static void eatFruit(List<? extends Fruit> fruit) {
    	
    } 
}