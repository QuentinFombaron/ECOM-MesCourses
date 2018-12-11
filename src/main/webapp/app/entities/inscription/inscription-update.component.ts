import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from './inscription.service';

@Component({
    selector: 'jhi-inscription-update',
    templateUrl: './inscription-update.component.html'
})
export class InscriptionUpdateComponent implements OnInit {
    private _inscription: IInscription;
    isSaving: boolean;

    constructor(private inscriptionService: InscriptionService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ inscription }) => {
            this.inscription = inscription;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.inscription.id !== undefined) {
            this.subscribeToSaveResponse(this.inscriptionService.update(this.inscription));
        } else {
            this.subscribeToSaveResponse(this.inscriptionService.create(this.inscription));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInscription>>) {
        result.subscribe((res: HttpResponse<IInscription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get inscription() {
        return this._inscription;
    }

    set inscription(inscription: IInscription) {
        this._inscription = inscription;
    }
}
