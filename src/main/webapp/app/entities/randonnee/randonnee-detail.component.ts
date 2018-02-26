import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Randonnee } from './randonnee.model';
import { RandonneeService } from './randonnee.service';

@Component({
    selector: 'jhi-randonnee-detail',
    templateUrl: './randonnee-detail.component.html'
})
export class RandonneeDetailComponent implements OnInit, OnDestroy {

    randonnee: Randonnee;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private randonneeService: RandonneeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRandonnees();
    }

    load(id) {
        this.randonneeService.find(id)
            .subscribe((randonneeResponse: HttpResponse<Randonnee>) => {
                this.randonnee = randonneeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRandonnees() {
        this.eventSubscriber = this.eventManager.subscribe(
            'randonneeListModification',
            (response) => this.load(this.randonnee.id)
        );
    }
}
