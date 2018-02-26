/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RandonneeDetailComponent } from '../../../../../../main/webapp/app/entities/randonnee/randonnee-detail.component';
import { RandonneeService } from '../../../../../../main/webapp/app/entities/randonnee/randonnee.service';
import { Randonnee } from '../../../../../../main/webapp/app/entities/randonnee/randonnee.model';

describe('Component Tests', () => {

    describe('Randonnee Management Detail Component', () => {
        let comp: RandonneeDetailComponent;
        let fixture: ComponentFixture<RandonneeDetailComponent>;
        let service: RandonneeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RandonneeDetailComponent],
                providers: [
                    RandonneeService
                ]
            })
            .overrideTemplate(RandonneeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RandonneeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RandonneeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Randonnee(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.randonnee).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
