import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Randonnee } from './randonnee.model';
import { RandonneeService } from './randonnee.service';

@Injectable()
export class RandonneePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private randonneeService: RandonneeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.randonneeService.find(id)
                    .subscribe((randonneeResponse: HttpResponse<Randonnee>) => {
                        const randonnee: Randonnee = randonneeResponse.body;
                        randonnee.date = this.datePipe
                            .transform(randonnee.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.randonneeModalRef(component, randonnee);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.randonneeModalRef(component, new Randonnee());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    randonneeModalRef(component: Component, randonnee: Randonnee): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.randonnee = randonnee;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
