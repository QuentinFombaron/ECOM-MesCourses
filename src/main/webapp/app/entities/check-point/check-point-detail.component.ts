import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckPoint } from 'app/shared/model/check-point.model';

@Component({
    selector: 'jhi-check-point-detail',
    templateUrl: './check-point-detail.component.html'
})
export class CheckPointDetailComponent implements OnInit {
    checkPoint: ICheckPoint;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ checkPoint }) => {
            this.checkPoint = checkPoint;
        });
    }

    previousState() {
        window.history.back();
    }
}
