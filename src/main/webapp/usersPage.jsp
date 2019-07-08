<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang_bundles.texts"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/tables.css"/>
    <link rel="stylesheet" href="css/buttons_and_forms.css"/>
    <link rel="stylesheet" href="css/popup_window.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title><fmt:message key="users"/> - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td colspan="2" class="tableTitle">
                    <strong><fmt:message key="users"/></strong>
                </td>
            </tr>
            <c:if test="${fn:length(users) == 0}">
            <tr class="no_entries" id="no_sections_${sectionBlock.id}">
                <td colspan="4">
                    <strong><fmt:message key="no_users"/></strong>
                </td>
            </tr>
            </c:if>
            <c:forEach var="user" items="${users}">
                <tr id="user_${user.login}">
                    <td class="user_entry">
                        <span class="float">
                        <a href="profile?login=${user.login}">
                            <strong>${user.login}</strong>
                        </a>
                        <c:if test="${sessionScope.user.role != 'ADMIN'}">
                            <a class="name">(<fmt:message key="${user.role}"/>) </a>
                        </c:if>
                        <c:if test="${user.reasonForBlocking != null}">
                            <strong><fmt:message key="banned"/></strong>
                        </c:if>
                        </span>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <form action="role" method="post">
                                <select name="role">
                                    <c:if test="${user.role == 'USER'}">
                                        <option value="USER" disabled selected><fmt:message key="USER"/></option>
                                        <option value="MODER"><fmt:message key="MODER"/></option>
                                        <option value="ADMIN"><fmt:message key="ADMIN"/></option>
                                    </c:if>
                                    <c:if test="${user.role == 'MODER'}">
                                        <option value="MODER" disabled selected><fmt:message key="MODER"/></option>
                                        <option value="USER"><fmt:message key="USER"/></option>
                                        <option value="ADMIN"><fmt:message key="ADMIN"/></option>
                                    </c:if>
                                    <c:if test="${user.role == 'ADMIN'}">
                                        <option value="ADMIN" disabled selected><fmt:message key="ADMIN"/></option>
                                        <option value="USER"><fmt:message key="USER"/></option>
                                        <option value="MODER"><fmt:message key="MODER"/></option>
                                    </c:if>
                                    <input name="login" value="${user.login}" type="hidden">
                                    <input type="submit" value="<fmt:message key="change"/>">
                                </select>
                            </form>
                        </c:if>
                    </td>
                    <c:if test="${sessionScope.user != null}">
                        <td>
                            <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MODER'}">
                                <c:if test="${user.reasonForBlocking == null}">
                                    <form class="float" action="#win_${user.login}">
                                        <button type="submit"><img src="resources/images/icons/lock.png" alt="image"/></button>
                                    </form>
                                    <c:if test="${user.login != sessionScope.user.login}">
                                        <div id="popup-window">
                                            <a href="#x" class="overlay" id="win_${user.login}"></a>
                                            <div class="popup">
                                                <form id="popupForm" method="post" action="lock">
                                                    <input name="login" value="${user.login}" type="hidden">
                                                    <textarea name="reason" maxlength="1000"
                                                              placeholder="<fmt:message key="reason_for_blocking"/> ${user.login}"
                                                              rows="8"></textarea>
                                                    <input type="submit" value="<fmt:message key="to_ban"/>"/>
                                                </form>
                                                <a class="close" title="<fmt:message key="cancel"/>" href="#close"></a>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:if>
                                <c:if test="${user.reasonForBlocking != null}">
                                    <form class="float" method="post" action="lock">
                                        <input name="login" value="${user.login}" type="hidden">
                                        <button type="submit"><img alt="image" src="resources/images/icons/unlock.png"/></button>
                                    </form>
                                </c:if>
                            </c:if>
                            <a href="http://localhost:8080/forum/profile?login=${user.login}#win1" title="<fmt:message key="write_message"/>">
                                <img src="resources/images/icons/message.png" alt="image"/></a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>

    </div>


</body>
</html>