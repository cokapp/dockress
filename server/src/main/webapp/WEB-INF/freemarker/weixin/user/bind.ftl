<#include "../_layout/layout.ftl"/>

<#macro body>

<form method="post">
	<div class="weui_cells">
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">微信用户</label>
			</div>
			<p class="weui_cell_bd small">${user.authUid}</p>
			<input type="hidden" name="authUid" value="${user.authUid}"/>
		</div>
	</div>

	<div class="weui_cells weui_cells_form">
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">用户名</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input type="text" name="username" placeholder="请输入用户名" class="weui_input" <#if user.username?exists> value="${user.username}" readonly="true"</#if> />
			</div>
		</div>
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">昵称</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input type="text" name="nickname" placeholder="请输入昵称" class="weui_input" <#if user.nickname?exists> value="${user.nickname}"</#if>/>
			</div>
		</div>
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">密码</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input type="password" name="password" placeholder="请输入密码" class="weui_input"/>
			</div>
		</div>
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">重复密码</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input type="password" name="password2" placeholder="请再次输入密码" class="weui_input"/>
			</div>
		</div>
	</div>
	<div class="weui_btn_area">
		<button type="submit" class="weui_btn weui_btn_primary">提交</button>
	</div>
</form>

</#macro>