<template>
	<div>
		<el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column label="编号" width="120" prop="aid">
			</el-table-column>
			<el-table-column label="姓名" width="120" prop="aname">
			</el-table-column>
			<el-table-column prop="rid" label="角色" width="120" :formatter="state">
			</el-table-column>
		
			<el-table-column prop="abackup" label="备注" show-overflow-tooltip>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="mini" @click="update(scope.$index, scope.row)">修改</el-button>
					<el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @current-change="handleSelectionChange" background :page-size="2" layout="prev,pager,next"
			:total="total">
		</el-pagination>


		<el-dialog title="审核" :visible.sync="dialogFormvisble">
			<el-form :model="form">
				<el-form-item label="管理员名称" :label-width="formLabelWidth">
					<el-input v-model='form.aname' autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="1为启用2为未启用" :label-width="formLabelWidth">
					<el-input v-model='form.rid' autocomplete="off" ></el-input>
				</el-form-item>
				<el-form-item label="备注" :label-width="formLabelWidth">
					<el-input v-model='form.abackup' autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class='dialog-footer'>
				<el-button @click="sub(form)">确定</el-button>
				<el-button type="primary" @click="quxiaoS">取消</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		beforeMount() {
			this.$axios.get("admin/list?pageno=1")
				.then(resp => {
					this.tableData = resp.data.records;
					this.total = resp.data.total;
					this.current = resp.data.current;
				})
		},

		data() {
			return {
				
				current: 1,
				total: 0,
				tableData: [],
				multipleSelection: [],

				dialogFormvisble: false,
				form: {

				},
				formLabelWidth: '120px',
			}
		},

		methods: {
			state(row,colum){
				if(row.rid == 1){
					return "启用";
				}else{
					return "未启用";
				}
			},
			query() {
				this.$axios.get("admin/list?pageno=" + this.current)
					.then(resp => {
						this.tableData = resp.data.records;
						this.total = resp.data.total;
						this.current = resp.data.current;
					})
			},
			change() {
				this.query();
			},
			handleSelectionChange(val) {
				this.$axios.get("admin/list?pageno=" + val)
					.then(resp => {
						this.tableData = resp.data.records;
						this.total = resp.data.total;
						this.current = resp.data.current;
					})
			},

			update(index, row) {
				alert(row.rid);
				this.dialogFormvisble = true;
				this.form = row;
			},
			handleDelete(index, row) {
				console.log(index, row);
				this.$axios.get("admin/delete?aid=" + this.form.aid)
					.then(resp => {
						if (resp.data.code == 1) {
							alert("删除成功");
						} else {
							alert("删除失败");
						}
					})
			},

			sub(from) {
				alert(from.aid);
				this.$axios.post("admin/update",from)
					.then(resp => {
						alert(ok);
						this.dialogFormvisble = false;
					})
			},
			quxiaoS() {
				this.dialogFormvisble = false;
			}
		}
	}
</script>

<style>
</style>