<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title><fmt:message key="articles"/> - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">

        <table class="article">
            <tr>
                <td class="tableTitle">
                    ${article.dateOfCreation} &#8195;
                    <strong>
                        <a class="whiteLinks" href="profile?login=${article.user.login}"> ${article.user.login}</a>
                    </strong>
                </td>
            </tr>
            <tr>
                <td class="message_text">
                    <h2> ${article.topic} </h2>
                    <p> ${article.text} </p>
                </td>
            </tr>
            <tr>
                <td class="tableTitle">
                    <c:if test="${sessionScope.user eq article.user
                        || sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MODER'}">
                        <form class="delete_button" action="delete_article" method="post">
                            <input type="hidden" name="id" value="${article.id}">
                            <input type="submit" value="X"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
</body>



