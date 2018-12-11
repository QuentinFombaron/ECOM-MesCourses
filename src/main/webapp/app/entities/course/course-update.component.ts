import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-course-update',
    templateUrl: './course-update.component.html'
})
export class CourseUpdateComponent implements OnInit {
    private _course: ICourse;
    isSaving: boolean;

    users: IUser[];
    dateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private courseService: CourseService,
        private userService: UserService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute,
        private http: HttpClient
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ course }) => {
            this.course = course;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.course, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.course.id !== undefined) {
            this.http.get('https://api-adresse.data.gouv.fr/search/?q=' + this.course.lieu).subscribe((data: any) => {
                this.course.longitude = data.features[0].geometry.coordinates[0];
                this.course.latitude = data.features[0].geometry.coordinates[1];
                this.subscribeToSaveResponse(this.courseService.update(this.course));
            });
        } else {
            this.http.get('https://api-adresse.data.gouv.fr/search/?q=' + this.course.lieu).subscribe((data: any) => {
                this.course.longitude = data.features[0].geometry.coordinates[0];
                this.course.latitude = data.features[0].geometry.coordinates[1];
                this.subscribeToSaveResponse(this.courseService.create(this.course));
            });
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>) {
        result.subscribe((res: HttpResponse<ICourse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get course() {
        return this._course;
    }

    set course(course: ICourse) {
        this._course = course;
    }
}
