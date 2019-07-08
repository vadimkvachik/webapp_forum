<%--Main Page--%>
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
    <link rel="stylesheet" href="css/buttons_and_forms.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/popup_window.css"/>
    <link rel="stylesheet" href="css/tables.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <script src="js/DeleteElementById.js"></script>
    <script src="js/EntryEditor.js"></script>
    <title><fmt:message key="head"/></title>
</head>

<body>
    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <%--Table "no section blocks" if they are not--%>
        <c:if test="${fn:length(sectionBlockList) == 0}">
            <table id="no_blocks_table">
                <tr>
                    <td class="tableTitle"></td>
                </tr>
                <tr class="no_entries">
                    <td>
                        <strong><fmt:message key="forum_is_empty"/></strong>
                    </td>
                </tr>
            </table>
        </c:if>
        <%--Insert block sections from the database--%>
        <c:forEach var="sectionBlock" items="${sectionBlockList}">
            <table>
                <tr id="sectionBlock${sectionBlock.id}">
                    <td class="tableTitle" colspan="4">
                            <%--Buttons sees only admin--%>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <%--Remove block button--%>
                            <form class="delete_button" action="delete_block" method="post">
                                <input type="hidden" name="block_id" value="${sectionBlock.id}">
                                <input type="submit" value="X"/>
                            </form>
                            <%--Rename block button--%>
                            <button class="edit_button" onclick="showEditForm('sectionBlock' + ${sectionBlock.id})">
                                <img src="resources/images/icons/edit.png" alt="image"></button>
                            <form action="rename_block" method="post" class="edit_form">
                                <input type="hidden" name="id" value="${sectionBlock.id}">
                                <input type="text" name="new_name" class="new_text" placeholder="<fmt:message key="new_name"/>" maxlength="40"
                                       required>
                            </form>
                        </c:if>
                            <%--Block name--%>
                        <strong class="name">${sectionBlock.name}</strong>
                    </td>
                </tr>
                    <%--Section list signatures--%>
                <tr class="tableHead">
                    <td class="main_entry"><fmt:message key="sections"/></td>
                    <td class="count_entry"><fmt:message key="topics"/></td>
                    <td class="count_entry"><fmt:message key="messages"/></td>
                    <td class="last_message_entry"><fmt:message key="lastMessage"/></td>
                </tr>
                    <%--Table "no sections" if they are not--%>
                <tr class="no_entries" id="no_sections_${sectionBlock.id}">
                    <td colspan="4">
                        <strong><fmt:message key="no_sections"/></strong>
                    </td>
                </tr>
                    <%--Insert the sections of the current block from the database--%>
                <c:forEach var="sectionEntry" items="${sectionEntries}">
                    <c:if test="${sectionEntry.section.sectionBlock eq sectionBlock}">
                        <script> del("no_sections_${sectionBlock.id}"); </script>
                        <tr class="entry_forum" id="section${sectionEntry.section.id}">
                            <td class="main_entry">
                                    <%--Buttons sees only admin--%>
                                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                    <%--Remove section button--%>
                                    <form class="delete_button" action="delete_section" method="post">
                                        <input type="hidden" name="sectionId" value="${sectionEntry.section.id}">
                                        <input type="submit" value="X"/>
                                    </form>
                                    <%--Rename section button--%>
                                    <button class="edit_button"
                                            onclick="showEditForm('section' + ${sectionEntry.section.id}, '${sectionEntry.section.name}')">
                                        <img src="resources/images/icons/edit.png" alt="image"></button>
                                    <form action="rename_section" method="post" class="edit_form">
                                        <input type="hidden" name="id" value="${sectionEntry.section.id}">
                                        <input type="text" name="new_name" class="new_text" maxlength="40"
                                               placeholder="<fmt:message key="new_name"/>" required>
                                    </form>
                                </c:if>
                                    <%--Section name--%>
                                <a href="section?id=${sectionEntry.section.id}" class="name"><strong>${sectionEntry.section.name}</strong></a>
                            </td>
                                <%--Section Information--%>
                            <td class="count_entry">${sectionEntry.numberOfTopics}</td>
                            <td class="count_entry">${sectionEntry.numberOfMessages}</td>
                            <td class="last_message_entry">
                                <c:if test="${sectionEntry.numberOfMessages == 0}">
                                    <fmt:message key="noMessages"/>
                                </c:if>
                                <c:if test="${sectionEntry.numberOfMessages > 0}">
                                    ${sectionEntry.lastMessage.dateOfPublication} <fmt:message key="from"/>
                                    <a href="profile?login=${sectionEntry.lastMessage.user.login}">
                                        <b> ${sectionEntry.lastMessage.user.login}</b> </a> <br/>
                                    <fmt:message key="inTopic"/> <a href="topic?id=${sectionEntry.lastMessage.topic.id}">
                                    <b> ${sectionEntry.lastMessageTopicNamePreview} </b> </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                <tr>
                    <td class="tableTitle" colspan="4">
                            <%--Sees only admin--%>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <%--Section add form--%>
                            <form action="add_section" method="post" class="add_form">
                                <input type="hidden" name="sectionBlock" value="${sectionBlock.id}">
                                <input type="text" name="sectionName" maxlength="40" placeholder="<fmt:message key="new_section_name"/>" required>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </table>
        </c:forEach>

        <%--Sees only admin--%>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <table>
                <tr>
                    <td class="tableTitle">
                            <%--Section block add form--%>
                        <form action="add_section_block" method="post" class="add_form">
                            <input type="text" name="sectionBlockName" maxlength="40" placeholder="<fmt:message key="new_block_name"/>" required>
                        </form>
                    </td>
                </tr>
            </table>
        </c:if>

        <div class="empty"></div>
    </div>


</body>
</html>