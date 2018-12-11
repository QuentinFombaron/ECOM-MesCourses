/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MesCoursesTestModule } from '../../../test.module';
import { CheckPointDetailComponent } from 'app/entities/check-point/check-point-detail.component';
import { CheckPoint } from 'app/shared/model/check-point.model';

describe('Component Tests', () => {
    describe('CheckPoint Management Detail Component', () => {
        let comp: CheckPointDetailComponent;
        let fixture: ComponentFixture<CheckPointDetailComponent>;
        const route = ({ data: of({ checkPoint: new CheckPoint(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [CheckPointDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CheckPointDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CheckPointDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.checkPoint).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
