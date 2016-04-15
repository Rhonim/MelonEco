package com.turqmelon.MelonEco.utils;

import com.turqmelon.MelonEco.MelonEco;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/******************************************************************************
 *  Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com   *
 *  For more information, see LICENSE.TXT.                                    *
 ******************************************************************************/

public class Account {

    private UUID uuid;
    private String nickname;
    private Map<Currency, Double> balances = new HashMap<>();

    private boolean canReceiveCurrency = true;

    public Account(UUID uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public boolean withdraw(Currency currency, double amount){
        if (getBalance(currency) >= amount){
            setBalance(currency, getBalance(currency)-amount);
            MelonEco.getDataStore().saveAccount(this);
            return true;
        }
        return false;
    }

    public boolean deposit(Currency currency, double amount){
        if (isCanReceiveCurrency()){
            setBalance(currency, getBalance(currency)+amount);
            MelonEco.getDataStore().saveAccount(this);
            return true;
        }
        return false;
    }

    public String getDisplayName(){
        return getNickname() != null ? getNickname() : getUuid().toString();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setBalance(Currency currency, double amount){
        getBalances().put(currency, amount);
    }

    public double getBalance(Currency currency){
        if (getBalances().containsKey(currency)){
            return getBalances().get(currency);
        }
        return currency.getDefaultBalance();
    }

    public boolean isCanReceiveCurrency() {
        return canReceiveCurrency;
    }

    public void setCanReceiveCurrency(boolean canReceiveCurrency) {
        this.canReceiveCurrency = canReceiveCurrency;
    }

    public Map<Currency, Double> getBalances() {
        return balances;
    }
}