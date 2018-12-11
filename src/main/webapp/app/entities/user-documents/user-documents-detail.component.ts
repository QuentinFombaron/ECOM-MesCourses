import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IUserDocuments } from 'app/shared/model/user-documents.model';

@Component({
    selector: 'jhi-user-documents-detail',
    templateUrl: './user-documents-detail.component.html'
})
export class UserDocumentsDetailComponent implements OnInit {
    userDocuments: IUserDocuments;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userDocuments }) => {
            this.userDocuments = userDocuments;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
