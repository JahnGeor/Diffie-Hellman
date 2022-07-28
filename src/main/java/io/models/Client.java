package io.models;
import io.exception.InvalidValuesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
    private final static Logger logger = LogManager.getLogger(Client.class);
    private final static Random random = new Random();
    private final String name;
    private final int modulus;
    private final int base;
    private final int secret;
    private double key;

    public Client(final int modulus, final int base, final String name) throws InvalidValuesException {
        this.name = name;
        this.modulus = modulus;
        this.base = base;
        this.secret = random.nextInt(10) + 1;
        validate();
        logger.info("Клиент \"{}\" создал: модуль = {}, базовый код = {}, секретный код = {}", name, modulus, base, secret);
    }

    public double getA() {
        final double A = Math.pow(base, secret) % modulus;
        logger.info("Клиент \"{}\" вычислил A: {}", name, A);
        return A;
    }

    public void calcKey(final double A) {
        logger.info("Клиент \"{}\" получил A: {}", name, A);
        this.key = Math.pow(A, secret) % modulus;
        logger.info("Клиент \"{}\" создал секретный ключ: {}", name, key);

    }

    private void validate() throws InvalidValuesException {
        final List<Double> values = new ArrayList<>();
        for (int i = 1; i < modulus; i++) {
            final double value = Math.pow(base, i) % modulus;
            if(values.contains(value)) {
                throw new InvalidValuesException("Значения 'base ^ i mod modulus' (i = [1, modulus-1]) должны быть различными");
            }

            if(!(value >= 1 && value <= modulus -1)) {
                throw new InvalidValuesException("Каждое значение 'base ^ i mod modulus' (i = [1, modulus - 1]) должны соответствовать условию [1, modulus - 1]");
            }
            values.add(value);
        }

        if(!(base >= 1 && base <= modulus - 1)) {
            throw new InvalidValuesException("Base должен быть равен значению в диапазоне [1, modulus - 1]");
        }

        if(Math.pow(base, secret) % modulus == 1) {
            throw new InvalidValuesException("Base ^ secret mod modulus не должен равняться 1");
        }

    }

    public String getName() {
        return name;
    }

    public double getKey() {
        return key;
    }
}