import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { HomePage } from '../home/home';
import { Latest } from '../latestTestResults/latest';
import { Upcoming } from '../upcomingTest/upcoming';

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {
    constructor(private navCtrl: NavController){}
  // this tells the tabs component which Pages
  // should be each tab's root Page
  tab1Root: any = HomePage;
  tab2Root: any = Latest;
  tab3Root: any = Upcoming;


}
