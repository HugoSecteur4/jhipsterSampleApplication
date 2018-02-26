import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Randonneur } from './randonneur.model';
import { RandonneurService } from './randonneur.service';

@Component({
    selector: 'jhi-randonneur-detail',
    templateUrl: './randonneur-detail.component.html'
})
export class RandonneurDetailComponent implements OnInit, OnDestroy {

    randonneur: Randonneur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private randonneurService: RandonneurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRandonneurs();
    }

    load(id) {
        this.randonneurService.find(id)
            .subscribe((randonneurResponse: HttpResponse<Randonneur>) => {
                this.randonneur = randonneurResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRandonneurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'randonneurListModification',
            (response) => this.load(this.randonneur.id)
        );
    }
}
