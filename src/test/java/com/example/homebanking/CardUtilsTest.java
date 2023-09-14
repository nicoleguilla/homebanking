package com.example.homebanking;

import com.example.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTest {
    @Test
    public void cardNumberIsCreated() {
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
    @Test
    public void cvvNumberIsCreated() {
        int cvv = CardUtils.getCvv();
        assertTrue(cvv<=999 && cvv>0);
    }
}
