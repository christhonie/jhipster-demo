import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentTerms } from 'app/shared/model/payment-terms.model';

type EntityResponseType = HttpResponse<IPaymentTerms>;
type EntityArrayResponseType = HttpResponse<IPaymentTerms[]>;

@Injectable({ providedIn: 'root' })
export class PaymentTermsService {
  public resourceUrl = SERVER_API_URL + 'api/payment-terms';

  constructor(protected http: HttpClient) {}

  create(paymentTerms: IPaymentTerms): Observable<EntityResponseType> {
    return this.http.post<IPaymentTerms>(this.resourceUrl, paymentTerms, { observe: 'response' });
  }

  update(paymentTerms: IPaymentTerms): Observable<EntityResponseType> {
    return this.http.put<IPaymentTerms>(this.resourceUrl, paymentTerms, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentTerms>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentTerms[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
