import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckPoint } from 'app/shared/model/check-point.model';
import { CheckPointService } from './check-point.service';

@Component({
    selector: 'jhi-check-point-delete-dialog',
    templateUrl: './check-point-delete-dialog.component.html'
})
export class CheckPointDeleteDialogComponent {
    checkPoint: ICheckPoint;

    constructor(private checkPointService: CheckPointService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.checkPointService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'checkPointListModification',
                content: 'Deleted an checkPoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-check-point-delete-popup',
    template: ''
})
export class CheckPointDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ checkPoint }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CheckPointDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.checkPoint = checkPoint;
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
