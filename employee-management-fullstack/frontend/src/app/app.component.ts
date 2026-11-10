import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Employee } from './employee.model';
import { EmployeeService } from './employee.service';
@Component({selector:'app-root',standalone:true,imports:[CommonModule,CurrencyPipe,FormsModule,ReactiveFormsModule],templateUrl:'./app.component.html'})
export class AppComponent implements OnInit {
 private api=inject(EmployeeService); private fb=inject(FormBuilder);
 employees:Employee[]=[]; loading=false; error=''; success=''; filterDepartment=''; editingId:number|null=null;
 form=this.fb.nonNullable.group({firstName:['',[Validators.required,Validators.maxLength(100)]],lastName:['',[Validators.required,Validators.maxLength(100)]],email:['',[Validators.required,Validators.email]],department:['',[Validators.required]],salary:[0,[Validators.required,Validators.min(0)]]});
 ngOnInit(){this.loadEmployees();}
 loadEmployees(){this.loading=true;this.error='';this.api.getAll(this.filterDepartment).subscribe({next:data=>{this.employees=data;this.loading=false;},error:err=>{this.error=err?.error?.detail??'Could not load records. Check backend, MySQL and Redis.';this.loading=false;}});}
 submit(){this.success='';this.error='';if(this.form.invalid){this.form.markAllAsTouched();return;}const e=this.form.getRawValue();const request=this.editingId===null?this.api.create(e):this.api.update(this.editingId,e);request.subscribe({next:()=>{this.success=this.editingId===null?'Employee created successfully.':'Employee updated successfully.';this.resetForm();this.loadEmployees();},error:err=>this.error=err?.error?.detail??'Save failed. Check the data and backend.'});}
 edit(e:Employee){if(e.id==null)return;this.editingId=e.id;this.form.setValue({firstName:e.firstName,lastName:e.lastName,email:e.email,department:e.department,salary:e.salary});this.success='';this.error='';window.scrollTo({top:0,behavior:'smooth'});}
 remove(e:Employee){if(e.id==null||!confirm(`Delete ${e.firstName} ${e.lastName}?`))return;this.api.delete(e.id).subscribe({next:()=>{this.success='Employee deleted.';this.loadEmployees();},error:err=>this.error=err?.error?.detail??'Delete failed.'});}
 resetForm(){this.editingId=null;this.form.reset({firstName:'',lastName:'',email:'',department:'',salary:0});}
}
