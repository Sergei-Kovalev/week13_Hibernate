# Неделя 8 домашнее задание по Patterns

---
___Содержание:___
* Используемый стек.
* Деплой
* Описание.
* Примеры запросов.
___     

# Используемый стек и библиотеки.
1. IntelliJ IDEA Community Edition (для проверки итоговых ветвлений)
2. GitHub
3. AssertJ
4. Mockito
5. GSON
6. Mapstruct
7. Snakeyaml
8. PostgreSQL
9. Hibernate
10. Liquibase
___

# Деплой
- использовался плагин Tomcat. 
- проект генерировался с именем MyApp, т.е. Context Path = "/MyApp"
- если забирать из build/libs, то тоже генерируется с этим именем

# Описание.
- Созданы схемы и данные согласно ТЗ, миграция с помощью Liquibase.
- У сущности Person выделена сущность Passport.
- У сущности House выделена сущность Address.
- Реализована пара методов с помощью JDBC Template.
- Реализованы методы с полнотекстовым поиском для текстовых полей в House и Person.
- Тесты для слоя Service.
- Реализовано 2 профиля dev и prod. (реализация не без костыля... со Spring Boot можно описать в одном файле).

# Примеры запросов для House
запрос Get / Read:
http://localhost:8080/MyApp/houses?uuid=75445f31-2cd7-41e2-8fde-1bedaf1526d7
* где uuid - id запрашиваемого House
* ответ:
~~~
{
  "uuid": "75445f31-2cd7-41e2-8fde-1bedaf1526d7",
  "area": 80.55,
  "address": {
    "country": "Belarus",
    "city": "Minsk",
    "street": "Partizansky avenue",
    "number": "27A"
  },
  "createDate": "2018-08-29T06:12:15.156"
}
~~~

запрос Get / Read:
http://localhost:8080/MyApp/houses?uuid=85445f31-2cd7-41e2-8fde-1bedaf1526d7
* где uuid - id запрашиваемого House / Неверное id
* ответ (со статусом 404):
~~~
Not found House with uuid = 85445f31-2cd7-41e2-8fde-1bedaf1526d7
~~~

запрос Get / Read для всех House с пагинацией на 3 элемента страница 2:
http://localhost:8080/MyApp/houses/?size=3&page=2
* где size - количество объектов в ответе
* где page - страница ответа
* ответ:
~~~
[
  {
    "uuid": "26b4e3fa-aa6c-4a4a-81e1-53d0cf996990",
    "area": 48.12,
    "address": {
      "country": "Belarus",
      "city": "Bobruisk",
      "street": "Mashinostroiteley street",
      "number": "122"
    },
    "createDate": "1988-03-20T15:10:11.165"
  },
  {
    "uuid": "5586015b-7b91-4f2e-a2df-4c20f76912e5",
    "area": 66.26,
    "address": {
      "country": "Belarus",
      "city": "Gomel",
      "street": "Lenina avenue",
      "number": "21"
    },
    "createDate": "1999-01-14T09:00:56.723"
  }
]
~~~

запрос Post / Create:
http://localhost:8080/MyApp/houses
>Body:
>~~~
>{
>   "uuid": "88445f31-2cd7-41e2-8fde-1bedaf1526d7",
>   "area": 100.00,
>   "address": {
>       "country": "Italy",
>       "city": "Rome",
>       "street": "Revolution str",
>       "number": "50"
>   }
>}
>~~~
* ответ:
~~~
{
  "uuid": "88445f31-2cd7-41e2-8fde-1bedaf1526d7",
  "area": 100.0,
  "address": {
    "country": "Italy",
    "city": "Rome",
    "street": "Revolution str",
    "number": "50"
  },
  "createDate": "2024-01-15T01:56:18.3579499"
}
~~~

запрос Post / Create (Попытка сохранить House с существующим UUID):
http://localhost:8080/MyApp/houses
>Body:
>~~~
>{
>   "uuid": "88445f31-2cd7-41e2-8fde-1bedaf1526d7",
>   "area": 100.00,
>   "address": {
>       "country": "Italy",
>       "city": "Rome",
>       "street": "Revolution str",
>       "number": "50"
>   }
>}
>~~~
* ответ:
~~~
Sorry but house with uuid 88445f31-2cd7-41e2-8fde-1bedaf1526d7 already exist at Database
~~~

