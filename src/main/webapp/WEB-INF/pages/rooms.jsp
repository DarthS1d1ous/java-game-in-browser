<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="rooms" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Game">
    <table>
        <thead>
        <tr>
            <td>№</td>
            <td>
                Name
            </td>
        </tr>
        </thead>
        <c:forEach var="room" items="${rooms}">
            <tr>
                <td>
                    ${room.id}
                </td>
                <td>
                   ${room.name}
                </td>
                <td>
                    <button><a href="${pageContext.servletContext.contextPath}/room/${room.id}">Join</a></button>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>