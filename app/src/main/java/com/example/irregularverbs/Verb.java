package com.example.irregularverbs;

public class Verb {
    private final String first_form;
    private final String second_form;
    private final String third_form;
    private final String[] incorrectOptions2;
    private final String[] incorrectOptions3;

    public String getFirst_form(){
        return first_form;
    }

    public String getSecond_form() {
        return second_form;
    }

    public String getThird_form() {
        return third_form;
    }

    public String[] getIncorrectOptions2() {
        return incorrectOptions2;
    }

    public String[] getIncorrectOptions3() {
        return incorrectOptions3;
    }

    public Verb(String first, String second, String third, String[] incorrect2, String[] incorrect3){
        first_form = first;
        second_form = second;
        third_form = third;
        incorrectOptions2 = incorrect2;
        incorrectOptions3 = incorrect3;
    }
}
