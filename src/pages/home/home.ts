import { Component, ViewChild  } from '@angular/core';
import { NavController } from 'ionic-angular';
import {Auth} from '../../providers/auth';
import {LoginPage} from '../login/login';
import {Latest} from '../latestTestResults/latest';
import {Upcoming} from '../upcomingTest/upcoming';
import {Test1} from '../test1/test1';
import {Test2} from '../test2/test2';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})

export class HomePage {

  username = '';
  email= '';

  constructor(public navCtrl: NavController, private auth: Auth) {
    let info = this.auth.getUserInfo();
    this.username = info.name;
  }

  public logout(){
    this.auth.logout().subscribe(succ => {
      //this.navCtrl.popAll();
      this.navCtrl.setRoot(LoginPage);
    })
  }

  test1(){
    this.navCtrl.push(Test1)
  }

   test2(){
    this.navCtrl.push(Test2)
  }

  send1(){

    this.navCtrl.push(Latest)
  }
  send2(){

    this.navCtrl.push(Upcoming)
  }

}
