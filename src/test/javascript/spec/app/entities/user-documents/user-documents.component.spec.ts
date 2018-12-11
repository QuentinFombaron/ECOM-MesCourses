/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MesCoursesTestModule } from '../../../test.module';
import { UserDocumentsComponent } from 'app/entities/user-documents/user-documents.component';
import { UserDocumentsService } from 'app/entities/user-documents/user-documents.service';
import { UserDocuments } from 'app/shared/model/user-documents.model';

describe('Component Tests', () => {
    describe('UserDocuments Management Component', () => {
        let comp: UserDocumentsComponent;
        let fixture: ComponentFixture<UserDocumentsComponent>;
        let service: UserDocumentsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [UserDocumentsComponent],
                providers: []
            })
                .overrideTemplate(UserDocumentsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserDocumentsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDocumentsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UserDocuments(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.userDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
