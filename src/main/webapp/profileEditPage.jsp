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
    <link rel="stylesheet" href="css/registration_page.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title><fmt:message key="profile_editing"/> ${login} - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle" colspan="5"><fmt:message key="profile_editing"/> <strong> ${login} </strong></td>
            </tr>
            <tr>
                <td>
                    <form class="contact_form" action="edit_profile" method="post">
                        <input type="hidden" name="login" value="${login}"/>
                        <ul>
                            <li>
                                <label for="name"><fmt:message key="name"/>:</label>
                                <input id="name" name="new_name" type="text" value="${name}" maxlength="20" placeholder="${userLogins}" required/>
                            </li>
                            <li>
                                <label for="phone"><fmt:message key="phone"/>:</label>
                                <input id="phone" type="text" name="new_phone" value="${phone}" maxlength="20" pattern="(\+?\d[- .]*){7,13}"
                                       required/>
                            </li>
                            <li>
                                <label for="description"><fmt:message key="about_me"/>:</label>
                                <textarea id="description" name="new_description" cols="40" rows="6" maxlength="1000"
                                          required>${description}</textarea>
                            </li>
                            <li>
                                <button class="submit" type="submit"><fmt:message key="save"/></button>
                            </li>
                        </ul>
                    </form>
                    <form class="contact_form" action="change_password" method="post">
                        <input type="hidden" name="login" value="${login}"/>
                        <ul>
                            <li>
                                <label for="pass"><fmt:message key="old_pass"/>:</label>
                                <input id="old_pass" name="old_password" type="password" minlength="5" maxlength="20" required/>
                            </li>
                            <li>
                                <label for="pass"><fmt:message key="new_pass"/>:</label>
                                <input id="pass" name="new_password" type="password" minlength="5" maxlength="20" required/>
                            </li>
                            <li>
                                <button class="submit" type="submit"><fmt:message key="save"/></button>
                            </li>
                        </ul>
                    </form>
                </td>
            </tr>

        </table>

    </div>


</body>
</html>