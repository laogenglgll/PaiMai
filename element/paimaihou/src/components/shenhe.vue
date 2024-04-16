<template>
  <div>
	  <el-table
	    
	    :data="tableData"
	    
	    style="width: 100%"
	    @selection-change="handleSelectionChange">
	    <el-table-column
	      type="selection"
	      width="55">
	    </el-table-column>
	    <el-table-column
	      label="拍品名称"
	      width="120"
		  prop="gname">
	    </el-table-column>
	    <el-table-column
	      prop="type.tname"
	      label="拍品类型"
	      width="120">
	    </el-table-column>
	    <el-table-column
	      prop="bprice"
	      label="基本价格"
	      show-overflow-tooltip>
	    </el-table-column>
		<el-table-column label="操作">
		      <template slot-scope="scope">
		        <el-button
		          size="mini"
		          @click="handleEdit(scope.$index, scope.row)">审核</el-button>
		        <el-button
		          size="mini"
		          type="danger"
		          @click="handleDelete(scope.$index,scope.row)">删除</el-button>
		      </template>
		    </el-table-column>
	  </el-table>
	     <el-pagination
	      
	       @current-change="handleSelectionChange"
		   background
	       :page-size="2"
	       layout="prev,pager,next"
	       :total="total">
	     </el-pagination>
	   
	 
	 <el-dialog title="审核" :visible.sync="dialogFormvisble">
		 <el-form :model="form">
		   <el-form-item label="拍品名称" :label-width="formLabelWidth">
		     <el-input v-model='form.gname' autocomplete="off"></el-input>
		   </el-form-item>
		   <el-form-item label="拍品类型" :label-width="formLabelWidth">
		     <el-input v-model='form.type.tname' autocomplete="off"></el-input>
		   </el-form-item>
		   <el-form-item label="图片" :label-width="formLabelWidth" >
		     <img style="width: 100px;height: 100px;" :src="'http://localhost:9090/'+form.gpic " width="500px"/>
		   </el-form-item>
		   <el-form-item label="起步价" :label-width="formLabelWidth">
		     <el-input v-model='form.bprice' autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="正涨价" :label-width="formLabelWidth">
			  <el-input v-model='form.increase' autocomplete="off"></el-input>
		   </el-form-item>
		 </el-form>
		 <div slot="footer" class='dialog-footer'>
			 <el-button @click="shenhe(2,form.aid)">取消</el-button>
			 <el-button type="primary" @click="shenhe(3,form.aid)">确定</el-button>
		 </div>
	 </el-dialog>
  </div>
</template>

<script>
  export default {
	  beforeMount(){
		  this.$axios.get("auction/list?pageno=1&state=1")
		  .then(resp=>{
			  this.tableData = resp.data.records;
		  })
	  },
	  
    data() {
      return {
		  current:1,
		  total:0,
		  tableData:[],
        multipleSelection: [],
		
		dialogFormvisble:false,
		form:{
			gname:'',
			tid:'',
			bprice:0,
			abmoney:0,
			increase:0,
			stime:'',
			etime:'',
			type:{
				tid:'',
				tname:'',
			},
			
		},
		formLabelWidth:'120px',
      }
    },

    methods: {	  
	 query(){
		 this.$axios.get("auction/list?state=1&pageno="+this.current)
		 .then(resp=>{
		 	this.tableData = resp.data.records;
		 	this.total = resp.data.total;
		 	this.current = resp.data.current;
		 })
	 },
	 change(){
		 this.query();
	 },
      handleSelectionChange(val) {
		this.$axios.get("auction/list?state=1&pageno="+val)
		.then(resp=>{
			this.tableData = resp.data.records;
			this.total = resp.data.total;
			this.pageno = resp.data.pageno;
		})
      },
	  
	   handleEdit(index, row) {
	        console.log(index, row);
			  this.dialogFormvisble = true;
			  this.form = row;
			  console.log("============================================"+this.form.gpic)
	        },
		handleDelete(index, row) {
	          console.log(index, row);
	        },
		
		shenhe(state,aid){
			alert(aid);
			this.$axios.get("auction/shenhe?aid="+aid+"&state="+state)
			.then(resp=>{
				alert(resp.data.msg);
				this.dialogFormvisble = false;
				this.query();
			})
		}
    }
  }
</script>
<style>
</style>