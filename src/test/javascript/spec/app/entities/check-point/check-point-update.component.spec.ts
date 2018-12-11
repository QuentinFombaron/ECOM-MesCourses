/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MesCoursesTestModule } from '../../../test.module';
import { CheckPointUpdateComponent } from 'app/entities/check-point/check-point-update.component';
import { CheckPointService } from 'app/entities/check-point/check-point.service';
import { CheckPoint } from 'app/shared/model/check-point.model';

describe('Component Tests', () => {
    describe('CheckPoint Management Update Component', () => {
        let comp: CheckPointUpdateComponent;
        let fixture: ComponentFixture<CheckPointUpdateComponent>;
        let service: CheckPointService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [CheckPointUpdateComponent]
            })
                .overrideTemplate(CheckPointUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CheckPointUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CheckPointService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CheckPoint(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.checkPoint = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CheckPoint();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.checkPoint = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
