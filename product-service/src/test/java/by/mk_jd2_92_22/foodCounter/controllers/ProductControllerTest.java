//package by.mk_jd2_92_22.foodCounter.controllers;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ProductControllerTest {
//
//    protected TestRestTemplate restTemplate;
//
//    @Test
//    public void getDate() {
//
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println("Test Local = " + now);
//
//        long timeToMilli = now.toInstant(ZoneOffset.UTC).toEpochMilli();
//        System.out.println("Test timeToMilli = " + timeToMilli);
//        String url = "/product/" + timeToMilli;
//
//        String response = restTemplate.getForObject(url, String.class);
//
//        System.out.println(response);
//
////        assertThat(response).isEqualTo("{\"dtUpdate\":\"" + brand + "\",\"date\":\"" + formattedDate + "\"}");
//    }
//
//}
