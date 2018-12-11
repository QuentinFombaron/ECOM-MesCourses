import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Inscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from './inscription.service';
import { InscriptionComponent } from './inscription.component';
import { InscriptionDetailComponent } from './inscription-detail.component';
import { InscriptionUpdateComponent } from './inscription-update.component';
import { InscriptionDeletePopupComponent } from './inscription-delete-dialog.component';
import { IInscription } from 'app/shared/model/inscription.model';

@Injectable({ providedIn: 'root' })
export class InscriptionResolve implements Resolve<IInscription> {
    constructor(private service: InscriptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((inscription: HttpResponse<Inscription>) => inscription.body));
        }
        return of(new Inscription());
    }
}

export const inscriptionRoute: Routes = [
    {
        path: 'inscription',
        component: InscriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inscriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inscription/:id/view',
        component: InscriptionDetailComponent,
        resolve: {
            inscription: InscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inscriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inscription/new',
        component: InscriptionUpdateComponent,
        resolve: {
            inscription: InscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inscriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inscription/:id/edit',
        component: InscriptionUpdateComponent,
        resolve: {
            inscription: InscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inscriptions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inscriptionPopupRoute: Routes = [
    {
        path: 'inscription/:id/delete',
        component: InscriptionDeletePopupComponent,
        resolve: {
            inscription: InscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inscriptions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
