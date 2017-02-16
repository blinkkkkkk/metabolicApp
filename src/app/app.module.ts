import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import {LoginPage} from '../pages/login/login';
import {Auth} from '../providers/auth';
import {RegisterPage} from '../pages/register/register';
import { HomePage } from '../pages/home/home';
import { Latest } from '../pages/latestTestResults/latest';
import { Upcoming } from '../pages/upcomingTest/upcoming';
import { Test1 } from '../pages/test1/test1';
import { Test2 } from '../pages/test2/test2';
// import { TabsPage } from '../pages/tabs/tabs';



@NgModule({
  declarations: [
    MyApp,
    HomePage,
    LoginPage,
    RegisterPage,
    Latest,
    Upcoming,
    Test1,
    Test2
    // TabsPage
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    LoginPage,
    RegisterPage,
    Latest,
    Upcoming,
    Test1,
    Test2
    // TabsPage
  ],
  providers: [Auth]
})
export class AppModule {}
