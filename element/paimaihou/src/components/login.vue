<template>
	<el-tabs type="border-card" v-model="activeName">
		<el-tab-pane label="登录" name="login">
			<el-form ref="form" :model="form" label-width="80px">
				<el-form-item label="用户名">
					<el-input v-model="form.aid"></el-input>
				</el-form-item>
				<el-form-item label="密码">
					<el-input v-model="form.apwd" show-password></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit">登录</el-button>
				</el-form-item>
			</el-form>
		</el-tab-pane>
		<el-tab-pane label="注册" name="register">
			<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
				<el-form-item label="管理员账号" prop="aid">
					<el-input v-model="ruleForm.aid"></el-input>
				</el-form-item>
				<el-form-item label="管理员名字" prop="anmae">
					<el-input v-model="ruleForm.aname"></el-input>
				</el-form-item>
				<el-form-item label="管理员密码" prop="pass">
					<el-input v-model="ruleForm.apwd"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="secpwd">
					<el-input v-model="ruleForm.secpwd" ></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
					<el-button @click="resetForm('ruleForm')">重置</el-button>
				</el-form-item>
			</el-form>
		</el-tab-pane>
	</el-tabs>
</template>

<script>
	export default {
		data() {
			var validatePass = (rule, value, callback) => {
							        if (value === '') {
							          callback(new Error('请输入密码'));
							        } else {
							          if (this.ruleForm.secpwd !== '') {
							            this.$refs.ruleForm.validateField('checkPass');
							          }
							          callback();
							        }
							      };
			var validatePass2 = (rule, value, callback) => {
							if (value === '') {
							  callback(new Error('请再次输入密码'));
							} else if (value !== this.ruleForm.apwd) {
							  callback(new Error('两次输入密码不一致!'));
							} else {
								
							  callback();
							  
							}
			};
			return {
				activeName: 'login',
				form: {
					aid: '',
					apwd: ''
				},

				ruleForm: {
					aid: '',
					aname: '',
					apwd: '',
					rid: 0,
					secpwd: '',
				},
				
				
				rules: {
					
					pass: [
					            { validator: validatePass, trigger: 'blur' }
					          ],
					secpwd: [
					            { validator: validatePass2, trigger: 'blur' }
					          ],
					aid: [{
							required: true,
							message: '请输入管理员账号',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 12,
							message: '长度在 3 到 12 个字符',
							trigger: 'blur'
						}
					],

					aname: [{
							required: true,
							message: '请输入管理员名称',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 5,
							message: '长度在 3 到 5 个字符',
							trigger: 'blur'
						}
					],

					

				},

			}
		},
		methods: {
			onSubmit() {
				this.$axios.post("admin/login", this.form)
					.then(resp => {
						if (resp.data.code == 1) {
							this.$router.push("/index")
						} else {
							alert("用户名或密码错误")
						}
					})
			},
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$axios.post("admin/register",this.ruleForm)
						.then(resp=>{
							this.activeName = 'login';
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			}
		}
	}
</script>

<style>
</style>