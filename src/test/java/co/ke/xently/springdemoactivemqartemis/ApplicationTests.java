package co.ke.xently.springdemoactivemqartemis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    void shouldHaveVirtualThreadsEnabled() {
        String virtualThreadsEnabled = context.getEnvironment().getProperty("spring.threads.virtual.enabled");

        assertThat(virtualThreadsEnabled).isEqualTo("true");
    }

    @Test
    void shouldHaveJsonMessageConverter() {
        MessageConverter messageConverter = jmsTemplate.getMessageConverter();

        assertThat(messageConverter).isInstanceOf(JacksonJsonMessageConverter.class);
    }
}
