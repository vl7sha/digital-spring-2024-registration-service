# Digital Spring 2024

## Стэк приложения

- языка программирования: java 17
- фреймворк: spring boot
- работа с почтой: spring boot starter mail
- работа с бд: spring boot jpa
- бд: postgresql 13.3

## Запуск приложения

1. Можно запустить просто docker compose, который соберет приложение и запустит базу данных
   `docker compose up -d`
2. Можно запустить бд где-то на стороне и запустить приложение отдельно, но не забудьте поменять переменные в
   application.yml, которые нужны для корректной работы

## Переменные

| env                          | default                                        | description                                                                                       |
|------------------------------|------------------------------------------------|---------------------------------------------------------------------------------------------------|
| SPRING_DATASOURCE_URL        | jdbc:postgresql://localhost:5433/digitalspring | ссылка на бд                                                                                      |
| SPRING_DATASOURCE_USERNAME   | user                                           | имя пользователя                                                                                  |
| SPRING_DATASOURCE_PASSWORD - | 2024                                           | пароль пользователя                                                                               |
| JWT_SUBJECT                  | user                                           | субъект JWT                                                                                       |
| JWT_ISSUER                   | course                                         | кто выдал                                                                                         |
| JWT_SECRET                   | J7gzxsdfdsdsfsklill234czxcQgfJ7gQgfczxc        |                                                                                                   |
| JWT_TOKEN_EXPRIES_IN         | 432000                                         | время жизни JWT токена. Измеряется в секундах                                                     |
| BACKEND_URL                  | http://localhost:8080                          | url на приложение                                                                                 |
| CLIENT_URL                   | http://localhost:4200                          | url на клиент приложения                                                                          |
| CONFIRM_TOKEN_URL            | http://localhost:4200/auth/confirm             | url на подтверждения аккаунта на клиенте, нужен для почты                                         |
| RESTORE_TOKEN_URL            | http://localhost:4200/auth/restore             | url на восстановление пароля аккаунта на клиенте, нужен для почты                                 |
| HOST_MAIL                    | секрет                                         | хост на котором расположена почта                                                                 |
| PORT_MAIL                    | секрет                                         | порт на котором слушает почта                                                                     |
| USERNAME_MAIL                | секрет                                         | логин для почты                                                                                   |
| PASSWORD_MAIL                | секрет                                         | пароль для почты                                                                                  |
| PROTOCOL_MAIL                | секрет                                         | протокол по которому будет работать почта                                                         |
| AUTH_MAIL                    | true                                           | включить ли аутентификация                                                                        |
| CONFIRM_EXPIRED_TIME         | 30                                             | время жизни токена для подтверждения аккаунта. Измеряется в минутах                               |
| RESTORE_EXPIRED_TIME         | 30                                             | время жизни токена для восстановления пароля. Измеряется в минутах                                |
| SERVER_PORT                  | 8080                                           | порт для приложения, который будет слушаться. Если меняем, то не забываем поменять и в dockerfile |
|                              |                                                |                                                                                                   |

             