запрос Put / Update:
http://localhost:8080/MyApp/houses?uuid=4eb6af8c-48ae-416b-8dd8-98935e8367a5
* где uuid - id House для изменения
* (Можно больше полей передавать :smile:)
> Body:
>~~~
>{
>   "area": 100.00
>}
>~~~
* ответ:
~~~
{
  "uuid": "4eb6af8c-48ae-416b-8dd8-98935e8367a5",
  "area": 100.0,
  "address": {
    "country": "Belarus",
    "city": "Minsk",
    "street": "Kuprianova street",
    "number": "48"
  },
  "createDate": "2020-01-16T10:00:00.123"
}
~~~

запрос Put / Update:
http://localhost:8080/MyApp/houses?uuid=5eb6af8c-48ae-416b-8dd8-98935e8367a5
* где uuid - неверный id House для изменения
> Body:
>~~~
>{
>   "area": 100.00
>}
>~~~
* ответ:
~~~
Not found House with uuid = 5eb6af8c-48ae-416b-8dd8-98935e8367a5
~~~

запрос Delete:
http://localhost:8080/MyApp/houses?uuid=88445f31-2cd7-41e2-8fde-1bedaf1526d7
* где uuid - id House для удаления
* 
* ответ:
~~~
House with uuid = 88445f31-2cd7-41e2-8fde-1bedaf1526d7 was successfully deleted
~~~
* либо (при несуществующем в БД UUID, например повторная отправка)
~~~
House with uuid = 88445f31-2cd7-41e2-8fde-1bedaf1526d7 not present at database
~~~

запрос Get / Read:
http://localhost:8080/MyApp/houses/substring?substring=ome
* где substring - подстрока для поиска в текстовых полях House
* ответ:
~~~
[
  {
    "uuid": "5586015b-7b91-4f2e-a2df-4c20f76912e5",
    "area": 66.26,
    "address": {
      "country": "Belarus",
      "city": "Gomel",
      "street": "Lenina avenue",
      "number": "21"
    },
    "createDate": "1999-01-14T09:00:56.723"
  }
]
~~~

# Примеры запросов для Person
запрос Get / Read:
http://localhost:8080/MyApp/persons?uuid=ae679fa9-0955-48f7-8276-c4492875f609
* где uuid - id запрашиваемого Person
* ответ:
~~~
{
  "uuid": "ae679fa9-0955-48f7-8276-c4492875f609",
  "name": "Igor",
  "surname": "Ovsiannikov",
  "sex": "MALE",
  "passport": {
    "passportSeries": "HB",
    "passportNumber": "1234567"
  },
  "createDate": "2018-08-29T06:12:15.156",
  "updateDate": "2018-08-29T06:12:15.156"
}
~~~

запрос Get / Read:
http://localhost:8080/MyApp/persons?uuid=573d82db-d844-45d8-a78a-f13eab3426e7
* где uuid - id запрашиваемого House / Неверное id
* ответ (со статусом 404):
~~~
Not found Person with uuid = 573d82db-d844-45d8-a78a-f13eab3426e7
~~~

запрос Get / Read для всех Person с пагинацией на 3 элемента страница 2:
http://localhost:8080/MyApp/persons/?size=3&page=2
* где size - количество объектов в ответе
* где page - страница ответа
* ответ:
~~~
[
  {
    "uuid": "3f155aed-b0f5-4fbb-bb6f-aa77d6be25e9",
    "name": "Oleg",
    "surname": "Sevostianchik",
    "sex": "MALE",
    "passport": {
      "passportSeries": "MP",
      "passportNumber": "7823564"
    },
    "createDate": "2020-01-16T10:00:00.123",
    "updateDate": "2020-01-16T10:00:00.123"
  },
  {
    "uuid": "8617df91-3c84-4f00-bb03-634139c70297",
    "name": "Zhanna",
    "surname": "Karpova",
    "sex": "FEMALE",
    "passport": {
      "passportSeries": "MP",
      "passportNumber": "5474466"
    },
    "createDate": "2021-12-30T10:00:00.321",
    "updateDate": "2021-12-30T10:00:00.321"
  },
  {
    "uuid": "39e8260d-6a85-4716-9e9b-b3e9c32b879f",
    "name": "Mikhail",
    "surname": "Oreshkin",
    "sex": "MALE",
    "passport": {
      "passportSeries": "MP",
      "passportNumber": "8973552"
    },
    "createDate": "2002-02-01T10:55:12.652",
    "updateDate": "2002-02-01T10:55:12.652"
  }
]
~~~

