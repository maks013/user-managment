# System Do Zarządzania Użytkownikami

## Kurs
Testowanie i jakość oprogramowania / projekt

## Autor
Maksymilian Ulanecki

## Opis Projektu
System zarządzania użytkownikami, zawierający:
- Rejestrację użytkownika
- Logowanie użytkownika
- Zmianę danych (email, nazwa użytkownika)
- Zmianę hasła
- Usuwanie użytkownika

Mechanizm logowania jest oparty o autoryzację JWT. Dodatkowo aplikacja zawiera mechanizm wysyłania maili z linkiem do potwierdzenia rejestracji.

## Uruchomienie Projektu
Serwer może być uruchomiony przy użyciu komendy `mvn spring-boot:run`.

## Dokumentacja API

### Autoryzacja

`POST /auth/register` - Endpoint do rejestracji użytkownika.
- Przyjmuje dane rejestracyjne `{"username": "Example", "password": "Example", "email": "example@example.com"}`
- Zwraca dane użytkownika `{"id": "1", "username": "Example", "email": "example@example.com"}` oraz wysyła email z linkiem do potwierdzenia rejestracji.

`POST /auth/login` - Endpoint do logowania użytkownika.
- Przyjmuje dane logowania `{"username": "Example", "password": "Example"}`
- Zwraca token JWT `{"username": "Example", "token": "AAA.BBB.CCC"}`.

### Użytkownicy

`GET /users` - Endpoint do odczytu listy wszystkich użytkowników.
- Zwraca listę użytkowników `[{"id": "1", "username": "Example", "email": "example@example.com"}]`.

`GET /users/user` - Endpoint do odczytu danych zalogowanego użytkownika.
- Zwraca dane użytkownika wraz z hasłem `{"id": "1", "username": "Example", "password": "crypted(Example)", "email": "example@example.com", "enabled": true}`.

`DELETE /users/{id}` - Endpoint do usuwania użytkownika.
- Usuwa użytkownika na podstawie podanego ID `{id}` i zwraca status 204 No Content w przypadku powodzenia.

`PUT /users/{id}` - Endpoint do aktualizacji danych użytkownika.
- Przyjmuje zmienione dane użytkownika `{"username": "New Username", "email": "new@new.com"}` i aktualizuje informacje o użytkowniku na podstawie ID.

`PUT /users/update-password/{id}` - Endpoint do zmiany hasła użytkownika.
- Przyjmuje `{"oldPassword": "Example", "newPassword": "NewPassword"}` i aktualizuje hasło dla użytkownika o podanym ID.

## Scenariusze Testowe dla Testera Manualnego

### Rejestracji użytkownika:
- Krok 1: Sprawdź, czy można zarejestrować nowego użytkownika poprzez wprowadzenie poprawnych danych (nazwa użytkownika, email, hasło).
- Krok 2: Upewnij się, że po udanej rejestracji użytkownik otrzymuje maila z linkiem do potwierdzenia rejestracji.
- Krok 3: Zweryfikuj, czy użytkownik po kliknięciu linku w mailu jest w pełni aktywowany w aplikacji.

### Logowania użytkownika:
- Krok 1: Sprawdź, czy istnieje możliwość zalogowania się poprawnymi danymi (nazwa użytkownika i hasło).
- Krok 2: Upewnij się, że po poprawnym zalogowaniu użytkownik ma dostęp do swojego konta.

### Edycji danych użytkownika:
- Krok 1: Zweryfikuj, czy zalogowany użytkownik może edytować swoją nazwę użytkownika.
- Krok 2: Upewnij się, że użytkownik może zmieniać swój email.
- Krok 3: Sprawdź, czy zmiana danych jest zachowywana i widoczna po wylogowaniu i ponownym zalogowaniu.

### Zmiany hasła użytkownika:
- Krok 1: Sprawdź możliwość zmiany hasła po zalogowaniu.
- Krok 2: Upewnij się, że nowe hasło jest akceptowane i zapisywane poprawnie.

### Usuwania użytkownika:
- Krok 1: Zweryfikuj, czy istnieje możliwość usunięcia konta przez zalogowanego użytkownika.
- Krok 2: Upewnij się, że dane użytkownika są prawidłowo usunięte z systemu.

### Wyświetlania innych użytkowników:
- Krok 1: Sprawdź, czy zalogowany użytkownik może wyświetlić listę innych użytkowników.
- Krok 2: Upewnij się, że zalogowany użytkownik nie widzi pełnych danych innych użytkowników (tylko ich podstawowe informacje).

### Potwierdzania rejestracji poprzez link w mailu:
- Krok 1: Zweryfikuj, czy link potwierdzający rejestrację działa poprawnie.
- Krok 2: Upewnij się, że po potwierdzeniu rejestracji użytkownik ma dostęp do pełnej funkcjonalności aplikacji.

### Próby edycji danych innych użytkowników:
- Krok 1: Spróbuj edytować dane innego użytkownika jako zalogowany użytkownik.
- Krok 2: Upewnij się, że edycja danych innych użytkowników jest niemożliwa.

### Niepoprawnego logowania:
- Krok 1: Spróbuj zalogować się z niepoprawnymi danymi.
- Krok 2: Upewnij się, że system nie pozwala na zalogowanie się z nieprawidłowymi danymi.

### Zmiany hasła po potwierdzeniu rejestracji:
- Krok 1: Spróbuj zmienić hasło po potwierdzeniu rejestracji przez link w mailu.
- Krok 2: Zweryfikuj, czy zmiana hasła po potwierdzeniu rejestracji przebiega bez problemów.

## Technologie Użyte w Projekcie
- Java 17
- JUnit
- Spring-Boot
