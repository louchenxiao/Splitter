package com.example.splitter.domain;


import java.math.BigDecimal;
import java.util.Objects;

public class Result {
    private final String receiver;
    private final String giver;
    private BigDecimal money;

    public Result(String receiver, String giver, BigDecimal money) {
        this.receiver = receiver;
        this.giver = giver;
        this.money = money;
    }

    public String getReceiver() {
        return receiver;
    }



    public String getGiver() {
        return giver;
    }



    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
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
