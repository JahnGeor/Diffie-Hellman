package io;
import io.exception.InvalidValuesException;
import io.models.Client;
import io.models.Courier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Net {
    private final static Logger logger = LogManager.getLogger(Net.class);

    public static void main(String[] args) {

        try {
            final Client client_a = new Client(23, 5, "Алиса");
            final Client client_b = new Client(23, 5, "Боб");
            final Courier courier = new Courier("Алексей");

            client_b.calcKey(courier.listen("A1", client_a.getA()));
            client_a.calcKey(courier.listen("A2", client_b.getA()));
            logger.info("Клиент \"{}\" имеет ключ {}", client_a.getName(), client_a.getKey());
            logger.info("Клиент \"{}\" имеет ключ {}", client_b.getName(), client_b.getKey());
            logger.info("Курьер \"{}\" видит значения {}", courier.getName(), courier.getValues().toString());

        }

        catch (InvalidValuesException e) {
            logger.error("Обнаружена ошибка во входных значениях:  ", e);


        }

    }
}
