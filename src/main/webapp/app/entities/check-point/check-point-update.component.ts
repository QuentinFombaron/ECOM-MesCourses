import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICheckPoint } from 'app/shared/model/check-point.model';
import { CheckPointService } from './check-point.service';

@Component({
    selector: 'jhi-check-point-update',
    templateUrl: './check-point-update.component.html'
})
export class CheckPointUpdateComponent implements OnInit {
    private _checkPoint: ICheckPoint;
    isSaving: boolean;

    constructor(private checkPointService: CheckPointService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ checkPoint }) => {
            this.checkPoint = checkPoint;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.checkPoint.id !== undefined) {
            this.subscribeToSaveResponse(this.checkPointService.update(this.checkPoint));
        } else {
            this.subscribeToSaveResponse(this.checkPointService.create(this.checkPoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICheckPoint>>) {
        result.subscribe((res: HttpResponse<ICheckPoint>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get checkPoint() {
        return this._checkPoint;
    }

    set checkPoint(checkPoint: ICheckPoint) {
        this._checkPoint = checkPoint;
    }
}
