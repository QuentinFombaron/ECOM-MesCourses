/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MesCoursesTestModule } from '../../../test.module';
import { UserDocumentsUpdateComponent } from 'app/entities/user-documents/user-documents-update.component';
import { UserDocumentsService } from 'app/entities/user-documents/user-documents.service';
import { UserDocuments } from 'app/shared/model/user-documents.model';

describe('Component Tests', () => {
    describe('UserDocuments Management Update Component', () => {
        let comp: UserDocumentsUpdateComponent;
        let fixture: ComponentFixture<UserDocumentsUpdateComponent>;
        let service: UserDocumentsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [UserDocumentsUpdateComponent]
            })
                .overrideTemplate(UserDocumentsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserDocumentsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDocumentsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserDocuments(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userDocuments = entity;
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
                    const entity = new UserDocuments();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userDocuments = entity;
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
