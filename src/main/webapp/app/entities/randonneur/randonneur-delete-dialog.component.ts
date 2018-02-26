import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Randonneur } from './randonneur.model';
import { RandonneurPopupService } from './randonneur-popup.service';
import { RandonneurService } from './randonneur.service';

@Component({
    selector: 'jhi-randonneur-delete-dialog',
    templateUrl: './randonneur-delete-dialog.component.html'
})
export class RandonneurDeleteDialogComponent {

    randonneur: Randonneur;

    constructor(
        private randonneurService: RandonneurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.randonneurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'randonneurListModification',
                content: 'Deleted an randonneur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-randonneur-delete-popup',
    template: ''
})
export class RandonneurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private randonneurPopupService: RandonneurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.randonneurPopupService
                .open(RandonneurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
