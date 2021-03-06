import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {Auth} from '../../providers/auth';
import {LoginPage} from '../login/login';

@Component({
  selector: 'page-test1',
  templateUrl: 'test1.html'
})

export class Test1 {
  

  constructor(private navCtrl: NavController, private auth: Auth) {

  }

  public logout(){
    this.auth.logout().subscribe(succ => {
      this.navCtrl.setRoot(LoginPage)
    })
  }

}