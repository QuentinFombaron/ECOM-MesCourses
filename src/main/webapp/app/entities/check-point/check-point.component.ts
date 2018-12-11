import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICheckPoint } from 'app/shared/model/check-point.model';
import { Principal } from 'app/core';
import { CheckPointService } from './check-point.service';

@Component({
    selector: 'jhi-check-point',
    templateUrl: './check-point.component.html'
})
export class CheckPointComponent implements OnInit, OnDestroy {
    checkPoints: ICheckPoint[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private checkPointService: CheckPointService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.checkPointService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICheckPoint[]>) => (this.checkPoints = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.checkPointService.query().subscribe(
            (res: HttpResponse<ICheckPoint[]>) => {
                this.checkPoints = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCheckPoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICheckPoint) {
        return item.id;
    }

    registerChangeInCheckPoints() {
        this.eventSubscriber = this.eventManager.subscribe('checkPointListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
