Вам нужно написать упрощенный вариант авторизационного сервиса
и несколько утилитных функций для безопасного исполнения кода.

1. Вам нужно создать сущности для пользователя
Авторизованный и Неавторизованный пользователь.
У обоих пользователей должен быть логин и пароль,
у авторизованного пользователя еще должна быть роль.
В качестве роли может быть либо какое то симейство классов
либо просто строка на ваш выбор.
2. Сервис, который будет заниматься авторизацией,
должен принимать на вход пользователя.
Если пользователь уже авторизован вам надо выбрасить UserAlreadyAuthorizedException.
Это исключение вам также надо определить самим.
Иначе надо проверить логин и пароль, и если они правильные
то вернуть Авториованного пользователя. Если нет то выкинуть IllegalArgumentException.
Правильные соотношения логин, пороль и роль, задайте прямо в коде.
3. Сделать package object и в нем написать функцию,
которая будет иметь следующую сигнатуру:
\[T\](пользователь, роль)(функция: (Авторизованный пользователь) => T): T
Она должна выполнять функцию только для авторизованных пользователей
с определённой ролью. Если условие не выполняется, то выбросить IllegalArgumentException.
4. Покрыть тестами весь свой код, с разными сценариями