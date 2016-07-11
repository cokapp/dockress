<#assign basePath = request.contextPath />

<!DOCTYPE html>
<html>
<head>
	<#include "../_public/head.ftl" />
	<#if head??>
	<@head/>
	</#if>
</head>
<body ontouchstart>

    <div class="container js_container">
        <div class="page">
            <div class="hd">
                <h1 class="page_title">${pageMeta.title}</h1>
                <p class="page_desc">${pageMeta.description}</p>
            </div>
            <div class="bd">
				<#if body??>
				<@body/>
				</#if>				
            </div>
        </div>
    </div>

	<#include "../_public/foot.ftl" />
	<#if foot??>
	<@foot/>
	</#if>
</body>
</html>