import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
http = inject(HttpClient);
baseUrl = 'http://localhost:8080/api/';

getTransactions() {
  return this.http.get(this.baseUrl + 'transactions').pipe(
    catchError(err => {
      console.error('Error fetching transactions', err);
      return throwError(() => new Error('Error fetching transactions'));
      })

  );
  }


}
