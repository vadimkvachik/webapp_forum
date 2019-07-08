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
    <title><fmt:message key="outbox"/> <fmt:message key="private"/> - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle" colspan="2">
                    <fmt:message key="outbox"/> <fmt:message key="private"/> &#8195;
                    <a class="yellowLinks" href="private_in"><fmt:message key="inbox"/></a>
                </td>
            </tr>
            <c:if test="${fn:length(messages) == 0}">
                <tr class="no_entries" id="no_messages">
                    <td colspan="2">
                        <strong><fmt:message key="noMessages"/></strong>
                    </td>
                </tr>
            </c:if>
            <c:forEach var="message" items="${messages}">
                <tr class="entry_forum">
                    <c:choose>
                    <c:when test="${message.read}">
                    <td class="count_entry">
                        <img alt="image" src="resources/images/icons/message_read.png">
                    </td>
                    <td class="main_entry">
                        <a href="private_view?id=${message.id}">${message.topic}</a><br>
                        </c:when>
                        <c:otherwise>
                    <td class="count_entry">
                        <img alt="image" src="resources/images/icons/message_unread.png">
                    </td>
                    <td class="main_entry">
                        <strong><a href="private_view?id=${message.id}">${message.topic}</a></strong><br>
                        </c:otherwise>
                        </c:choose>
                        <form class="delete_button" action="delete_private_message" method="post">
                            <input type="hidden" name="id" value="${message.id}">
                            <input type="hidden" name="activity" value="sender">
                            <input type="submit" value="X"/>
                        </form>
                        <fmt:message key="to"/> <strong><a href="profile?login=${message.to.login}">
                            ${message.to.login}</a></strong> ${message.dateOfSending}
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td class="tableTitle" colspan="2">
                </td>
            </tr>
        </table>
    </div>
</body>



