# Polyclinic Test Task 

## Latest Release
v1.2 - [Download](https://github.com/Evgenen96/polyclinic-web-app/archive/v1.2.zip)

## Task Description
* Реализовать систему ввода и отображения информации о рецептах поликлиники, включающую следующие сущности и их атрибуты:
    * Пациент
        * Имя
        * Фамилия
        * Отчество
        * Телефон
    * Доктор
        * Имя
        * Фамилия
        * Отчество
        * Специализация
    * Рецепт
        * Описание
        * Пациент
        * Врач
        * Дата создания
        * Срок действия
        * Приоритет (Нормальный, Cito (Срочный), Statim (Немедленный))
* Система должна иметь следующие функции:
    * Отображение списка пациентов
    * Добавление нового пациента, редактирование и удаление существующего
    * Отображение списка врачей
    * Отображение статистической информации по количеству рецептов, выписанных врачами
    * Добавление нового врача, редактирование и удаление существующего
    * Отображения списка рецептов
    * Фильтрация списка рецептов по описанию, приоритету и пациенту
    * Добавление нового рецепта, редактирование и удаление существующего

## SQL Scripts Path (inside this repository)

* Script for creating tables: `db/createDB.sql`
* Script for populating tables: `db/popDB.sql`

## Template Used

* [Haulmont Test Task](https://github.com/Haulmont/test-task)

## Prerequisites

* [Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)

## Build and Run

1. Run in the command line:
	```
	mvn package
	mvn jetty:run
	```

2. Open `http://localhost:8080` in a web browser.
