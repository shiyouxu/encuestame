<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/project.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/project.jsp"%>
  </c:if>