import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationRandonneurModule } from './randonneur/randonneur.module';
import { JhipsterSampleApplicationMessageModule } from './message/message.module';
import { JhipsterSampleApplicationRandonneeModule } from './randonnee/randonnee.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationRandonneurModule,
        JhipsterSampleApplicationMessageModule,
        JhipsterSampleApplicationRandonneeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
