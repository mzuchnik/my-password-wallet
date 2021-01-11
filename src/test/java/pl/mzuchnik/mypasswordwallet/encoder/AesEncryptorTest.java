package pl.mzuchnik.mypasswordwallet.encoder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class AesEncryptorTest {

    @ParameterizedTest
    @CsvSource(value =
            {"admin:secret_key:IuZawO+z6TPu4fPPUtQXFg=="
                    , "Admin123:secret_key44:X9+ifnNws3HJ3xqRbBv5xQ=="
                    , "Mateusz123:secret_mateusz123:3XhyRRjDTrOvS8eCHFxfxA=="},
            delimiter = ':')
    void should_decrypt_encrypted_text_with_secret_key(String plainText, String key, String encodedText) {
        //
        assertThat(plainText, equalTo(AesEncryptor.decrypt(encodedText,key)) );

    }
}