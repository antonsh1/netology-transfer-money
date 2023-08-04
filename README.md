# Курсовой проект «Сервис перевода денег»


### Архитектура приложения 
Приложение выполнено монолитом  
Испоьзуются три слоя - Контроллер, Сервис, Репозиторий

В контроллере реализованы два метода 
* POST путь /transfer
* POST путь /confirmOperation

В сервисе реализована логика
* Генерация UUID перед добавлением в репозиторий
* Проверка корректных данных по проведению операции (корректный код 0000, корректный UUID + проверка, не закрыта ли уже операция по данному UUID)

В репозитории
* В части нумерации операций используется UUID
* Добаление данных транзакции
* Получение данных транзакции 
* Обновление данных транзакции (в части результата)

### Обработка ошибок
* Перехватываются события валидации объектов класса и переводятся в более понятный для пользователя язык
* Формируются соответствующие объекты сообщений ошибок (в части указания id в сообщении об ошибках, дублируется код HTTP ошибки).
* Созданы исключения для событий
  * неправльный код подтверждения
  * неправильный код операции
  * перевод с таким кодом уже проведен

### Хранение данных 
Данные хранятся в классе с использованием ConcurrentHashMap, где ключем является UUID (для быстрого доступа к объекту перевода) ,а значенем объект перевода со всеми данными. 


### Логирование
Файл лога transfer.log в корне проекта, логирование происходит двумя записями о переводе
* первая, запись данных полученных при обращении по пути /transfer, с указанием uuid
* вторая, запись фиксации обращения по пути /confirmOperation, с указанием uuid и результата операции  
Сделано, со смыслом, что между этими операциями может пройти много времени, а так же, возможно, что и подтверждения может и вообще не быть по тем или иным причинам.  
Запросы с некорректными данными в лог не пишутся.
PS попытался сделать функции записи synchronized, чтобы в многопоточном режиме информация не перемешивалась, а каждая запись была в одну строку.

### Функциональность тестировалась с помощью Postman и фронта по адресу https://serp-ya.github.io/card-transfer/

### Варианты запросов
POST по адресу http://localhost:5500/transfer
с указанием заголовка Content-Type = application/json
В теле передаем параметры по переводу в виде JSON
```json
{
  "cardFromNumber":"0000000000000000",
  "cardFromValidTill":"22/22",
  "cardFromCVV":"cvv",
  "cardToNumber":"0000000000000000",
  "amount": {
    "value":"10",
    "currency":"RUR"
  }
}
```

POST по адресу http://localhost:5500/confirmOperation
с указанием заголовка Content-Type = application/json
В теле передаем параметры по подтвержению перевода в виде JSON
```json
{
    "code":"0000",
    "operationId":"f89eca3a-20e9-4684-9f26-38339d790d43"
}
```
