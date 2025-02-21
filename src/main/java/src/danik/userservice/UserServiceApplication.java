package src.danik.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
    /*
    1. User Service (Сервис пользователей)
Функционал:

Регистрация, авторизация (например, через JWT).
Управление профилем: обновление аватара, статуса, личной информации.
Почему отдельный микросервис?

Пользовательские данные – ядро системы. Отделив их в отдельный сервис, ты сможешь централизованно управлять аутентификацией и профилем,
а также обеспечить независимое масштабирование.
Технологии и архитектура:

Контроллеры: REST‑эндпоинты для регистрации, логина, получения и изменения профиля.
Сервис: Бизнес‑логика (валидация, шифрование паролей и т.д.).
DAO/Repository: Работа с PostgreSQL через Spring Data JPA.
Swagger для документирования API и тестов.
     */
}
