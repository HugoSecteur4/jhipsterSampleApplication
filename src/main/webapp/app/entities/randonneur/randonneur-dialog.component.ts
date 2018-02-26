import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Randonneur } from './randonneur.model';
import { RandonneurPopupService } from './randonneur-popup.service';
import { RandonneurService } from './randonneur.service';
import { Randonnee, RandonneeService } from '../randonnee';
import { Message, MessageService } from '../message';

@Component({
    selector: 'jhi-randonneur-dialog',
    templateUrl: './randonneur-dialog.component.html'
})
export class RandonneurDialogComponent implements OnInit {

    randonneur: Randonneur;
    isSaving: boolean;

    randonnees: Randonnee[];

    messages: Message[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private randonneurService: RandonneurService,
        private randonneeService: RandonneeService,
        private messageService: MessageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.randonneeService.query()
            .subscribe((res: HttpResponse<Randonnee[]>) => { this.randonnees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.messageService.query()
            .subscribe((res: HttpResponse<Message[]>) => { this.messages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.randonneur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.randonneurService.update(this.randonneur));
        } else {
            this.subscribeToSaveResponse(
                this.randonneurService.create(this.randonneur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Randonneur>>) {
        result.subscribe((res: HttpResponse<Randonneur>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Randonneur) {
        this.eventManager.broadcast({ name: 'randonneurListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRandonneeById(index: number, item: Randonnee) {
        return item.id;
    }

    trackMessageById(index: number, item: Message) {
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
    selector: 'jhi-randonneur-popup',
    template: ''
})
export class RandonneurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private randonneurPopupService: RandonneurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.randonneurPopupService
                    .open(RandonneurDialogComponent as Component, params['id']);
            } else {
                this.randonneurPopupService
                    .open(RandonneurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
