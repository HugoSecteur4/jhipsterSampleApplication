import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RandonneeComponent } from './randonnee.component';
import { RandonneeDetailComponent } from './randonnee-detail.component';
import { RandonneePopupComponent } from './randonnee-dialog.component';
import { RandonneeDeletePopupComponent } from './randonnee-delete-dialog.component';

@Injectable()
export class RandonneeResolvePagingParams implements Resolve<any> {

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

export const randonneeRoute: Routes = [
    {
        path: 'randonnee',
        component: RandonneeComponent,
        resolve: {
            'pagingParams': RandonneeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonnee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'randonnee/:id',
        component: RandonneeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonnee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const randonneePopupRoute: Routes = [
    {
        path: 'randonnee-new',
        component: RandonneePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonnee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'randonnee/:id/edit',
        component: RandonneePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonnee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'randonnee/:id/delete',
        component: RandonneeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.randonnee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
