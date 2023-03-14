package com.example.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

public class Result {
    private final Person receiver;
    private final Person giver;
    private Money money;

    public Result(Person receiver, Person giver, Money money) {
        this.receiver = receiver;
        this.giver = giver;
        this.money = money;
    }

    public Person getReceiver() {
        return receiver;
    }



    public Person getGiver() {
        return giver;
    }



    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Result{" +
                "receiver=" + receiver +
                ", giver=" + giver +
                ", money=" + money +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result result)) return false;
        return Objects.equals(getReceiver(), result.getReceiver()) && Objects.equals(getGiver(), result.getGiver()) && Objects.equals(getMoney(), result.getMoney());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReceiver(), getGiver(), getMoney());
    }
}
