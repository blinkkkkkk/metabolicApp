import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {Auth} from '../../providers/auth';
import {LoginPage} from '../login/login';

@Component({
  selector: 'page-latest',
  templateUrl: 'latest.html'
})

export class Latest {
  username = '';
  email= '';

  constructor(private navCtrl: NavController, private auth: Auth) {

  }

  items = [
    'Blood Test',
    'Rental Test',
    'Some test',
    'Some other test',
    'Even more tests',
    'How many',
    'Tests',
    'Are ',
    'There',
    'One more test',
    'Blood test',
    'Another blood test',
    'Rental Test',
    'Another rental test',
    'More rental tests',
    'More blood tests',
    'Hey here is another test'
  ];

  itemSelected(item: string) {
    console.log("Selected Item", item);
  }

  public logout(){
    this.auth.logout().subscribe(succ => {
      this.navCtrl.setRoot(LoginPage)
    })
  }

}