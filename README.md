# Сбор информации о системе

## Описание
Приложение собирает информацию о CPU, RAM, ОС и выводит в JSON.

## Технологии
Java 17, Gradle, Jackson, JUnit 5, Checkstyle

## Пример вывода
```json
{
  "os": { "name": "Ubuntu", "version": "22.04" },
  "cpu": { "model": "Intel i7-10750H", "cores": 12 },
  "memory": { "total_mb": 15998 }
}