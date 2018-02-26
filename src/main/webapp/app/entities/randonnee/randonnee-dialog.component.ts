import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Randonnee } from './randonnee.model';
import { RandonneePopupService } from './randonnee-popup.service';
import { RandonneeService } from './randonnee.service';
import { Randonneur, RandonneurService } from '../randonneur';

@Component({
    selector: 'jhi-randonnee-dialog',
    templateUrl: './randonnee-dialog.component.html'
})
export class RandonneeDialogComponent implements OnInit {

    randonnee: Randonnee;
    isSaving: boolean;

    randonneurs: Randonneur[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private randonneeService: RandonneeService,
        private randonneurService: RandonneurService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.randonneurService.query()
            .subscribe((res: HttpResponse<Randonneur[]>) => { this.randonneurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.randonnee.id !== undefined) {
            this.subscribeToSaveResponse(
                this.randonneeService.update(this.randonnee));
        } else {
            this.subscribeToSaveResponse(
                this.randonneeService.create(this.randonnee));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Randonnee>>) {
        result.subscribe((res: HttpResponse<Randonnee>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Randonnee) {
        this.eventManager.broadcast({ name: 'randonneeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRandonneurById(index: number, item: Randonneur) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-randonnee-popup',
    template: ''
})
export class RandonneePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private randonneePopupService: RandonneePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.randonneePopupService
                    .open(RandonneeDialogComponent as Component, params['id']);
            } else {
                this.randonneePopupService
                    .open(RandonneeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