запрос Post / Create:
http://localhost:8080/MyApp/persons?owner=75445f31-2cd7-41e2-8fde-1bedaf1526d7&owner=4eb6af8c-48ae-416b-8dd8-98935e8367a5&residence=75445f31-2cd7-41e2-8fde-1bedaf1526d7
* где owner - UUID домов в собственности - может не быть
* где residence - UUID дома проживания - не может не быть (человек должен где-то жить). При отсутствии бросит сообщение
~~~
Please enter the uuid of the house as "residence", a person cannot be homeless
~~~
>Body:
>~~~
>{
>   "uuid": "f683741c-762f-4bee-9b0e-d8e18e7db5da",
>   "name": "Dzmiter",
>   "surname": "Orange",
>   "sex": "MALE",
>   "passport": {
>       "passportSeries": "HB",
>       "passportNumber": "9536543"
>   }
>}
>~~~
* ответ:
~~~
{
  "uuid": "f683741c-762f-4bee-9b0e-d8e18e7db5da",
  "name": "Dzmiter",
  "surname": "Orange",
  "sex": "MALE",
  "passport": {
    "passportSeries": "HB",
    "passportNumber": "9536543"
  },
  "createDate": "2024-01-15T02:26:34.4462097",
  "updateDate": "2024-01-15T02:26:34.4462097"
}
~~~

запрос Post / Create (Попытка сохранить House с существующим UUID либо с неуникальной парой серия+номер паспорта):
http://localhost:8080/MyApp/persons?owner=75445f31-2cd7-41e2-8fde-1bedaf1526d7&owner=4eb6af8c-48ae-416b-8dd8-98935e8367a5&residence=75445f31-2cd7-41e2-8fde-1bedaf1526d7
* можно повторить предыдущий запрос
>Body:
>~~~
>{
>   "uuid": "f683741c-762f-4bee-9b0e-d8e18e7db5da",
>   "name": "Dzmiter",
>   "surname": "Orange",
>   "sex": "MALE",
>   "passport": {
>       "passportSeries": "HB",
>       "passportNumber": "9536543"
>   }
>}
>~~~
* ответ:
~~~
Sorry, field uuid or combination of passport series and passport number must be unique
~~~

запрос Put / Update:
http://localhost:8080/MyApp/persons?uuid=ae679fa9-0955-48f7-8276-c4492875f609
* где uuid - id Person для изменения
> Body:
>~~~
>{
>   "name": "Olga",
>   "surname": "Ovsiannikova",
>   "sex": "FEMALE",
>   "passport": {
>       "passportSeries": "AE",
>       "passportNumber": "1234567"
>   }
>}
>~~~
* ответ:
~~~
{
  "uuid": "ae679fa9-0955-48f7-8276-c4492875f609",
  "name": "Olga",
  "surname": "Ovsiannikova",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "AE",
    "passportNumber": "1234567"
  },
  "createDate": "2018-08-29T06:12:15.156",
  "updateDate": "2024-01-15T02:32:39.6251298"
}
~~~

запрос Put / Update:
http://localhost:8080/MyApp/persons?uuid=5825eac1-eab4-469a-90ae-d0ab92cac608
* где uuid - неверный id Person для изменения
> Body:
>~~~
>{
>   "name": "Olga",
>   "surname": "Ovsiannikova",
>   "sex": "FEMALE",
>   "passport": {
>       "passportSeries": "AE",
>       "passportNumber": "1234567"
>   }
>}
>~~~
* ответ:
~~~
Not found Person with uuid = 5825eac1-eab4-469a-90ae-d0ab92cac608
~~~

запрос Delete:
http://localhost:8080/MyApp/persons?uuid=fd54080c-57a7-4a25-9deb-102a088e7a2f
* где uuid - id Person для удаления
*
* ответ:
~~~
Person with uuid = fd54080c-57a7-4a25-9deb-102a088e7a2f was successfully deleted
~~~
* либо (при несуществующем в БД UUID, например повторная отправка)
~~~
Person with uuid = fd54080c-57a7-4a25-9deb-102a088e7a2f not present at database
~~~

запрос Get / Read:
http://localhost:8080/MyApp/persons/substring?substring=leg
* где substring - подстрока для поиска в текстовых полях Person
* ответ:
~~~
[
  {
    "uuid": "3f155aed-b0f5-4fbb-bb6f-aa77d6be25e9",
    "name": "Oleg",
    "surname": "Sevostianchik",
    "sex": "MALE",
    "passport": {
      "passportSeries": "MP",
      "passportNumber": "7823564"
    },
    "createDate": "2020-01-16T10:00:00.123",
    "updateDate": "2020-01-16T10:00:00.123"
  }
]
~~~

