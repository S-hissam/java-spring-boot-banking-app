import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
 http = inject(HttpClient);
  baseUrl = 'http://localhost:8080/api/';

  getAccounts() {
    return this.http.get(this.baseUrl + 'accounts').pipe(
      catchError(err => {
        console.error('Error fetching accounts', err);
        return throwError(() => new Error('Error fetching accounts'));
        })

  }

  getAccountDetails(accountId: string) {
    return this.http.get(this.baseUrl + `accounts/${accountId}`).pipe(
      catchError(err => {
        console.error('Error fetching account details', err);
        return throwError(() => new Error('Error fetching account details'));
      })
    );
  }

  addAccount(accountData: any) {
    return this.http.post(this.baseUrl + 'accounts', accountData).pipe(
      catchError(err => {
        console.error('Error adding account', err);
        return throwError(() => new Error('Error adding account'));
      })
    );
  }

  updateAccount(accountId: string, accountData: any) {
    return this.http.put(this.baseUrl + `accounts/${accountId}`, accountData).pipe(
      catchError(err => {
        console.error('Error updating account', err);
        return throwError(() => new Error('Error updating account'));
      })
    );
  }

  deleteAccount(accountId: string) {
    return this.http.delete(this.baseUrl + `accounts/${accountId}`).pipe(
      catchError(err => {
        console.error('Error deleting account', err);
        return throwError(() => new Error('Error deleting account'));
      })
    );
  }


}
