<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<%@ attribute name="value" required="true" 
              type="java.time.temporal.TemporalAccessor" %>
<%@ attribute name="pattern" type="java.lang.String" %>

<%
    if (pattern == null) pattern = "yyyy-MM-dd";
%>

<%
    if (value == null) {
%>
    <!-- value가 null일 때 보여줄 내용 -->
    <%= "" %>
<%
    } else {
%>
    <%= DateTimeFormatter.ofPattern(pattern).format(value) %>
<%
    }
%>
