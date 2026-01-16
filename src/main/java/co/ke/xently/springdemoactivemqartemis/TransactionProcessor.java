package co.ke.xently.springdemoactivemqartemis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@AllArgsConstructor
class TransactionProcessor {
    private final JmsClient jmsClient;

    @Scheduled(cron = "0/1 * * * * *")
    public void sendTransaction() {
        int numberOfTransactions = new Random().nextInt(900, 1_000);
        for (int i = 0; i < numberOfTransactions; i++) {
            var transaction = new Transaction(new Random().nextDouble());
            jmsClient.destination("transactions")
                    .send(transaction);
        }
        log.info("Sent {} transactions; processed {}", numberOfTransactions, transactionCount.get());
    }

    private final AtomicInteger transactionCount = new AtomicInteger(0);

    @JmsListener(destination = "transactions")
    public void processTransaction(final Transaction transaction) {
        log.debug("[#{}] Processing transaction {}", transactionCount.incrementAndGet(), transaction);
    }
}
