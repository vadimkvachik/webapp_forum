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
    <link rel="stylesheet" href="css/login_page_css/login_page_utils.css"/>
    <link rel="stylesheet" href="css/login_page_css/login_page.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title><fmt:message key="login"/> - <fmt:message key="head"/></title>
</head>

<body>

    <jsp:include page="WEB-INF/jsp/Header.jsp"/>

    <div id="forumBody">
        <table>
            <tr>
                <td class="tableTitle" colspan="5"></td>
            </tr>
            <tr>
                <td>
                    <div class="container-login100">
                        <div class="wrap-login100 p-t-50 p-b-90">
                            <form class="login100-form validate-form flex-sb flex-w" action="login" method="post">
                                <span class="login100-form-title p-b-51">
                                    <h7> <fmt:message key="login_pass_wrong"/> <br>
                                     <a href="#win1"><fmt:message key="forget_pass"/></a> </h7>
                                </span>
                                <div class="wrap-input100 validate-input m-b-16" data-validate="Username is required">
                                    <input class="input100" type="text" name="login" placeholder="<fmt:message key="login"/>"
                                           autocomplete="off" required>
                                    <span class="focus-input100"></span>
                                </div>
                                <div class="wrap-input100 validate-input m-b-16" data-validate="Password is required">
                                    <input class="input100" type="password" name="password" placeholder="<fmt:message key="pass"/>" required>
                                    <span class="focus-input100"></span>
                                </div>
                                <div class="container-form m-t-17">
                                    <button class="login100-form-btn">
                                        <fmt:message key="enter"/>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div id="popup-window">
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <h2>
                <fmt:message key="enter_email_message"/>
            </h2>
            <form id="popupForm" method="post" action="pass_recovery">
                <label>
                    <input name="email" type="text" maxlength="40" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                           placeholder="<fmt:message key="email"/>"/>
                </label>
                <input type="submit" value="<fmt:message key="send"/>"/>
            </form>
            <a class="close" title="<fmt:message key="cancel"/>" href="#close"></a>
        </div>
    </div>

</body>
</html>