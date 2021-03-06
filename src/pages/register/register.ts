import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import {Auth} from '../../providers/auth';
import {LoginPage} from '../login/login';

@Component({
  selector: 'page-register',
  templateUrl: 'register.html'
})

export class RegisterPage {
  createSuccess: boolean;
  registerCredentials = {
    name: '', 
    NHS: '',
    DateOfBirth: '',
    email: '',
    password: ''
}

  constructor(private navCtrl: NavController, private auth: Auth, 
              private alertCtrl: AlertController ) {}
  
  public register(){
    this.auth.register(this.registerCredentials).subscribe(success =>{
      if(success){
        this.createSuccess = true;
        this.showPopup("Success", "Account Created.")
      } else {
        this.createSuccess = false;
        this.showPopup("Error", "Problem creating account.")
      }
    },
    error => {
      this.showPopup("Error", error)
    })
  }

  showPopup(title, text){
    let alert = this.alertCtrl.create({
      title: title,
      subTitle: text,
      buttons: [{
        text: 'OK',
        handler: data => {
          if(this.createSuccess){
            this.navCtrl.setRoot(LoginPage);
          }
        }
      }]
    })
    alert.present();
  }

}
