<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang_bundles.texts"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/buttons_and_forms.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/popup_window.css"/>
    <link rel="stylesheet" href="css/tables.css"/>
    <link rel="stylesheet" href="css/registration_page.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title><fmt:message key="registration_word"/> - <fmt:message key="head"/></title>

    <script src="js/LoginAndEmailCheckerForUniqueness.js"></script>
    <c:forEach var="user" items="${users}">
        <script>
            addLogin("${user.login}");
            addEmail("${user.email}");
        </script>
    </c:forEach>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table>
            <tr>
                <td class="tableTitle" colspan="5"><strong><fmt:message key="registration_word"/></strong></td>
            </tr>
            <tr>
                <td>
                    <form class="contact_form" action="reg" method="post" name="contact_form">
                        <ul>
                            <li>
                                <label for="loginInput"><fmt:message key="login"/>:</label>
                                <input id="loginInput" name="login" type="text" placeholder="<fmt:message key="login_pl"/>" pattern="^[a-zA-Z0-9-]+$" maxlength="18" required/>
                                <span id="messageFromLogin"></span>
                            </li>
                            <li>
                                <label for="pass"><fmt:message key="pass"/>:</label>
                                <input id="pass" name="pass" type="password" placeholder="<fmt:message key="pass_pl"/>" minlength="5" maxlength="20" required/>
                            </li>
                            <li>
                                <label for="name"><fmt:message key="name"/>:</label>
                                <input id="name" name="name" type="text" placeholder="<fmt:message key="name_pl"/>" maxlength="20" required/>
                            </li>
                            <li>
                                <label for="emailInput"><fmt:message key="email"/>:</label>
                                <input id="emailInput" type="email" name="email" maxlength="50" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                                       placeholder="<fmt:message key="email_pl"/>" required/>
                                <span id="messageFromEmail"></span>
                            </li>
                            <li>
                                <label for="phone"><fmt:message key="phone"/>:</label>
                                <input id="phone" type="text" name="phone" maxlength="20" pattern="(\+?\d[- .]*){7,13}" placeholder="<fmt:message key="phone_pl"/>" required/>
                            </li>
                            <li>
                                <label for="description"><fmt:message key="about_me"/>:</label>
                                <textarea id="description" name="description" cols="40" rows="6" maxlength="1000" placeholder="<fmt:message key="about_pl"/>" required></textarea>
                            </li>
                            <li>
                                <input type="checkbox" required> <fmt:message key="accept_rules"/><a href="#rules"> <fmt:message key="forum_rules"/> </a>
                            </li>
                            <li>
                                <button class="submit" type="submit"><fmt:message key="registration"/></button>
                            </li>
                        </ul>
                    </form>
                </td>
            </tr>

        </table>

    </div>

</body>
</html>