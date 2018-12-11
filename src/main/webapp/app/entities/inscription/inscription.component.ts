import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInscription } from 'app/shared/model/inscription.model';
import { Principal } from 'app/core';
import { InscriptionService } from './inscription.service';

@Component({
    selector: 'jhi-inscription',
    templateUrl: './inscription.component.html'
})
export class InscriptionComponent implements OnInit, OnDestroy {
    inscriptions: IInscription[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private inscriptionService: InscriptionService,
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
            this.inscriptionService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IInscription[]>) => (this.inscriptions = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.inscriptionService.query().subscribe(
            (res: HttpResponse<IInscription[]>) => {
                this.inscriptions = res.body;
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
        this.registerChangeInInscriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInscription) {
        return item.id;
    }

    registerChangeInInscriptions() {
        this.eventSubscriber = this.eventManager.subscribe('inscriptionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
