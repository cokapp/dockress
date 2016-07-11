<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>登录成功</title>
</head>
<body>

	<script>
		var info = {
			type: 'oauth',
			success: true,
			access_token: '${access_token}'
		};
		window.opener.postMessage(JSON.stringify(info), '*');
		window.close();
	</script>
</body>
</html>