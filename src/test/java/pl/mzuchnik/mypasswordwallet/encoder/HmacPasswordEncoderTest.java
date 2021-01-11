package pl.mzuchnik.mypasswordwallet.encoder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

class HmacPasswordEncoderTest {

    @ParameterizedTest
    @ValueSource(strings = {"admin", "mateusz", "haslo","hasełko123"})
    void should_return_hashcode_for_plain_string(String plainText) {
        //given
        HmacPasswordEncoder hmacPasswordEncoder = new HmacPasswordEncoder();
        //when
        String encode = hmacPasswordEncoder.encode(plainText);
        //then
        assertThat(encode, is(not(nullValue())));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1Wz/sYIfZ2dhwuWMvrQ14od00AqNvNAkQ9INPRVH0qQWicDnF4uQNnr4g+83h+tL56G+XH9tEtaUuxgj0t31jw==:admin"
            ,"+6lzgIjoIULIFv9W0LKkKKDsQhpNbgkm2MjTfOhN4wMbDjSIWmCnuhLTmphQT6FZPqg2xHjhlUnuf3ukNSa46A==:mateusz"
            ,"07htzZem9uSdqgzgvW4s9nR1N2d+khWD2EGy9viG+y/1vaMy7jYvmYIV+XjxFUVdo9AlpUBU9D8uynagve4jlA==:haslo"
            ,"3HqnnN/Jy5KDLwH+NWxy88fWQzZmOKU1EWIN7MCpXeKyNX8PeDXrmjIXp5GM/vJPBTE9HP29UH+0pAK1GtiIuA==:hasełko123"}
            ,delimiter = ':')
    void should_text_after_encode_equal_to_encode_text(String encoded, String plainText){
        //given
        HmacPasswordEncoder hmacPasswordEncoder = new HmacPasswordEncoder();
        //then
        assertThat(encoded,equalTo(hmacPasswordEncoder.encode(plainText)));
    }

    @Test
    void should_not_plain_text_equal_to_plain_text_with_uppercase_after_encode(){
        //Given
        HmacPasswordEncoder hmacPasswordEncoder = new HmacPasswordEncoder();
        //When
        String encode1 = hmacPasswordEncoder.encode("mateusz");
        String encode2 = hmacPasswordEncoder.encode("Mateusz");
        //Then
        assertThat(encode1,is(not(equalTo(encode2))));

    }
}