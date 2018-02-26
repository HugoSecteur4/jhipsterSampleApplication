import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    RandonneeService,
    RandonneePopupService,
    RandonneeComponent,
    RandonneeDetailComponent,
    RandonneeDialogComponent,
    RandonneePopupComponent,
    RandonneeDeletePopupComponent,
    RandonneeDeleteDialogComponent,
    randonneeRoute,
    randonneePopupRoute,
    RandonneeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...randonneeRoute,
    ...randonneePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RandonneeComponent,
        RandonneeDetailComponent,
        RandonneeDialogComponent,
        RandonneeDeleteDialogComponent,
        RandonneePopupComponent,
        RandonneeDeletePopupComponent,
    ],
    entryComponents: [
        RandonneeComponent,
        RandonneeDialogComponent,
        RandonneePopupComponent,
        RandonneeDeleteDialogComponent,
        RandonneeDeletePopupComponent,
    ],
    providers: [
        RandonneeService,
        RandonneePopupService,
        RandonneeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationRandonneeModule {}
