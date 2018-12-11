/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MesCoursesTestModule } from '../../../test.module';
import { CheckPointComponent } from 'app/entities/check-point/check-point.component';
import { CheckPointService } from 'app/entities/check-point/check-point.service';
import { CheckPoint } from 'app/shared/model/check-point.model';

describe('Component Tests', () => {
    describe('CheckPoint Management Component', () => {
        let comp: CheckPointComponent;
        let fixture: ComponentFixture<CheckPointComponent>;
        let service: CheckPointService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [CheckPointComponent],
                providers: []
            })
                .overrideTemplate(CheckPointComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CheckPointComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CheckPointService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CheckPoint(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.checkPoints[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
