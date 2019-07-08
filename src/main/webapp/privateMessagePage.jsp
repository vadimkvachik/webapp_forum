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
    <title>${message.topic} - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle" colspan="2">
                    <strong>${message.topic}</strong>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="from"/>: <strong>${message.from.login} </strong> <br>
                    <fmt:message key="to"/>: <strong> ${message.to.login} </strong>
                </td>
            </tr>
            <tr>
                <td>
                    <p>${message.text}</p>
                </td>
            </tr>
            <c:if test="${sessionScope.user eq message.to}">
            <tr>
                <td class="tableTitle" colspan="2">
                    <form id="new_message" method="post" action="send_private_message">
                        <input name="login_from" value="${sessionScope.user.login}" type="hidden">
                        <input name="login_to" value="${message.from.login}" type="hidden">
                        <input name="topic" required value="RE: ${message.topic}" type="hidden">
                        <textarea name="text" required maxlength="5000" placeholder="<fmt:message key="enter_text"/>" rows="8"></textarea>
                        <input type="submit" value="<fmt:message key="send"/>"/>
                    </form>
                </td>
            </tr>
            </c:if>
        </table>
    </div>
</body>



