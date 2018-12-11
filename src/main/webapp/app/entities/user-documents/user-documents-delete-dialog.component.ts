import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserDocuments } from 'app/shared/model/user-documents.model';
import { UserDocumentsService } from './user-documents.service';

@Component({
    selector: 'jhi-user-documents-delete-dialog',
    templateUrl: './user-documents-delete-dialog.component.html'
})
export class UserDocumentsDeleteDialogComponent {
    userDocuments: IUserDocuments;

    constructor(
        private userDocumentsService: UserDocumentsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userDocumentsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userDocumentsListModification',
                content: 'Deleted an userDocuments'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-documents-delete-popup',
    template: ''
})
export class UserDocumentsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userDocuments }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserDocumentsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userDocuments = userDocuments;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
