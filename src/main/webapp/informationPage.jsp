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
    <title><fmt:message key="head"/></title>
</head>

<body>
    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle">
                </td>
            </tr>
            <tr>
                <td class="information">
                    <c:choose>
                        <c:when test="${information == 'admin_register_successful'}">
                            <p><fmt:message key="admin_register_successful"/></p>
                        </c:when>
                        <c:when test="${information == 'successful_password_change'}">
                            <p><fmt:message key="pass_change"/></p>
                        </c:when>
                        <c:when test="${information == 'fail_password_change'}">
                            <p><fmt:message key="pass_wrong"/></p>
                        </c:when>
                        <c:when test="${information == 'successful_send_new_password'}">
                            <p><fmt:message key="pass_send"/></p>
                        </c:when>
                        <c:when test="${information == 'fail_send_new_password'}">
                            <p><fmt:message key="pass_send_fail"/></p>
                        </c:when>
                        <c:when test="${information == 'fail_send_new_password_no_internet'}">
                            <p><fmt:message key="pass_send_fail_no_internet"/></p>
                        </c:when>
                        <c:when test="${information == 'your_role_changed'}">
                            <p><fmt:message key="change_role_message"/> <fmt:message key="${role}"/></p>
                        </c:when>
                        <c:when test="${information == 'successful_restore_yourself'}">
                            <p><fmt:message key="user_restore"/></p>
                        </c:when>
                        <c:when test="${information == 'your_profile_banned'}">
                            <p><fmt:message key="user_banned"/>: ${reason}<br>
                                <a href="mailto:admin@programmersforum.by"><fmt:message key="admin_contact"/></a></p>
                        </c:when>
                        <c:when test="${information == 'your_profile_deleted'}">
                            <p><fmt:message key="user_delete"/><br></p>
                            <form action="restore_profile" method="post">
                                <input type="hidden" name="login" value="${login}">
                                <button type="submit"><fmt:message key="restore"/></button>
                            </form>
                        </c:when>
                        <c:when test="${information == 'message_sent'}">
                            <p>
                                <fmt:message key="message_sent"/> ${login}<br>
                                <a href="profile?login=${login}"><fmt:message key="go_profile"/></a></p>
                        </c:when>
                    </c:choose>
                    <a href="index"> <img src="resources/images/icons/main_page.png"> </a>
                </td>
            </tr>

        </table>

    </div>

</body>
</html>