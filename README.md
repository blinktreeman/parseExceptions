# parseExceptions

## Запрос данных
Запрашиваем в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Для запроса данных выполнен класс UserDataRequest.
Метод класса requestDataFromUser пробрасывает исключение 
если количество введенных данных не соответствует требованиям
```java
public class UserDataRequest {
    public void requestDataFromUser() throws WrongAmountOfDataException {
```
Выполнен класс исключений
```java
public class WrongAmountOfDataException extends RuntimeException{
    public WrongAmountOfDataException(String message) {
        super(message);
    }
}
```
При некорректном вводе возвращаем сообщения:
```java
String message = userStringsList.size() < 6
                    ? "You have entered less data than required"
                    : "You have entered more data than required";
            throw new WrongAmountOfDataException(message);
```
Если данные корректны, данные сохраняются в виде списка 
в поле userDataList класса UserDataHolder
```java
public class UserDataHolder {
    private static UserDataHolder userDataHolder;
    private static List<String> userDataList;
    private static UserData userData;
```
## Парсинг данных
Для парсинга выполнен класс UserDataParser
```java
public class UserDataParser {
    public void parseUserData() throws UserDataParseException {
```
Метод parseUserData() пробрасывает исключение если:
- пол задан некорректно
```java
.orElseThrow(() -> new UserDataParseException("Wrong gender data, " +
                        "please enter gender data as 'm' or 'f'"));
```
- номер телефона задан некорректно
```java
.orElseThrow(() -> new UserDataParseException("Wrong phone number please enter as number"));
```
- неверный формат даты рождения
```java
.orElseThrow(() -> new UserDataParseException("Wrong date of birth please enter as dd.mm.yyyy"));
```
- имена заданы одной буквой или содержат цифры
```java
hrow new UserDataParseException("Name/surname/patronymic must be more than one letter and no numbers");
```
При удачном парсинге данные записываются в поле класса UserDataHolder
```java
UserDataHolder.setUserData(userData);
```
## Разбор/корректировка ФИО с помощью DaData API
Запрос реализован в классе NamesDaDataRequest.
Для определения фамилии, имени и отчества выполняем запрос к API:
```java
HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://cleaner.dadata.ru/api/v1/clean/name"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", DaDataAPIKeyConfig.getAPIKey())
                    .header("X-Secret", DaDataAPIKeyConfig.getSecretKey())
                    .POST(HttpRequest.BodyPublishers.ofString(requestString))
                    .build();
```
заголовки запроса "Authorization" и "X-Secret" 
получаем из DaDataAPIKeyConfig.java.

JSON-ответ записываем на свои места в UserDataHolder
```java
UserDataHolder.getUserData().setName(responseMap.get("name"));
UserDataHolder.getUserData().setSurname(responseMap.get("surname"));
UserDataHolder.getUserData().setPatronymic(responseMap.get("patronymic"));
```
## Запись в файл
Запись реализована методом writeDataToFile класса WriteUserDataToFile
```java
public void writeDataToFile() throws IOException {
```
При работе с файлами используем try-with-resources