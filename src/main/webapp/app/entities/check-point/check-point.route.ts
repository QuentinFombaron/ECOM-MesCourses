import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CheckPoint } from 'app/shared/model/check-point.model';
import { CheckPointService } from './check-point.service';
import { CheckPointComponent } from './check-point.component';
import { CheckPointDetailComponent } from './check-point-detail.component';
import { CheckPointUpdateComponent } from './check-point-update.component';
import { CheckPointDeletePopupComponent } from './check-point-delete-dialog.component';
import { ICheckPoint } from 'app/shared/model/check-point.model';

@Injectable({ providedIn: 'root' })
export class CheckPointResolve implements Resolve<ICheckPoint> {
    constructor(private service: CheckPointService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((checkPoint: HttpResponse<CheckPoint>) => checkPoint.body));
        }
        return of(new CheckPoint());
    }
}

export const checkPointRoute: Routes = [
    {
        path: 'check-point',
        component: CheckPointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckPoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'check-point/:id/view',
        component: CheckPointDetailComponent,
        resolve: {
            checkPoint: CheckPointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckPoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'check-point/new',
        component: CheckPointUpdateComponent,
        resolve: {
            checkPoint: CheckPointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckPoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'check-point/:id/edit',
        component: CheckPointUpdateComponent,
        resolve: {
            checkPoint: CheckPointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckPoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const checkPointPopupRoute: Routes = [
    {
        path: 'check-point/:id/delete',
        component: CheckPointDeletePopupComponent,
        resolve: {
            checkPoint: CheckPointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CheckPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
