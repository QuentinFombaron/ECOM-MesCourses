import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IUserDocuments } from 'app/shared/model/user-documents.model';
import { UserDocumentsService } from './user-documents.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-user-documents-update',
    templateUrl: './user-documents-update.component.html'
})
export class UserDocumentsUpdateComponent implements OnInit {
    private _userDocuments: IUserDocuments;
    isSaving: boolean;

    users: IUser[];
    dateDeNaissanceDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private userDocumentsService: UserDocumentsService,
        private userService: UserService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userDocuments }) => {
            this.userDocuments = userDocuments;
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
        this.dataUtils.clearInputImage(this.userDocuments, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userDocuments.id !== undefined) {
            this.subscribeToSaveResponse(this.userDocumentsService.update(this.userDocuments));
        } else {
            this.subscribeToSaveResponse(this.userDocumentsService.create(this.userDocuments));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserDocuments>>) {
        result.subscribe((res: HttpResponse<IUserDocuments>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get userDocuments() {
        return this._userDocuments;
    }

    set userDocuments(userDocuments: IUserDocuments) {
        this._userDocuments = userDocuments;
    }
}
