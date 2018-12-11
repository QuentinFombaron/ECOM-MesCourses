/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MesCoursesTestModule } from '../../../test.module';
import { UserDocumentsDetailComponent } from 'app/entities/user-documents/user-documents-detail.component';
import { UserDocuments } from 'app/shared/model/user-documents.model';

describe('Component Tests', () => {
    describe('UserDocuments Management Detail Component', () => {
        let comp: UserDocumentsDetailComponent;
        let fixture: ComponentFixture<UserDocumentsDetailComponent>;
        const route = ({ data: of({ userDocuments: new UserDocuments(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MesCoursesTestModule],
                declarations: [UserDocumentsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserDocumentsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserDocumentsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userDocuments).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
