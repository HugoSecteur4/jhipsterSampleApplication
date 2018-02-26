/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RandonneurComponent } from '../../../../../../main/webapp/app/entities/randonneur/randonneur.component';
import { RandonneurService } from '../../../../../../main/webapp/app/entities/randonneur/randonneur.service';
import { Randonneur } from '../../../../../../main/webapp/app/entities/randonneur/randonneur.model';

describe('Component Tests', () => {

    describe('Randonneur Management Component', () => {
        let comp: RandonneurComponent;
        let fixture: ComponentFixture<RandonneurComponent>;
        let service: RandonneurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RandonneurComponent],
                providers: [
                    RandonneurService
                ]
            })
            .overrideTemplate(RandonneurComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RandonneurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RandonneurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Randonneur(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.randonneurs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
