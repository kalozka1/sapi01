<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1><spring:message code="label.storyPoint.list.page.title"/></h1>
    <div>
        <a href="/storyPoint/add" id="add-button" class="btn btn-primary"><spring:message code="label.add.storyPoint.link"/></a>
    </div>
    <div id="storyPoint-list" class="page-content">
        <c:choose>
            <c:when test="${empty storyPoints}">
                <p><spring:message code="label.storyPoint.list.empty"/></p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${ storyPoints }" var="storyPoint">
                    <div class="well well-small">
                        <a href="/storyPoint/${storyPoint.id}"><c:out value="title"/></a>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>