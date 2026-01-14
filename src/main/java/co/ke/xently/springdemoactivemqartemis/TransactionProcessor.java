package co.ke.xently.springdemoactivemqartemis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
class TransactionProcessor {
    private final JmsTemplate jmsTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void sendTransaction() {
        log.info("Sending transaction");
        var transaction = new Transaction(new Random().nextDouble());
        jmsTemplate.convertAndSend("transactions", transaction);
        log.info("Sent transaction {}", transaction);
    }

    @JmsListener(destination = "transactions")
    public void processTransaction(final Transaction transaction) {
        log.info("Processing transaction {}", transaction);
    }
}
