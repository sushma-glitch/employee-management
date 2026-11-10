import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './employee.model';
@Injectable({providedIn:'root'})
export class EmployeeService {
 private http=inject(HttpClient);
 private api='http://localhost:8080/api/employees';
 getAll(department=''):Observable<Employee[]>{let params=new HttpParams();if(department.trim())params=params.set('department',department.trim());return this.http.get<Employee[]>(this.api,{params});}
 create(e:Employee):Observable<Employee>{return this.http.post<Employee>(this.api,e);}
 update(id:number,e:Employee):Observable<Employee>{return this.http.put<Employee>(`${this.api}/${id}`,e);}
 delete(id:number):Observable<void>{return this.http.delete<void>(`${this.api}/${id}`);}
}
