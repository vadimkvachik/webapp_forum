<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang_bundles.texts"/>

<div id="header">
    <div id="logo">
        <a href="index"> <img src="resources/images/logo.png" alt="image"> </a>
    </div>
    <div id="description">
        <h1><fmt:message key="head"/></h1>
        <p><fmt:message key="description"/></p>
    </div>
    <div id="user_block">
        <c:if test="${sessionScope.user != null}">
            <div id="userPanel">
                <p><fmt:message key="login_info"/> <br/> <strong>${sessionScope.user.login}</strong></p>
                <ul>
                    <li><a href="profile?login=${sessionScope.user.login}"><fmt:message key="my_profile"/></a></li>
                    <li><a href="private_in"><fmt:message key="private"/>
                        <c:if test="${number_of_unread_messages > 0}">
                        <b>(${number_of_unread_messages}) </c:if> </b></a></li>
                    <li><a href="articles#win1"><fmt:message key="write_article"/></a></li>
                    <li><a href="logout"><fmt:message key="exit"/></a></li>
                </ul>
            </div>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <form id="authorizationForm" method="post" action="login">
                <div class="login_form">
                    <input id="login" name="login" class="inputs" placeholder="<fmt:message key="login"/>" type="text" required/> <br/>
                    <input id="password" name="password" class="inputs" placeholder="<fmt:message key="pass"/>" type="password" required/> <br/>
                    <input id="submitButton" type="submit" value="<fmt:message key="enter"/>"/>
                </div>
            </form>
        </c:if>
    </div>
    <div id="languagePanel">
        <c:choose>
            <c:when test="${sessionScope.lang == 'ru'}">
                <a href="locale?lang=en&page=/index"><img alt="image" src="resources/images/icons/lang_eng.jpg"></a>
            </c:when>
            <c:otherwise>
                <a href="locale?lang=ru&page=/index"><img alt="image" src="resources/images/icons/lang_rus.jpg"></a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div id="buttonPanel">
    <table id="buttonTable">
        <tr>
            <td class="table_button"><a href="index"><fmt:message key="main"/> </a></td>
            <td class="table_button"><a href="#rules"><fmt:message key="rules"/> </a></td>
            <td class="table_button"><a href="users"><fmt:message key="users"/></a></td>
            <td class="table_button"><a href="articles"><fmt:message key="articles"/> </a></td>
            <c:if test="${sessionScope.user == null}">
                <td class="table_button"><a href="reg"><fmt:message key="registration"/></a></td>
            </c:if>
        </tr>
    </table>
</div>

<div id="popup-window">
    <a href="#x" class="overlay" id="rules"></a>
    <div class="popup">
        <jsp:include page="rules_${sessionScope.lang}.jsp"/>
        <a class="close" title="<fmt:message key="cancel"/>" href="#close"></a>
    </div>
</div>

