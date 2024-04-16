<template>
	<el-container>
		<el-header>Header</el-header>
		<el-container>
			<el-aside width="200px">
				<el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
					background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :router="true">
					<el-submenu index="1">
						<template slot="title">
							<i class="el-icon-location"></i>
							<span>拍卖管理</span>
						</template>

						<el-menu-item index="/index/shenhe">审核</el-menu-item>

					</el-submenu>
					<el-submenu index="2">
						<template slot="title">
							<i class="el-icon-location"></i>
							<span>用户管理</span>
						</template>
					
						<el-menu-item index="/index/user">管理员</el-menu-item>
					
					</el-submenu>

				</el-menu>
			</el-aside>
			<el-main><router-view></router-view></el-main>
		</el-container>
	</el-container>
</template>


<script>
	export default {
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			}
		}
	}
</script>

<style>
	.el-header,
	.el-footer {
		background-color: #B3C0D1;
		color: #333;
		text-align: center;
		line-height: 60px;
	}

	.el-aside {
		background-color: #D3DCE6;
		color: #333;
		text-align: center;
		line-height: 200px;
	}

	.el-main {
		background-color: #E9EEF3;
		color: #333;
		text-align: center;
		line-height: 160px;
	}

	body>.el-container {
		margin-bottom: 40px;
	}

	.el-container:nth-child(5) .el-aside,
	.el-container:nth-child(6) .el-aside {
		line-height: 260px;
	}

	.el-container:nth-child(7) .el-aside {
		line-height: 320px;
	}
</style>

<style>
</style>