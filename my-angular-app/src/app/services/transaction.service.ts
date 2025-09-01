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

 getTransactionDetails(transactionId: string) {
  return this.http.get(this.baseUrl + `transactions/${transactionId}`).pipe(
    catchError(err => {
      console.error('Error fetching transaction details', err);
      return throwError(() => new Error('Error fetching transaction details'));
    })
  );
  }

  addTransaction(transactionData: any) {
  return this.http.post(this.baseUrl + 'transactions', transactionData).pipe(
    catchError(err => {
      console.error('Error adding transaction', err);
      return throwError(() => new Error('Error adding transaction'));
    })
  );
  }

  updateTransaction(transactionId: string, transactionData: any) {
  return this.http.put(this.baseUrl + `transactions/${transactionId}`, transactionData).pipe(
    catchError(err => {
      console.error('Error updating transaction', err);
      return throwError(() => new Error('Error updating transaction'));
    })
  );
  }

  deleteTransaction(transactionId: string) {
  return this.http.delete(this.baseUrl + `transactions/${transactionId}`).pipe(
    catchError(err => {
      console.error('Error deleting transaction', err);
      return throwError(() => new Error('Error deleting transaction'));
    })
  );
  }


}
