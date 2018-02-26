import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RandonneurComponent } from './randonneur.component';
import { RandonneurDetailComponent } from './randonneur-detail.component';
import { RandonneurPopupComponent } from './randonneur-dialog.component';
import { RandonneurDeletePopupComponent } from './randonneur-delete-dialog.component';

@Injectable()
export class RandonneurResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const randonneurRoute: Routes = [
    {
        path: 'randonneur',
        component: RandonneurComponent,
        resolve: {
            'pagingParams': RandonneurResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonneur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'randonneur/:id',
        component: RandonneurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonneur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const randonneurPopupRoute: Routes = [
    {
        path: 'randonneur-new',
        component: RandonneurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonneur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'randonneur/:id/edit',
        component: RandonneurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonneur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'randonneur/:id/delete',
        component: RandonneurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonneur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
