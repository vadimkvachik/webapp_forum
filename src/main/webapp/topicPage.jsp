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
    <script src="js/EntryEditor.js"></script>
    <title>${topic.name} - <fmt:message key="head"/></title>

    <c:if test="${sessionScope.user.role == 'USER'}">
        <script src="js/DeleteAllButtonsExceptLast.js"></script>
    </c:if>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table>
            <tr>
                <td class="tableTitle" colspan="2">
                    <a href="section?id=${topic.section.id}">
                        <img src="resources/images/icons/back.png" alt="back"></a>
                    <strong>${topic.name}</strong>
                    <c:forEach var="page" items="${pages}">
                        <c:choose>
                            <c:when test="${page == currentPage}">
                                <a class="yellowLinks" href="topic?id=${topic.id}&page=${page}"> ${page}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="whiteLinks" href="topic?id=${topic.id}&page=${page}"> ${page}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
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
                <tr class="tableHead">
                    <td class="main_entry" colspan="2">${message.dateOfPublication}</td>
                </tr>
                <tr class="message" id="id${message.id}">
                    <td class="message_user">
                        <a href="profile?login=${message.user.login}">
                            <strong>${message.user.login}</strong><br>
                        </a>
                        <c:choose>
                            <c:when test="${message.user.reasonForBlocking != null}">
                                <fmt:message key="banned"/><br>
                            </c:when>
                            <c:when test="${message.user.deleted}">
                                <fmt:message key="deleted"/><br>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="${message.user.role}"/> <br>
                                <fmt:message key="name"/>: ${message.user.name}<br>
                                <fmt:message key="registration_word"/>: ${message.user.dateOfRegistration} <br>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MODER' ||
                        sessionScope.user.name == message.user.name}">
                            <form class="delete_button" action="delete_message" method="post">
                                <input type="hidden" name="id" value="${message.id}">
                                <input type="hidden" name="page" value="${currentPage}">
                                <input name="numberOfMessages" value="${fn:length(messages)}" type="hidden">
                                <input type="submit" value="X"/>
                            </form>
                            <button class="edit_button" onclick="showEditForm('id' + '${message.id}')">
                                <img src="resources/images/icons/edit.png" alt="image"></button>
                        </c:if>
                    </td>
                    <td class="message_text">
                        <p class="name">${message.text}</p>
                        <form class="edit_form" action="change_message" method="post">
                            <input type="hidden" name="id" value="${message.id}">
                            <input type="hidden" name="page" value="${currentPage}">
                            <textarea name="new_text" class="new_text" rows="7"></textarea> <br/>
                            <input type="submit" value="<fmt:message key="save"/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td class="tableTitle" colspan="2">
                    <a href="section?id=${topic.section.id}">
                        <img src="resources/images/icons/back.png" alt="back"></a>
                    <strong>${topic.name}</strong>
                    <c:forEach var="page" items="${pages}">
                        <c:choose>
                            <c:when test="${page == currentPage}">
                                <a class="yellowLinks" href="topic?id=${topic.id}&page=${page}"> ${page}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="whiteLinks" href="topic?id=${topic.id}&page=${page}"> ${page}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </td>
            </tr>
            <c:if test="${sessionScope.user != null}">
                <tr>
                    <td class="tableTitle" colspan="2">
                        <form id="new_message" method="post" action="add_message">
                            <input name="topic_id" value="${topic.id}" type="hidden">
                            <textarea name="message_text" maxlength="5000" placeholder="<fmt:message key="enter_text"/>" rows="8" required></textarea>
                            <input type="submit" value="<fmt:message key="send"/>"/>
                        </form>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
</body>



