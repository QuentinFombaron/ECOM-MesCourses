import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICheckPoint } from 'app/shared/model/check-point.model';

type EntityResponseType = HttpResponse<ICheckPoint>;
type EntityArrayResponseType = HttpResponse<ICheckPoint[]>;

@Injectable({ providedIn: 'root' })
export class CheckPointService {
    private resourceUrl = SERVER_API_URL + 'api/check-points';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/check-points';

    constructor(private http: HttpClient) {}

    create(checkPoint: ICheckPoint): Observable<EntityResponseType> {
        return this.http.post<ICheckPoint>(this.resourceUrl, checkPoint, { observe: 'response' });
    }

    update(checkPoint: ICheckPoint): Observable<EntityResponseType> {
        return this.http.put<ICheckPoint>(this.resourceUrl, checkPoint, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICheckPoint>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICheckPoint[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICheckPoint[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
