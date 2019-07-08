#  Веб-приложение «Форум»
## Курсовой проект
[Ссылка на этот проект, переписанный с использованием Spring](https://github.com/vadimkvachik/webapp_forum_with_spring)

[![N|Solid](http://sopranotv.ru/logo/youtube_logo.png)](https://youtu.be/onHlHWLVH3c)

### Общие требования к проекту:
 Приложение реализовать, применяя технологии Servlet и JSP. Архитектура приложения должна соответствовать шаблонам Layered architecture и Model-View-Controller.
 
Информация о предметной области должна храниться в БД:
- данные в базе хранятся на кириллице, рекомендуется применять кодировку utf-8;
- при проектировании БД рекомендуется не использовать более 6-8 таблиц; 
- доступ к данным в приложении осуществлять с использованием шаблона DAO.

Приложение должно корректно обрабатывать возникающие исключительные ситуации, в том числе вести их журналирование. В качестве логгера использовать Log4j.
 
Классы и другие сущности приложения должны быть грамотно структурированы по пакетам и иметь отражающую их функциональность название. 

Для хранения пользовательской информации между запросами использовать сессию. 

При реализации страниц JSP следует использовать теги библиотеки JSTL, использовать скриплеты запрещено. Просмотр “длинных списков” желательно организовывать в постраничном режиме.

### Общие требования к функциональности проекта:
1) Вход (sign in) и выход (sign out) в/из системы. 
2) Регистрация. 
3) Просмотр информации (например: просмотр всех курсов, имеющихся кредитных карт, счетов и т.д.) 
4) Удаление информации (например: отмена заказа, медицинского назначения, отказ от курса обучения и т.д.) 
5) Добавление и модификация информации (например: создать и отредактировать курс, создать и отредактировать заказ и т.д.)

### Технологии:
- apache maven - Сборка проекта
- apache tomcat - Сервер
- jsp (jstl) / css - Пользовательский интерфейс
- javaScript - Некоторая логика на jsp страницах
- servlet - Контроллеры и фильтры
- resourceBundle - Интернационализация  
- hyper SQL Database - Встроенная база данных
- hibernate - ORM фреймворк
- log4J - Логирование

### Запуск:
Проект собирается сборщиком apache maven.

Запускается на сервере tomcat (рекомендуется не ниже 9 версии).

Проект использует встроенную базу данных "hyper SQL Database". Для подключения к базе данных PostgreSQL нужно установить её на компьютер и перенастроить hibernate.cfg.xml (закомментировать или удалить настройки HSQLDB и раскомментировать и переделать под себя настройки PostgreSQL - название БД, логин и пароль). Hibernate сам создаст необходимые таблицы.

На форуме реализована поддержка двух языков пользовательского интерфейса - русский и английский. Их можно менять кликом по флагу в верхнем правом углу. По умолчанию установлен русский (можно сменить в forum.properties)

Для инициализации администратора нужно обратиться к адресу /initialization (например http://localhost:8080/forum/initialization ). Создается пользователь с правами администратора (по умолчанию логин и пароль - admin, можно сменить в forum.properties).

# Функциональность:

### Регистрация:
Для регистрации в навигационной панели есть ссылка "регистрация" (не доступна для авторизованного пользователя). На всех полях формы регистрации есть проверка корректности ввода. Логин и имейл проверяются на уникальность. При корректном вводе всех полей кнопка регистрации станет активной, после её нажатия пользователь автоматически авторизуется и будет перенаправлен на главную страницу.

### Авторизация:
Для авторизации в верхнем правом углу есть форма ввода логина-пароля (не доступна для авторизованного пользователя). При верном вводе пользователь авторизуется и будет перенаправлен на главную страницу.

При неверном вводе пользователь будет перенаправлен на страницу повторного ввода логина-пароля с возможностью восстановления пароля по почте (язык письма зависит он выбранного языка на форуме, содержание письма можно редактировать в mail.properties, есть проверка на существование пользователя с таким имейлом в базе данных).

У авторизованного пользователя вместо формы логина и пароля появится панель пользователя.

### Обмен сообщениями на форуме:
**Не авторизованный пользователь может:**

Просматривать список разделов и тем. Читать сообщения на форуме (реализован постраничный режим по 10 сообщений на странице).

**Пользователь со статусом "пользователь" может то же, что и не авторизованный пользователь +:**

Создавать темы в разделах, писать в темах сообщения, редактировать иди удалять своё последнее сообщение в теме (если после него ничего не написали).

**Пользователь со статусом "модератор" может то же, что и пользователь со статусом "пользователь" +:**

Переименовывать и удалять темы в разделах, редактировать или удалять все сообщения.

**Пользователь со статусом "администратор" может то же, что и пользователь со статусом "модератор" +:**

Создавать, переименовывать и удалять блоки разделов и разделы на главной странице.

### Раздел "Пользователи":
**Не авторизованный пользователь может:**

Посмотреть список всех зарегистрированных пользователей, профиль каждого пользователя (ели он не забанен и не удалён).

**Пользователь со статусом "пользователь" может то же, что и не авторизованный пользователь +:**

Написать личное сообщение любому пользователю (еcли он не забанен и не удалён).

**Пользователь со статусом "модератор" может то же, что и пользователь со статусом "пользователь" +:**

Забанить или разбанить любого пользователя, указав причину бана (кроме пользователя с логином "admin").

**Пользователь со статусом "администратор" может то же, что и пользователь со статусом "модератор" +:**

Сменить роль любому пользователю (кроме пользователя с логином "admin").

### Обмен личными сообщениями:
Написать личное сообщение может авторизованный пользователь любому другому пользователю либо через страницу "пользователи", либо через страницу с профилем пользователя. После этого у получателя появится уведомление в панели пользователя о непрочитанном сообщении.

Непрочитанное сообщение отображается с изображением закрытого конверта и жирным текстом.
Прочитанное сообщение отображается с изображением открытого конверта.

По ссылке "личные сообщения" в панели пользователя можно посмотреть входящие сообщения, исходящие, узнать прочитаны они получателем или нет, ответить на них и удалить.


### Удаление личных сообщений:
- Непрочитанное сообщение, удаленное отправителем, удаляется из базы данных и исчезает у получателя.
- Непрочитанное сообщение, удаленное получателем, удаляется только у него.
- Прочитанное сообщение удаляется только у пользователя, который его удалил.
- Если сообщение удалили оба пользователя, оно удаляется из базы данных.

### Профиль пользователя:
На странице с профилем пользователя можно: 
- посмотреть информацию о нём; 
- написать ему личное сообщение или имейл;
- посмотреть его сообщения на форуме или статьи.
Пользователь может отредактировать или удалить свой профиль.

### Удаление профиля:
После удаления пользователь помечается как удаленный, блокируется доступ к его профилю и возможность писать ему личные сообщения. Пользователь только сам может удалиться и восстановиться.

### Редактирование профиля:
Авторизованный пользователь может изменить свое имя, телефон, информацию о себе, пароль.

### Правила форума
Раздел доступен с любой части форума по ссылке "правила" в навигационной панели.

### Статьи:
- Любой пользователь может почитать все статьи на портале.
- Авторизованный пользователь может опубликовать статью. Свои статьи может удалить.
- Администратор и модератор могут удалять любые статьи.
- Функционал раздела "статьи" будет ещё дорабатываться.


