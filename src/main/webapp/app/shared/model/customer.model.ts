import { ICountry } from 'app/shared/model/country.model';
import { IPaymentTerms } from 'app/shared/model/payment-terms.model';

export const enum Language {
  FRENCH = 'FRENCH',
  ENGLISH = 'ENGLISH',
  SPANISH = 'SPANISH'
}

export interface ICustomer {
  id?: number;
  name?: string;
  language?: Language;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  country?: ICountry;
  defaultTerms?: IPaymentTerms;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public language?: Language,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public country?: ICountry,
    public defaultTerms?: IPaymentTerms
  ) {}
}
