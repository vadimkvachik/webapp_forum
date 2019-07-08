<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang_bundles.texts"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/buttons_and_forms.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/popup_window.css"/>
    <link rel="stylesheet" href="css/tables.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title>${user.login} - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle">
                    <c:if test="${user.login == sessionScope.user.login}">
                        <form class="delete_button" action="delete_user" method="post">
                            <input type="hidden" name="login" value="${user.login}">
                            <input type="submit" value="X"/>
                        </form>
                        <form action="edit" method="post">
                            <input type="hidden" name="login" value="${user.login}">
                            <input type="hidden" name="name" value="${user.name}">
                            <input type="hidden" name="phone" value="${user.phone}">
                            <input type="hidden" name="description" value="${user.description}">
                            <button class="edit_button">
                                <img src="resources/images/icons/edit.png" alt="image"></button>
                        </form>
                    </c:if>

                    <fmt:message key="info_about_user"/> <strong> ${user.login} </strong>
                </td>
            </tr>
            <tr>
                <td>
                    <h1> ${user.login} </h1>
                    <c:choose>
                        <c:when test="${user.reasonForBlocking != null}">
                            <p>
                                <strong> (<fmt:message key="banned"/>) </strong> <br/>
                                <fmt:message key="reason"/>: ${user.reasonForBlocking}
                            </p>
                        </c:when>
                        <c:when test="${user.deleted}">
                            <p>
                                <strong> (<fmt:message key="deleted"/>) </strong>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <ul>
                                <c:if test="${sessionScope.user != null && user.login != sessionScope.user.login}">
                                    <li>
                                        <strong><a href="#win1"><fmt:message key="write_message"/></a></strong>
                                    </li>
                                    <div id="popup-window">
                                        <a href="#x" class="overlay" id="win1"></a>
                                        <div class="popup">
                                            <h2>
                                                <fmt:message key="write_message"/>
                                            </h2>
                                            <h1>${user.login}</h1>
                                            <form id="popupForm" method="post" action="send_private_message">
                                                <input name="login_from" value="${sessionScope.user.login}" type="hidden">
                                                <input name="login_to" value="${user.login}" type="hidden">
                                                <input name="topic" type="text" maxlength="40" required
                                                       placeholder="<fmt:message key="topic_name"/>"/>
                                                <textarea name="text" maxlength="5000" required placeholder="<fmt:message key="first_message"/>"
                                                          rows="8"></textarea>
                                                <input type="submit" value="<fmt:message key="send"/>"/>
                                            </form>
                                            <a class="close" title="<fmt:message key="cancel"/>" href="#close"></a>
                                        </div>
                                    </div>
                                </c:if>
                                <li>
                                    <fmt:message key="name"/>: <strong class="name">${user.name}</strong>
                                </li>
                                <li>
                                    <fmt:message key="status"/>: <strong><fmt:message key="${user.role}"/></strong>
                                </li>
                                <li>
                                    <fmt:message key="phone"/>: <strong>${user.phone}</strong>
                                </li>
                                <li>
                                    <fmt:message key="email"/>: <strong><a href="mailto:${user.email}">${user.email}</a></strong>
                                </li>
                                <li>
                                    <fmt:message key="registration_word"/>: <strong>${user.dateOfRegistration}</strong>
                                </li>
                                <p>
                                <li>
                                <fmt:message key="messages"/>: <strong><a href="messages?login=${user.login}"> ${messages}</a></strong>
                                </li>
                                <li>
                                    <fmt:message key="articles_word"/>: <strong><a href="articles?login=${user.login}"> ${articles}</a></strong>
                                </li>
                                <li>
                                    <p><fmt:message key="about_me"/>: <strong><br>${user.description}</strong></p>
                                </li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>

        </table>

    </div>


</body>
</html>