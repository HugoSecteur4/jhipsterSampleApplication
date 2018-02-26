import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Randonnee } from './randonnee.model';
import { RandonneePopupService } from './randonnee-popup.service';
import { RandonneeService } from './randonnee.service';

@Component({
    selector: 'jhi-randonnee-delete-dialog',
    templateUrl: './randonnee-delete-dialog.component.html'
})
export class RandonneeDeleteDialogComponent {

    randonnee: Randonnee;

    constructor(
        private randonneeService: RandonneeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.randonneeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'randonneeListModification',
                content: 'Deleted an randonnee'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-randonnee-delete-popup',
    template: ''
})
export class RandonneeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private randonneePopupService: RandonneePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.randonneePopupService
                .open(RandonneeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
