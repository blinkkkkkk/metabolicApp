import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { LoginPage } from '../pages/login/login';

import 'rxjs/add/operator/map';

export class User {
  name: string;
  email: string;

  constructor(name: string, email: string){
    this.name = name;
    this.email = email;
  }
}


@Injectable()
export class Auth {
  currentUser: User;

  //constructor(private navCtrl: NavController) {}

  public login(credentials){
    if(credentials.email === null || credentials.password === null){
      return Observable.throw("Please insert credentials")
    }else{
      return Observable.create(observer => {
        let access = (credentials.password === "pass", credentials.email ="email")
        this.currentUser = new User('Kiran/Yihang', 'Admin@gmail.com')
        observer.next(access);
        observer.complete();
      })
    }
  }

  public register(credentials){
    if (credentials.email === null || credentials.password === null) {
      return Observable.throw("Please insert credentials");
    } else {
      return Observable.create(observer => {
        observer.next(true);
        observer.complete();
      })
    }
  }

  public getUserInfo() : User {
    return this.currentUser;
  }

  public logout(){
    return Observable.create(observer => {
      this.currentUser = null;
      //this.navCtrl.setRoot(LoginPage);
      observer.next(true);
      observer.complete();
    })
  }

}
