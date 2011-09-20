<%@page contentType="application/pdf" trimDirectiveWhitespaces="true"%>
<%
    response.getOutputStream().write((byte[]) request.getAttribute(
            "pdfArray"));
%>
