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
    <title><fmt:message key="messages"/> - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle">
                    <fmt:message key="messages_from"/> <strong> ${login} </strong>
                </td>
            </tr>
            <c:if test="${fn:length(messages) == 0}">
                <tr class="no_entries" id="no_messages">
                    <td>
                        <strong><fmt:message key="noMessages"/></strong>
                    </td>
                </tr>
            </c:if>
            <c:forEach var="message" items="${messages}">
            <tr class="tableHead">
                <td class="main_entry" colspan="2">${message.dateOfPublication} <fmt:message key="inTopic"/>
                    <a href="topic?id=${message.topic.id}">${message.topic.name}</a> </td>
            </tr>
            <tr class="message" id="id${message.id}">
                <td class="message_text">
                    <p class="name">${message.text}</p>
                </td>
            </tr>
            </c:forEach>
            <tr>
                <td class="tableTitle"></td>
            </tr>
        </table>
    </div>
</body>



