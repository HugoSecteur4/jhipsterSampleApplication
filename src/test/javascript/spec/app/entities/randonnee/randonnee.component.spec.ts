/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RandonneeComponent } from '../../../../../../main/webapp/app/entities/randonnee/randonnee.component';
import { RandonneeService } from '../../../../../../main/webapp/app/entities/randonnee/randonnee.service';
import { Randonnee } from '../../../../../../main/webapp/app/entities/randonnee/randonnee.model';

describe('Component Tests', () => {

    describe('Randonnee Management Component', () => {
        let comp: RandonneeComponent;
        let fixture: ComponentFixture<RandonneeComponent>;
        let service: RandonneeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RandonneeComponent],
                providers: [
                    RandonneeService
                ]
            })
            .overrideTemplate(RandonneeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RandonneeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RandonneeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Randonnee(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.randonnees[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
