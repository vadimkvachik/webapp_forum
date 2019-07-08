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
    <title>${section.name} - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table>
            <tr>
                <td class="tableTitle" colspan="4"><strong>${section.name}&#8195;</strong>
                    <c:if test="${sessionScope.user != null}">
                        <a class="whiteLinks" href="#win1"><fmt:message key="create_topic"/></a>
                    </c:if>
                </td>
            </tr>
            <tr class="tableHead">
                <td class="main_entry"><fmt:message key="topics"/></td>
                <td class="count_entry"><fmt:message key="messages"/></td>
                <td class="count_entry"><fmt:message key="created"/></td>
                <td class="last_message_entry"><fmt:message key="lastMessage"/></td>
            </tr>
            <c:if test="${fn:length(topicEntries) == 0}">
                <tr class="no_entries" id="no_topics">
                    <td colspan="4">
                        <strong><fmt:message key="no_topics"/></strong>
                    </td>
                </tr>
            </c:if>
            <c:forEach var="topicEntry" items="${topicEntries}">
                <tr class="entry_forum" id="topic${topicEntry.topic.id}">
                    <td class="main_entry">
                        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MODER'}">
                            <form class="delete_button" action="delete_topic" method="post">
                                <input type="hidden" name="topicId" value="${topicEntry.topic.id}">
                                <input type="submit" value="X"/>
                            </form>
                            <button class="edit_button" onclick="showEditForm('topic' + ${topicEntry.topic.id})">
                                <img src="resources/images/icons/edit.png" alt="image"></button>
                            <form action="rename_topic" method="post" class="edit_form">
                                <input type="hidden" name="id" value="${topicEntry.topic.id}">
                                <input type="text" class="new_text" name="new_name" maxlength="40" placeholder="<fmt:message key="new_name"/>"
                                       required>
                            </form>
                        </c:if>
                        <a class="name" href="topic?id=${topicEntry.topic.id}"><strong>${topicEntry.topic.name}</strong></a>
                    </td>
                    <td class="count_entry">${topicEntry.numberOfMessages}</td>
                    <td class="count_entry">${topicEntry.topic.dateOfCreation}</td>
                    <td class="last_message_entry">
                        <c:if test="${topicEntry.numberOfMessages == 0}">
                            <fmt:message key="noMessages"/>
                        </c:if>
                        <c:if test="${topicEntry.numberOfMessages > 0}">
                            ${topicEntry.lastMessage.dateOfPublication} <br>
                            <a href="profile?login=${topicEntry.lastMessage.user.login}">
                                <b>${topicEntry.lastMessage.user.login}:</b> </a>
                            ${topicEntry.lastMessagePreview}
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td class="tableTitle" colspan="4"><strong>${section.name}&#8195;</strong>
                    <c:if test="${sessionScope.user != null}">
                        <a class="whiteLinks" href="#win1"><fmt:message key="create_topic"/></a>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>

    <div id="popup-window">
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <h2>
                <fmt:message key="create_topic_in_section"/>
            </h2>
            <h1>${section.name}</h1>
            <form id="popupForm" method="post" action="add_topic">
                <input name="section_id" value="${section.id}" type="hidden">
                <label>
                    <input name="topic_name" type="text" maxlength="40" placeholder="<fmt:message key="topic_name"/>" required/>
                </label>
                <textarea name="message_text" maxlength="5000" placeholder="<fmt:message key="first_message"/>" rows="8" required></textarea>
                <input type="submit" value="<fmt:message key="create"/>"/>
            </form>
            <a class="close" title="<fmt:message key="cancel"/>" href="#close"></a>
        </div>
    </div>


</body>



