/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MesCoursesTestModule } from '../../../test.module';
import { UserDocumentsDeleteDialogComponent } from 'app/entities/user-documents/user-documents-delete-dialog.component';
import { UserDocumentsService } from 'app/entities/user-documents/user-documents.service';

describe('Component Tests', () => {
    describe('UserDocuments Management Delete Component', () => {
        let comp: UserDocumentsDeleteDialogComponent;
        let fixture: ComponentFixture<UserDocumentsDeleteDialogComponent>;
        let service: UserDocumentsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [UserDocumentsDeleteDialogComponent]
            })
                .overrideTemplate(UserDocumentsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserDocumentsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDocumentsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
