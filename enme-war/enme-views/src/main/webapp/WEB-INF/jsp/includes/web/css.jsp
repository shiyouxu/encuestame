<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/css-common.jsp"%>
<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/dev/resources.css" />" />
</c:if>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dbootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dijit.css" />" />
<tiles:insertAttribute name="css_module" ignore="true" />