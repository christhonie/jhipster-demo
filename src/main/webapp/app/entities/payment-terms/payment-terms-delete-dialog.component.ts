import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentTerms } from 'app/shared/model/payment-terms.model';
import { PaymentTermsService } from './payment-terms.service';

@Component({
  selector: 'jhi-payment-terms-delete-dialog',
  templateUrl: './payment-terms-delete-dialog.component.html'
})
export class PaymentTermsDeleteDialogComponent {
  paymentTerms: IPaymentTerms;

  constructor(
    protected paymentTermsService: PaymentTermsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paymentTermsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paymentTermsListModification',
        content: 'Deleted an paymentTerms'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-payment-terms-delete-popup',
  template: ''
})
export class PaymentTermsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentTerms }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaymentTermsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paymentTerms = paymentTerms;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/payment-terms', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/payment-terms', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
