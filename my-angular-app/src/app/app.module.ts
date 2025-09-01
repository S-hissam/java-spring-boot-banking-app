import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountlistComponent } from './components/accountlist/accountlist.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { TransferFundsComponent } from './components/transfer-funds/transfer-funds.component';
import { TransactionHistoryComponent } from './components/transaction-history/transaction-history.component';
import { NavbarComponent } from './components/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    AccountlistComponent,
    AccountDetailsComponent,
    CreateAccountComponent,
    TransferFundsComponent,
    TransactionHistoryComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
