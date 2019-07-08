<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <c:if test="${fn:length(articles) == 0}">
            <table id="no_blocks_table">
                <tr>
                    <td class="tableTitle"></td>
                </tr>
                <tr class="no_entries">
                    <td>
                        <strong><fmt:message key="no_articles"/></strong>
                    </td>
                </tr>
            </table>
        </c:if>
        <c:forEach var="articleEntry" items="${articles}">
            <table class="article" id="article${articleEntry.article.id}">
                <tr>
                    <td class="tableTitle">
                            ${articleEntry.article.dateOfCreation} &#8195;
                        <strong>
                            <a class="whiteLinks" href="profile?login=${articleEntry.article.user.login}"> ${articleEntry.article.user.login}</a>
                        </strong>
                    </td>
                </tr>
                <tr>
                    <td class="message_text">
                        <a href="article?id=${articleEntry.article.id}">
                            <h2> ${articleEntry.article.topic} </h2></a>
                        <p> ${articleEntry.textPreview} </p>
                    </td>
                </tr>
                <tr>
                    <td class="tableTitle">
                        <c:if test="${sessionScope.user eq articleEntry.article.user
                        || sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MODER'}">
                            <form class="delete_button" action="delete_article" method="post">
                                <input type="hidden" name="id" value="${articleEntry.article.id}">
                                <input type="submit" value="X"/>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </table>
        </c:forEach>
    </div>

    <div id="popup-window">
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <h2>
                <fmt:message key="write_article"/>
            </h2>
            <form id="popupForm" method="post" action="add_article">
                <input name="topic" type="text" maxlength="800" required
                       placeholder="<fmt:message key="article_name"/>"/>
                <textarea name="text" maxlength="10000" required placeholder="<fmt:message key="first_message"/>"
                          rows="20"></textarea>
                <input type="submit" value="<fmt:message key="create"/>"/>
            </form>
            <a class="close" title="<fmt:message key="cancel"/>" href="#close"></a>
        </div>
    </div>


</body>



