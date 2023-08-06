package test.anotationExercise;

public class Apple {
    //2: 使用注解接口

    @FruitProvider(id = 1, name = "陕西红富士集团", address = "陕西省西安市")
    private String appleProvider;

    private int providerID;

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }
}
