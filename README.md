# Aplikacja My Password Wallet

### Wstęp:

Aplikacja służy do składowania haseł użytkownika dla poszczególnych serwisów internetowych np: facebook, youtube itp..., w bezpieczny sposób. 
Dodatkowo użytkownik, może dzielić się wybranymi hasłami do serwisów z innymi użytkownikami na zasadzie współdzieleniu hasła. 

### Funkcjonalność:

- Użytkownik ma możliwość dodawania, edycji i usuwania swoich haseł do danych serwisów,
- Użytkownik może udostępnić wybrane hasło z "wirtualnego portfela" do wybranego przez siebie innego użytkownika,
- Użytkownik widzi i wie, komu udostępnia hasło oraz widzi hasła udostępnione przez innych użytkowników dla niego,
- Użytkownik podczas zakładania konta wybiera w jaki sposób jego hasło ma być szyfrowane i przetrzymywane w bazie danych (HMac, BCrypt)
- System śledzi działania użytkownika takie jak:
  - z jakiego ip i kiedy ktoś zalogował się na konto użytkownika
  - historia edycji poszczególnych haseł w wirtualnym portfelu (Kiedy i z jakiego ip zostało zmienione hasło)
  - możliwość cofnięcia zmian w wirtualnym portfelu (np: użytkownik zmienił nazwę serwisu na niepoprawna lub podał niepoprawne hasło. Za pomocą przycisku 'Rollback' może wrócić do poprzedniego stanu) <- Archiwizacja haseł

### Bezpieczeństwo: 

- System jest odporny na ataki typu Brute-Force. Podczas 3 próby uwierzytelniania bez konsekwencji, podczas dalszych prób zostanie założona czasowa blokada na ip z którego dokonywana jest próba uwierzytelnienia. Po większej ilości prób konto zostaje zablokowane i w celu odblokowania go należy skontaktować się z administratorem,
- Użytkownik podczas rejestracji może wybrać w jaki sposób ma być szyfrowane jego hasło (HMAC lub BCrypt),
- Hasła w portfelu wirtualnym są szyfrowanie algorytmem AES gdzie kluczem jest zaszyfrowane hasło główne do konta danego użytkownika,
- Działania użytkownika są rejestrowane, a dostęp do danych jest widoczny dla użytkownika (w celu np: wykrycia "dziwnej aktywności" -> ktoś inny poza wiedzą właściciela konta zalogował się na jego konto),

### Stack technologiczny
- Java
- Spring Boot
- Spring Security
- Hibernate / JPA
- MySQL
- HTML / CSS / JS
