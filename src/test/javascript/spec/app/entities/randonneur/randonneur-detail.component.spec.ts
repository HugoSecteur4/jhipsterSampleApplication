/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RandonneurDetailComponent } from '../../../../../../main/webapp/app/entities/randonneur/randonneur-detail.component';
import { RandonneurService } from '../../../../../../main/webapp/app/entities/randonneur/randonneur.service';
import { Randonneur } from '../../../../../../main/webapp/app/entities/randonneur/randonneur.model';

describe('Component Tests', () => {

    describe('Randonneur Management Detail Component', () => {
        let comp: RandonneurDetailComponent;
        let fixture: ComponentFixture<RandonneurDetailComponent>;
        let service: RandonneurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RandonneurDetailComponent],
                providers: [
                    RandonneurService
                ]
            })
            .overrideTemplate(RandonneurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RandonneurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RandonneurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Randonneur(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.randonneur).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