запрос Put / Update:
http://localhost:8080/MyApp/persons/ownership?personUUID=ae679fa9-0955-48f7-8276-c4492875f609&houseUUID=a1b64898-7b1e-401a-8a7e-fd50af1bafec
* где personUUID - id Person для добавления права владения домом
* где houseUUID - id House для добавления права владения на него
* 
* ответ:
~~~
For person with uuid = ae679fa9-0955-48f7-8276-c4492875f609 added ownership for a house with uuid = a1b64898-7b1e-401a-8a7e-fd50af1bafec
~~~
* либо выдается сообщение при неверных id House либо Person

запрос Delete:
http://localhost:8080/MyApp/persons/ownership?personUUID=ae679fa9-0955-48f7-8276-c4492875f609&houseUUID=a1b64898-7b1e-401a-8a7e-fd50af1bafec
* где personUUID - id Person для удаления права владения домом
* где houseUUID - id House для удаления права владения на него
* ответ:
~~~
For person with uuid = ae679fa9-0955-48f7-8276-c4492875f609 deleted ownership for a house with uuid = a1b64898-7b1e-401a-8a7e-fd50af1bafec
~~~
* либо выдается сообщение при неверных id House либо Person

запрос Put / Update (Переезд Person жить в другой House):
http://localhost:8080/MyApp/persons/moving?personUUID=ae679fa9-0955-48f7-8276-c4492875f609&houseUUID=4eb6af8c-48ae-416b-8dd8-98935e8367a5
* где personUUID - id Person для переезда
* где houseUUID - id House для переезда в него
* 
* ответ:
~~~
Person with uuid = ae679fa9-0955-48f7-8276-c4492875f609 moved to a new house with uuid = 4eb6af8c-48ae-416b-8dd8-98935e8367a5
~~~
* либо выдается сообщение при неверных id House либо Person

# Примеры запросов для пунктов 1 и 2 ТЗ.

запрос Get / Read (Поиск жильцов, проживающих в доме):
http://localhost:8080/MyApp/persons/residents?houseUUID=75445f31-2cd7-41e2-8fde-1bedaf1526d7
* где houseUUID - id House для поиска всех жильцов, проживающих в нём
* ответ:
~~~
[
  {
    "uuid": "421776cb-284a-47cc-a236-a32b464361a7",
    "name": "Maria",
    "surname": "Sennikova",
    "sex": "FEMALE",
    "passport": {
      "passportSeries": "HB",
      "passportNumber": "1234568"
    },
    "createDate": "2018-08-29T06:12:15.156",
    "updateDate": "2018-08-29T06:12:15.156"
  },
  {
    "uuid": "b3d6f2fc-4c0f-40f9-86f7-64638abaada4",
    "name": "Dmitriy",
    "surname": "Ordzhanikidze",
    "sex": "MALE",
    "passport": {
      "passportSeries": "HB",
      "passportNumber": "6532165"
    },
    "createDate": "2018-08-29T06:12:15.156",
    "updateDate": "2018-08-29T06:12:15.156"
  },
  {
    "uuid": "f683741c-762f-4bee-9b0e-d8e18e7db5da",
    "name": "Dzmiter",
    "surname": "Orange",
    "sex": "MALE",
    "passport": {
      "passportSeries": "HB",
      "passportNumber": "9536543"
    },
    "createDate": "2024-01-15T02:26:34.44621",
    "updateDate": "2024-01-15T02:26:34.44621"
  }
]
~~~

запрос Get / Read (Поиск домов, на которые имеет права собственности Person):
http://localhost:8080/MyApp/persons/ownership?personUUID=ae679fa9-0955-48f7-8276-c4492875f609
* где personUUID - id Person для которого ищем дома с правом собственности.
* ответ:
~~~
[
  {
    "uuid": "75445f31-2cd7-41e2-8fde-1bedaf1526d7",
    "area": 80.55,
    "address": {
      "country": "Belarus",
      "city": "Minsk",
      "street": "Partizansky avenue",
      "number": "27A"
    },
    "createDate": "2018-08-29T06:12:15.156"
  },
  {
    "uuid": "4eb6af8c-48ae-416b-8dd8-98935e8367a5",
    "area": 100.0,
    "address": {
      "country": "Belarus",
      "city": "Minsk",
      "street": "Kuprianova street",
      "number": "48"
    },
    "createDate": "2020-01-16T10:00:00.123"
  }
]
~~~

###### CПАСИБО ЗА ВНИМАНИЕ !!!
