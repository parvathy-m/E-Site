import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserLayoutComponent } from './component/layout/user-layout/user-layout.component';
import { HomeComponent } from './component/page/home/home.component';



@NgModule({
  declarations: [
    UserLayoutComponent,
    HomeComponent
  ],
  imports: [
    CommonModule
  ]
})
export class UserModule { }
